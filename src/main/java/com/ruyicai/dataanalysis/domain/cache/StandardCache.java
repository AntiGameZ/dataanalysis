package com.ruyicai.dataanalysis.domain.cache;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruyicai.dataanalysis.cache.CacheService;
import com.ruyicai.dataanalysis.domain.Standard;
import com.ruyicai.dataanalysis.util.StringUtil;

@Service
public class StandardCache {

	@Autowired
	private CacheService cacheService;
	
	public Standard getStandard(Integer scheduleId, Integer companyId) {
		String key = StringUtil.join("_", "dadaanalysis", "Standard", String.valueOf(scheduleId), String.valueOf(companyId));
		String value = cacheService.get(key);
		Standard standard = null;
		if (StringUtils.isBlank(value)) {
			standard = Standard.findByScheduleIdCompanyId(scheduleId, companyId);
			if (standard!=null) {
				cacheService.set(key, 30*24*60*60, standard.toJson());
			}
		} else {
			standard = Standard.fromJsonToStandard(value);
		}
		return standard;
	}
	
	public void setToMemcache(Standard standard) {
		String key = StringUtil.join("_", "dadaanalysis", "Standard", String.valueOf(standard.getScheduleID()), String.valueOf(standard.getCompanyID()));
		cacheService.set(key, 30*24*60*60, standard.toJson());
	}
	
}