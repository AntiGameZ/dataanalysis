package com.ruyicai.dataanalysis.timer.zq;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ruyicai.dataanalysis.domain.EuropeCompany;
import com.ruyicai.dataanalysis.domain.GlobalCache;
import com.ruyicai.dataanalysis.domain.Schedule;
import com.ruyicai.dataanalysis.domain.Standard;
import com.ruyicai.dataanalysis.domain.StandardDetail;
import com.ruyicai.dataanalysis.service.GlobalInfoService;
import com.ruyicai.dataanalysis.util.CommonUtil;
import com.ruyicai.dataanalysis.util.DateUtil;
import com.ruyicai.dataanalysis.util.HttpUtil;
import com.ruyicai.dataanalysis.util.NumberUtil;
import com.ruyicai.dataanalysis.util.StringUtil;
import com.ruyicai.dataanalysis.util.ThreadPoolUtil;
import com.ruyicai.dataanalysis.util.zq.FootBallMapUtil;
import com.ruyicai.dataanalysis.util.zq.SendJmsJczUtil;

/**
 * 足球欧赔Detail更新
 * @author Administrator
 *
 */
@Service
public class StandardDetailUpdateService {

	private Logger logger = LoggerFactory.getLogger(StandardDetailUpdateService.class);
	
	private ThreadPoolExecutor standardDetailUpdateExecutor;
	
	@Value("${baijiaoupei}")
	private String url;
	
	@Autowired
	private HttpUtil httpUtil;
	
	@Autowired
	private FootBallMapUtil footBallMapUtil;
	
	@Autowired
	private SendJmsJczUtil sendJmsJczUtil;
	
	@Autowired
	private GlobalInfoService infoService;
	
	@PostConstruct
	public void init() {
		standardDetailUpdateExecutor = ThreadPoolUtil.createTaskExecutor("standardDetailUpdate", 20);
	}
	
	public void process() {
		logger.info("足球欧赔Detail更新开始");
		long startmillis = System.currentTimeMillis();
		try {
			String data = httpUtil.getResponse(url+"?min=2", HttpUtil.GET, HttpUtil.UTF8, "");
			if (StringUtils.isBlank(data)) {
				logger.info("足球欧赔Detail更新时获取数据为空");
				return;
			}
			ProcessStandardThread task = new ProcessStandardThread(data);
			//logger.info("standardUpdateExecutor,size="+standardDetailUpdateExecutor.getQueue().size());
			standardDetailUpdateExecutor.execute(task);
			long endmillis = System.currentTimeMillis();
			logger.info("足球欧赔Detail更新结束，共用时 " + (endmillis - startmillis));
		} catch (Exception e) {
			logger.error("足球欧赔Detail更新时发生异常", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private class ProcessStandardThread implements Runnable {
		private String data;
		
		private ProcessStandardThread(String data) {
			this.data = data;
		}
		
		@Override
		public void run() {
			logger.info("足球欧赔Detail更新-ProcessStandardThread开始");
			long startmillis = System.currentTimeMillis();
			try {
				Document doc = DocumentHelper.parseText(data);
				List<Element> matches = doc.getRootElement().elements("h");
				for(Element match : matches) {
					doStandard(match);
				}
				long endmillis = System.currentTimeMillis();
				logger.info("足球欧赔Detail更新-ProcessStandardThread结束, 共用时 "+(endmillis-startmillis)+",size="+matches.size()
						+",threadPoolSize="+standardDetailUpdateExecutor.getQueue().size());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void doStandard(Element match) {
		try {
			String scheduleId = match.elementTextTrim("id");
			Schedule schedule = Schedule.findSchedule(Integer.parseInt(scheduleId));
			if(schedule==null) {
				return;
			}
			boolean isModify = false; //欧赔是否发生变化
			List<Element> odds = match.element("odds").elements("o");
			for(Element odd : odds) {
				boolean modify = doOdd(scheduleId, odd);
				if (!isModify&&modify) {
					isModify = true;
				}
			}
			if (isModify) {
				updateStandardCache(schedule);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean doOdd(String scheduleId, Element odd) {
		try {
			String o = odd.getTextTrim();
			String[] values = o.split("\\,");
			String companyId = values[0]; //博彩公司ID
			//String companyName = values[1]; //博彩公司名
			//String firstHomeWin = values[2]; //初盘主胜
			//String firstStandoff = values[3]; //初盘和局
			//String firstGuestWin = values[4]; //初盘客胜
			String homeWin = values[5]; //主胜
			String standoff = values[6]; //和局
			String guestWin = values[7]; //客胜
			String modTime = values[8]; //变化时间
			
			Boolean isValid = footBallMapUtil.europeCompanyMap.get(companyId);
			if (isValid==null) {
				EuropeCompany company = EuropeCompany.findEuropeCompany(Integer.parseInt(companyId));
				if (company!=null) {
					String name_Cn = company.getName_Cn();
					Integer isPrimary = company.getIsPrimary();
					//只保存接口需要的公司欧赔
					if (StringUtils.isNotBlank(name_Cn)&&isPrimary!=null&&isPrimary==1) {
						isValid = true;
					} else {
						isValid = false;
					}
				} else {
					isValid = false;
				}
				footBallMapUtil.europeCompanyMap.put(companyId, isValid);
			}
			if (!isValid) {
				return false;
			}
			
			Standard standard = Standard.findStandard(Integer.parseInt(scheduleId), Integer.parseInt(companyId));
			if (standard==null) {
				return false;
			}
			if ((StringUtils.isNotBlank(homeWin) && !NumberUtil.compare(homeWin, standard.getHomeWin()))
					||(StringUtils.isNotBlank(standoff) && !NumberUtil.compare(standoff, standard.getStandoff()))
					||(StringUtils.isNotBlank(guestWin) && !NumberUtil.compare(guestWin, standard.getGuestWin()))) {
				logger.info("scheduleId="+scheduleId+";companyId="+companyId+";homeWin="+homeWin+";standard.getHomeWin()="+standard.getHomeWin()
						+";standoff="+standoff+";standard.getStandoff()="+standard.getStandoff()
						+";guestWin="+guestWin+";standard.getGuestWin()="+standard.getGuestWin());
				standard.setHomeWin(new Double(homeWin));
				standard.setStandoff(new Double(standoff));
				standard.setGuestWin(new Double(guestWin));
				standard.setModifyTime(DateUtil.parse("yyyy/MM/dd HH:mm:ss", modTime));
				standard.merge();
				StandardDetail detail = new StandardDetail();
				detail.setOddsID(standard.getOddsID());
				detail.setIsEarly(0);
				detail.setHomeWin(new Double(homeWin));
				detail.setStandoff(new Double(standoff));
				detail.setGuestWin(new Double(guestWin));
				detail.setModifyTime(standard.getModifyTime());
				detail.persist();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void updateStandardCache(Schedule schedule) {
		boolean zqEventEmpty = CommonUtil.isZqEventEmpty(schedule);
		if (zqEventEmpty) { //如果event为空,则不需要更新缓存
			return ;
		}
		Integer scheduleId = schedule.getScheduleID();
		Collection<Standard> standards = Standard.findByScheduleID(scheduleId);
		infoService.buildStandards(schedule, standards);
		GlobalCache standard = GlobalCache.findGlobalCache(StringUtil.join("_", "dataanalysis", "Standard", String.valueOf(scheduleId)));
		if (standard==null) {
			standard = new GlobalCache();
			standard.setId(StringUtil.join("_", "dataanalysis", "Standard", String.valueOf(scheduleId)));
			standard.setValue(Standard.toJsonArray(standards));
			standard.persist();
		} else {
			standard.setValue(Standard.toJsonArray(standards));
			standard.merge();
		}
		sendJmsJczUtil.sendInfoUpdateJMS(String.valueOf(scheduleId));
	}
	
}