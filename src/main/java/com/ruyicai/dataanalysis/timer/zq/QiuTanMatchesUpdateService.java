package com.ruyicai.dataanalysis.timer.zq;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ruyicai.dataanalysis.domain.QiuTanMatches;
import com.ruyicai.dataanalysis.domain.Schedule;
import com.ruyicai.dataanalysis.util.CommonUtil;
import com.ruyicai.dataanalysis.util.DateUtil;
import com.ruyicai.dataanalysis.util.FileUtil;
import com.ruyicai.dataanalysis.util.HttpUtil;
import com.ruyicai.dataanalysis.util.StringUtil;
import com.ruyicai.dataanalysis.util.bd.BeiDanUtil;
import com.ruyicai.dataanalysis.util.jc.JingCaiUtil;
import com.ruyicai.dataanalysis.util.jc.JmsSendUtil;
import com.ruyicai.dataanalysis.util.zc.ZuCaiUtil;
import com.ruyicai.dataanalysis.util.zq.JmsZqUtil;

@Service
public class QiuTanMatchesUpdateService {
	
	private Logger logger = LoggerFactory.getLogger(QiuTanMatchesUpdateService.class);

	@Value("${qiutanmatches}")
	private String url;
	
	@Autowired
	private HttpUtil httpUtil;
	
	@Autowired
	private JmsSendUtil jmsSendUtil;
	
	@Autowired
	private JmsZqUtil jmsZqUtil;
	
	public void processFile(String filename) {
		try {
			logger.info("开始读取文件更新彩票赛事与球探网的关联表, filename:{}", new String[] {filename});
			long startmillis = System.currentTimeMillis();
			String data = FileUtil.read(filename);
			if (StringUtil.isEmpty(data)) {
				logger.info("更新彩票赛事与球探网的关联表时获取数据为空");
				return;
			}
			doProcess(data);
			long endmillis = System.currentTimeMillis();
			logger.info("读取文件更新彩票赛事与球探网的关联表结束，filename:{}, 共用时:{}", new String[] {filename, String.valueOf((endmillis-startmillis))});
		} catch(Exception e) {
			logger.error("读取文件更新彩票赛事与球探网的关联表出错,filename:" + filename, e);
		}
	}
	
	public void process() {
		try {
			logger.info("开始更新彩票赛事与球探网的关联表");
			long startmillis = System.currentTimeMillis();
			String data = httpUtil.getResponse(url, HttpUtil.GET, HttpUtil.UTF8, "");
			doProcess(data);
			long endmillis = System.currentTimeMillis();
			logger.info("更新彩票赛事与球探网的关联表结束，共用时 " + (endmillis - startmillis));
		} catch(Exception e) {
			logger.error("更新彩票赛事与球探网的关联表出错", e);
		}
	}

	@SuppressWarnings("unchecked")
	private void doProcess(String data) throws DocumentException {
		Document doc = DocumentHelper.parseText(data);
		List<Element> matches = doc.getRootElement().elements("i");
		logger.info("彩票赛事与球探网的关联表 size:" + matches.size());
		for(Element match : matches) {
			doProcess(match);
		}
	}

	/**
	 * 解析数据
	 * @param match
	 */
	private void doProcess(Element match) {
		try {
			String lotteryName = match.elementTextTrim("LotteryName");
			String issueNum = match.elementTextTrim("IssueNum");
			String iD = match.elementTextTrim("ID");
			String iD_bet007 = match.elementTextTrim("ID_bet007");
			String time = match.elementTextTrim("time");
			String home = match.elementTextTrim("Home");
			String away = match.elementTextTrim("Away");
			String homeID = match.elementTextTrim("HomeID");
			String awayID = match.elementTextTrim("AwayID");
			String turn = match.elementTextTrim("Turn"); //主客队是否需要反转
			turn = StringUtils.equals(turn, "True") ? "1" : "0";
			//如果是竞彩篮球则不执行
			if (JingCaiUtil.isJcLq(lotteryName)) { //竞彩篮球
				return;
			}
			Schedule schedule = Schedule.findSchedule(Integer.parseInt(iD_bet007), true);
			if(schedule==null) {
				return;
			}
			QiuTanMatches qiuTanMatches = QiuTanMatches.findByID_bet007(Integer.parseInt(iD_bet007), lotteryName);
			if(null == qiuTanMatches) { //记录不存在
				qiuTanMatches = saveQiuTanMatches(lotteryName, issueNum, iD, iD_bet007, time, home, away, homeID, awayID, turn);
			} else { //记录已存在
				updateQiuTanMatches(lotteryName, issueNum, iD, iD_bet007, time, home, away, homeID, awayID, turn, qiuTanMatches);
			}
			//更新赛事的event
			updateScheduleEvent(qiuTanMatches, schedule);
		} catch(Exception e) {
			logger.error("解析彩票赛事与球探网的关联表发生异常", e);
		}
	}

	/**
	 * 保存对象
	 * @param lotteryName
	 * @param issueNum
	 * @param iD
	 * @param iD_bet007
	 * @param time
	 * @param home
	 * @param away
	 * @param homeID
	 * @param awayID
	 * @param turn
	 * @return
	 */
	private QiuTanMatches saveQiuTanMatches(String lotteryName, String issueNum, String iD, String iD_bet007, String time,
			String home, String away, String homeID, String awayID, String turn) {
		QiuTanMatches qiuTanMatches;
		qiuTanMatches = new QiuTanMatches();
		qiuTanMatches.setLotteryName(lotteryName);
		qiuTanMatches.setIssueNum(issueNum);
		qiuTanMatches.setId(iD);
		qiuTanMatches.setID_bet007(Integer.parseInt(iD_bet007));
		qiuTanMatches.setTime(DateUtil.parse("yyyy/MM/dd HH:mm:ss", time));
		qiuTanMatches.setHome(home);
		qiuTanMatches.setAway(away);
		qiuTanMatches.setHomeID(Integer.parseInt(homeID));
		qiuTanMatches.setAwayID(Integer.parseInt(awayID));
		//设置event
		if (JingCaiUtil.isJcZq(lotteryName)) { //竞彩足球
			String event = JingCaiUtil.getEvent(lotteryName, iD, qiuTanMatches.getTime());
			qiuTanMatches.setEvent(event);
			qiuTanMatches.setTurn(turn);
			//判断是否有重复赛事
			JingCaiUtil.jczProcess(event, iD_bet007);
		} else if (ZuCaiUtil.isZuCai(lotteryName)) { //足彩
			String zcEvent = ZuCaiUtil.getZcEvent(lotteryName, issueNum, iD);
			if (ZuCaiUtil.isZcSfc(lotteryName)) { //足彩胜负彩
				qiuTanMatches.setZcSfcEvent(zcEvent);
				qiuTanMatches.setZcSfcTurn(turn);
				//判断是否有重复赛事
				ZuCaiUtil.sfcProcess(zcEvent, iD_bet007);
			} else if (ZuCaiUtil.isZcJqc(lotteryName)) { //足彩进球彩
				qiuTanMatches.setZcJqcEvent(zcEvent);
				qiuTanMatches.setZcJqcTurn(turn);
				//判断是否有重复赛事
				ZuCaiUtil.jqcProcess(zcEvent, iD_bet007);
			} else if (ZuCaiUtil.isZcBqc(lotteryName)) { //足彩半全场
				qiuTanMatches.setZcBqcEvent(zcEvent);
				qiuTanMatches.setZcBqcTurn(turn);
				//判断是否有重复赛事
				ZuCaiUtil.bqcProcess(zcEvent, iD_bet007);
			}
		} else if (BeiDanUtil.isBeiDan(lotteryName)) { //北单
			String bdEvent = BeiDanUtil.getBdEvent(issueNum, iD);
			qiuTanMatches.setBdEvent(bdEvent);
			qiuTanMatches.setBdTurn(turn);
			//判断是否有重复赛事
			BeiDanUtil.bdProcess(bdEvent, iD_bet007);
		}
		qiuTanMatches.persist();
		return qiuTanMatches;
	}
	
	private void updateQiuTanMatches(String lotteryName, String issueNum, String iD, String iD_bet007, String time, 
			String home, String away, String homeID, String awayID, String turn, QiuTanMatches qiuTanMatches) {
		boolean isModify = false;
		
		String lotteryName_old = qiuTanMatches.getLotteryName();
		if (!StringUtil.isEmpty(lotteryName) && (StringUtil.isEmpty(lotteryName_old)||!lotteryName.equals(lotteryName_old))) {
			isModify = true;
			qiuTanMatches.setLotteryName(lotteryName);
		}
		String issueNum_old = qiuTanMatches.getIssueNum();
		if (!StringUtil.isEmpty(issueNum) && (StringUtil.isEmpty(issueNum_old)||!issueNum.equals(issueNum_old))) {
			isModify = true;
			qiuTanMatches.setIssueNum(issueNum);
		}
		String id_old = qiuTanMatches.getId();
		if (!StringUtil.isEmpty(iD) && (StringUtil.isEmpty(id_old)||!iD.equals(id_old))) {
			isModify = true;
			qiuTanMatches.setId(iD);
		}
		Integer id_bet0072_old = qiuTanMatches.getID_bet007();
		if (!StringUtil.isEmpty(iD_bet007) && (id_bet0072_old==null||Integer.parseInt(iD_bet007)!=id_bet0072_old)) {
			isModify = true;
			qiuTanMatches.setID_bet007(Integer.parseInt(iD_bet007));
		}
		Date time_old = qiuTanMatches.getTime();
		if (StringUtils.isNotBlank(time)) {
			String pattern = "yyyy/MM/dd HH:mm:ss";
			Date dateTime = DateUtil.parse(pattern, time);
			String dateTimeStr = DateUtil.format(pattern, dateTime);
			String time_oldStr = DateUtil.format(pattern, time_old);
			if (time_old==null||!StringUtils.equals(dateTimeStr, time_oldStr)) {
				isModify = true;
				qiuTanMatches.setTime(dateTime);
			}
		}
		String home_old = qiuTanMatches.getHome();
		if (!StringUtil.isEmpty(home) && (StringUtil.isEmpty(home_old)||!home.equals(home_old))) {
			isModify = true;
			qiuTanMatches.setHome(home);
		}
		String away_old = qiuTanMatches.getAway();
		if (!StringUtil.isEmpty(away) && (StringUtil.isEmpty(away_old)||!away.equals(away_old))) {
			isModify = true;
			qiuTanMatches.setAway(away);
		}
		Integer homeID_old = qiuTanMatches.getHomeID();
		if (!StringUtil.isEmpty(homeID) && (homeID_old==null||Integer.parseInt(homeID)!=homeID_old)) {
			isModify = true;
			qiuTanMatches.setHomeID(Integer.parseInt(homeID));
		}
		Integer awayID_old = qiuTanMatches.getAwayID();
		if (!StringUtil.isEmpty(awayID) && (awayID_old==null||Integer.parseInt(awayID)!=awayID_old)) {
			isModify = true;
			qiuTanMatches.setAwayID(Integer.parseInt(awayID));
		}
		//设置event
		if (JingCaiUtil.isJcZq(lotteryName)) { //竞彩足球
			String event = JingCaiUtil.getEvent(lotteryName, iD, qiuTanMatches.getTime());
			String event_old = qiuTanMatches.getEvent();
			if (!StringUtil.isEmpty(event) && (StringUtil.isEmpty(event_old)||!event.equals(event_old))) {
				isModify = true;
				qiuTanMatches.setEvent(event);
			}
			String turn_old = qiuTanMatches.getTurn();
			if (StringUtils.isNotBlank(turn) && (StringUtils.isBlank(turn_old)||!StringUtils.equals(turn, turn_old))) {
				isModify = true;
				qiuTanMatches.setTurn(turn);
			}
			//判断是否有重复赛事
			JingCaiUtil.jczProcess(event, iD_bet007);
		} else if (ZuCaiUtil.isZuCai(lotteryName)) { //足彩
			String zcEvent = ZuCaiUtil.getZcEvent(lotteryName, issueNum, iD);
			if (ZuCaiUtil.isZcSfc(lotteryName)) { //足彩胜负彩
				String zcSfcEvent_old = qiuTanMatches.getZcSfcEvent();
				if (!StringUtil.isEmpty(zcEvent) && (StringUtil.isEmpty(zcSfcEvent_old)||!zcEvent.equals(zcSfcEvent_old))) {
					isModify = true;
					qiuTanMatches.setZcSfcEvent(zcEvent);
				}
				String zcSfcTurn_old = qiuTanMatches.getZcSfcTurn();
				if (StringUtils.isNotBlank(turn) && (StringUtils.isBlank(zcSfcTurn_old)||!StringUtils.equals(turn, zcSfcTurn_old))) {
					isModify = true;
					qiuTanMatches.setZcSfcTurn(turn);
				}
				//判断是否有重复赛事
				ZuCaiUtil.sfcProcess(zcEvent, iD_bet007);
			} else if (ZuCaiUtil.isZcJqc(lotteryName)) { //足彩进球彩
				String zcJqcEvent_old = qiuTanMatches.getZcJqcEvent();
				if (!StringUtil.isEmpty(zcEvent) && (StringUtil.isEmpty(zcJqcEvent_old)||!zcEvent.equals(zcJqcEvent_old))) {
					isModify = true;
					qiuTanMatches.setZcJqcEvent(zcEvent);
				}
				String zcJqcTurn_old = qiuTanMatches.getZcJqcTurn();
				if (StringUtils.isNotBlank(turn) && (StringUtils.isBlank(zcJqcTurn_old)||!StringUtils.equals(turn, zcJqcTurn_old))) {
					isModify = true;
					qiuTanMatches.setZcJqcTurn(turn);
				}
				//判断是否有重复赛事
				ZuCaiUtil.jqcProcess(zcEvent, iD_bet007);
			} else if (ZuCaiUtil.isZcBqc(lotteryName)) { //足彩半全场
				String zcBqcEvent_old = qiuTanMatches.getZcBqcEvent();
				if (!StringUtil.isEmpty(zcEvent) && (StringUtil.isEmpty(zcBqcEvent_old)||!zcEvent.equals(zcBqcEvent_old))) {
					isModify = true;
					qiuTanMatches.setZcBqcEvent(zcEvent);
				}
				String zcBqcTurn_old = qiuTanMatches.getZcBqcTurn();
				if (StringUtils.isNotBlank(turn) && (StringUtils.isBlank(zcBqcTurn_old)||!StringUtils.equals(turn, zcBqcTurn_old))) {
					isModify = true;
					qiuTanMatches.setZcBqcTurn(turn);
				}
				//判断是否有重复赛事
				ZuCaiUtil.bqcProcess(zcEvent, iD_bet007);
			}
		} else if (BeiDanUtil.isBeiDan(lotteryName)) { //北单
			String bdEvent = BeiDanUtil.getBdEvent(issueNum, iD);
			String bdEvent_old = qiuTanMatches.getBdEvent();
			if (StringUtils.isNotBlank(bdEvent) && (StringUtils.isBlank(bdEvent_old)||!StringUtils.equals(bdEvent, bdEvent_old))) {
				isModify = true;
				qiuTanMatches.setBdEvent(bdEvent);
			}
			String bdTurn_old = qiuTanMatches.getBdTurn();
			if (StringUtils.isNotBlank(turn) && (StringUtils.isBlank(bdTurn_old)||!StringUtils.equals(turn, bdTurn_old))) {
				isModify = true;
				qiuTanMatches.setBdTurn(turn);
			}
			//判断是否有重复赛事
			BeiDanUtil.bdProcess(bdEvent, iD_bet007);
		}
		if (isModify) {
			qiuTanMatches.merge();
		}
	}
	
	/**
	 * 更新赛事的event
	 * @param qiuTanMatches
	 * @param schedule
	 */
	private void updateScheduleEvent(QiuTanMatches qiuTanMatches, Schedule schedule) {
		boolean isUpdate = false;
		boolean eventModify = false;
		boolean turnModify = false;
		int scheduleId = schedule.getScheduleID();
		//竞彩足球
		String eventQ = qiuTanMatches.getEvent();
		String eventS = schedule.getEvent();
		logger.info("更新赛事scheduleId="+scheduleId+"的event时两参数值eventQ="+eventQ+",eventS="+eventS);
		if(StringUtils.isNotBlank(eventQ)&&(StringUtils.isBlank(eventS)||!StringUtils.equals(eventQ, eventS))) {
			logger.info("赛事添加event,event="+eventQ+",scheduleId="+scheduleId);
			schedule.setEvent(eventQ);
			isUpdate = true;
			eventModify = true;
		}
		String turnQ = qiuTanMatches.getTurn();
		String turnS = schedule.getTurn();
		if (StringUtils.isNotBlank(turnQ)&&(StringUtils.isBlank(turnS)||!StringUtils.equals(turnQ, turnS))) {
			schedule.setTurn(turnQ);
			isUpdate = true;
			turnModify = true;
		}
		//足彩胜负彩
		String zcSfcEventQ = qiuTanMatches.getZcSfcEvent();
		String zcSfcEventS = schedule.getZcSfcEvent();
		if(!StringUtil.isEmpty(zcSfcEventQ)&&(StringUtil.isEmpty(zcSfcEventS)||!zcSfcEventQ.equals(zcSfcEventS))) {
			schedule.setZcSfcEvent(zcSfcEventQ);
			isUpdate = true;
		}
		String zcSfcTurnQ = qiuTanMatches.getZcSfcTurn();
		String zcSfcTurnS = schedule.getZcSfcTurn();
		if (StringUtils.isNotBlank(zcSfcTurnQ)&&(StringUtils.isBlank(zcSfcTurnS)||!StringUtils.equals(zcSfcTurnQ, zcSfcTurnS))) {
			schedule.setZcSfcTurn(zcSfcTurnQ);
			isUpdate = true;
		}
		//足彩进球彩
		String zcJqcEventQ = qiuTanMatches.getZcJqcEvent();
		String zcJqcEventS = schedule.getZcJqcEvent();
		if(!StringUtil.isEmpty(zcJqcEventQ)&&(StringUtil.isEmpty(zcJqcEventS)||!zcJqcEventQ.equals(zcJqcEventS))) {
			schedule.setZcJqcEvent(zcJqcEventQ);
			isUpdate = true;
		}
		String zcJqcTurnQ = qiuTanMatches.getZcJqcTurn();
		String zcJqcTurnS = schedule.getZcJqcTurn();
		if (StringUtils.isNotBlank(zcJqcEventQ)&&(StringUtils.isBlank(zcJqcEventS)||!StringUtils.equals(zcJqcTurnQ, zcJqcTurnS))) {
			schedule.setZcJqcTurn(zcJqcTurnQ);
			isUpdate = true;
		}
		//足彩半全场
		String zcBqcEventQ = qiuTanMatches.getZcBqcEvent();
		String zcBqcEventS = schedule.getZcBqcEvent();
		if(!StringUtil.isEmpty(zcBqcEventQ)&&(StringUtil.isEmpty(zcBqcEventS)||!zcBqcEventQ.equals(zcBqcEventS))) {
			schedule.setZcBqcEvent(zcBqcEventQ);
			isUpdate = true;
		}
		String zcBqcTurnQ = qiuTanMatches.getZcBqcTurn();
		String zcBqcTurnS = schedule.getZcBqcTurn();
		if (StringUtils.isNotBlank(zcBqcTurnQ)&&(StringUtils.isBlank(zcBqcTurnS)||!StringUtils.equals(zcBqcTurnQ, zcBqcTurnS))) {
			schedule.setZcBqcTurn(zcBqcTurnQ);
			isUpdate = true;
		}
		//北单
		String bdEventQ = qiuTanMatches.getBdEvent();
		String bdEventS = schedule.getBdEvent();
		if(StringUtils.isNotBlank(bdEventQ)&&(StringUtils.isBlank(bdEventS)||!StringUtils.equals(bdEventQ, bdEventS))) {
			schedule.setBdEvent(bdEventQ);
			isUpdate = true;
		}
		String bdTurnQ = qiuTanMatches.getBdTurn();
		String bdTurnS = schedule.getBdTurn();
		if (StringUtils.isNotBlank(bdTurnQ)&&(StringUtils.isBlank(bdTurnS)||!StringUtils.equals(bdTurnQ, bdTurnS))) {
			schedule.setBdTurn(bdTurnQ);
			isUpdate = true;
		}
		if (isUpdate) {
			schedule.merge();
			//如果赛事已经有了event
			if (!CommonUtil.isZqEventEmpty(schedule)) {
				jmsZqUtil.standardAvgUpdate(String.valueOf(scheduleId)); //更新平均欧赔
				jmsZqUtil.standardCacheUpdate(String.valueOf(scheduleId)); //更新欧赔缓存
				jmsZqUtil.letgoalCacheUpdate(String.valueOf(scheduleId)); //亚赔缓存更新
				jmsZqUtil.scheduleUpdate(String.valueOf(scheduleId)); //赛事更新
			}
		}
		String event = schedule.getEvent();
		logger.info("彩票赛事与球探网的关联更新跟踪event,event="+event);
		//发送event增加的Jms
		if (eventModify && StringUtils.isNotBlank(event)) {
			jmsSendUtil.scheduleEventAdd(event);
		}
		//更新缓存
		if ((eventModify||turnModify) && StringUtils.isNotBlank(event)) {
			jmsZqUtil.schedulesByEventCacheUpdate(event);
		}
	}
	
}
