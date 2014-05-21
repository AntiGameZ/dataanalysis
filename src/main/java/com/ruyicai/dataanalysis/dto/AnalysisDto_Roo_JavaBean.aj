// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.dto;

import com.ruyicai.dataanalysis.dto.RankingDTO;
import com.ruyicai.dataanalysis.dto.ScheduleDTO;
import java.util.Collection;

privileged aspect AnalysisDto_Roo_JavaBean {
    
    public ScheduleDTO AnalysisDto.getSchedule() {
        return this.schedule;
    }
    
    public void AnalysisDto.setSchedule(ScheduleDTO schedule) {
        this.schedule = schedule;
    }
    
    public Collection<ScheduleDTO> AnalysisDto.getHomePreSchedules() {
        return this.homePreSchedules;
    }
    
    public void AnalysisDto.setHomePreSchedules(Collection<ScheduleDTO> homePreSchedules) {
        this.homePreSchedules = homePreSchedules;
    }
    
    public Collection<ScheduleDTO> AnalysisDto.getGuestPreSchedules() {
        return this.guestPreSchedules;
    }
    
    public void AnalysisDto.setGuestPreSchedules(Collection<ScheduleDTO> guestPreSchedules) {
        this.guestPreSchedules = guestPreSchedules;
    }
    
    public Collection<ScheduleDTO> AnalysisDto.getHomeAfterSchedules() {
        return this.homeAfterSchedules;
    }
    
    public void AnalysisDto.setHomeAfterSchedules(Collection<ScheduleDTO> homeAfterSchedules) {
        this.homeAfterSchedules = homeAfterSchedules;
    }
    
    public Collection<ScheduleDTO> AnalysisDto.getGuestAfterSchedules() {
        return this.guestAfterSchedules;
    }
    
    public void AnalysisDto.setGuestAfterSchedules(Collection<ScheduleDTO> guestAfterSchedules) {
        this.guestAfterSchedules = guestAfterSchedules;
    }
    
    public Collection<ScheduleDTO> AnalysisDto.getPreClashSchedules() {
        return this.preClashSchedules;
    }
    
    public void AnalysisDto.setPreClashSchedules(Collection<ScheduleDTO> preClashSchedules) {
        this.preClashSchedules = preClashSchedules;
    }
    
    public Collection<RankingDTO> AnalysisDto.getRankings() {
        return this.rankings;
    }
    
    public void AnalysisDto.setRankings(Collection<RankingDTO> rankings) {
        this.rankings = rankings;
    }
    
}