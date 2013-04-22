package com.ruyicai.dataanalysis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruyicai.dataanalysis.service.AnalysisService;
import com.ruyicai.dataanalysis.service.GlobalInfoService;
import com.ruyicai.dataanalysis.service.UpdateDetailResultService;
import com.ruyicai.dataanalysis.service.UpdateLetgoalStandardService;
import com.ruyicai.dataanalysis.service.UpdateQiuTanMatchesService;
import com.ruyicai.dataanalysis.service.UpdateScheduleService;
import com.ruyicai.dataanalysis.service.UpdateSclassService;
import com.ruyicai.dataanalysis.service.UpdateScoreService;
import com.ruyicai.dataanalysis.service.UpdateStandardService;
import com.ruyicai.dataanalysis.service.UpdateTeamService;

@RequestMapping("/system")
@Controller
public class SystemController {
	
	private Logger logger = LoggerFactory.getLogger(SystemController.class);
	
	@Autowired
	private UpdateSclassService updateSclassService;
	
	@Autowired
	private UpdateTeamService updateTeamService;
	
	@Autowired
	private UpdateLetgoalStandardService updateLetgoalStandardService;
	
	@Autowired
	private UpdateDetailResultService updateDetailResultService;
	
	@Autowired
	private UpdateQiuTanMatchesService updateQiuTanMatchesService;

	@Autowired
	private UpdateScheduleService updateScheduleService;
	
	@Autowired
	private UpdateStandardService updateStandardService;
	
	@Autowired
	private UpdateScoreService updateScoreService;
	
	@Autowired
	private AnalysisService analysisService;
	
	@Autowired
	private GlobalInfoService globalInfoService;
	
	@RequestMapping(value = "/updatesclass", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData updatesclass() {
		ResponseData rd = new ResponseData();
		try {
			updateSclassService.process();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping(value = "/updateteam", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData updateteam() {
		ResponseData rd = new ResponseData();
		try {
			updateTeamService.process();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping(value = "/updatepeiluall", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData updateletgoalpeilu() {
		ResponseData rd = new ResponseData();
		try {
			updateLetgoalStandardService.process();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping(value = "/updatedetailresult", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData updatedetailresult() {
		ResponseData rd = new ResponseData();
		try {
			updateDetailResultService.process();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping(value = "/updateDetailResultByDate", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData updateDetailResultByDate(@RequestParam("date") String date) {
		ResponseData rd = new ResponseData();
		try {
			updateDetailResultService.processByDate(date);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping(value = "/updateqiutanmatches", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData updateqiutanmatches() {
		ResponseData rd = new ResponseData();
		try {
			updateQiuTanMatchesService.process();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping(value = "/updateschedule", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData updateschedule() {
		ResponseData rd = new ResponseData();
		try {
			updateScheduleService.process();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping(value = "/updateschedulemore", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData updateschedulemore(@RequestParam("count") int count,
			@RequestParam("mode") int mode) {
		ResponseData rd = new ResponseData();
		try {
			updateScheduleService.processCount(count, mode);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping(value = "/updatestandard", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData updatestandard() {
		ResponseData rd = new ResponseData();
		try {
			updateStandardService.process();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping(value = "/updatescore", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData updatescore() {
		ResponseData rd = new ResponseData();
		try {
			updateScoreService.process();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping(value = "/getAllScheduleBySclass", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getAllScheduleBySclass() {
		ResponseData rd = new ResponseData();
		try {
			updateScheduleService.getAllScheduleBySclass();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping(value = "/updateAllRanking", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData updateAllRanking() {
		ResponseData rd = new ResponseData();
		try {
			analysisService.updateAllRanking();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping(value = "/updateRanking", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData updateRanking(@RequestParam("sclassID") int sclassID) {
		ResponseData rd = new ResponseData();
		try {
			rd.setValue(analysisService.getRanking(false, sclassID));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping(value = "/processFile", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData processFile(@RequestParam("filename") String filename) {
		ResponseData rd = new ResponseData();
		try {
			updateQiuTanMatchesService.processFile(filename);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping(value = "/updateImmediateScore", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData updateImmediateScore(@RequestParam("event") String event) {
		ResponseData rd = new ResponseData();
		try {
			globalInfoService.updateImmediateScore(event);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return rd;
	}
}
