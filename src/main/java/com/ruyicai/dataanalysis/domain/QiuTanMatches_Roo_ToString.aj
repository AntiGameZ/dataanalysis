// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.domain;

import java.lang.String;

privileged aspect QiuTanMatches_Roo_ToString {
    
    public String QiuTanMatches.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Away: ").append(getAway()).append(", ");
        sb.append("AwayID: ").append(getAwayID()).append(", ");
        sb.append("Event: ").append(getEvent()).append(", ");
        sb.append("Home: ").append(getHome()).append(", ");
        sb.append("HomeID: ").append(getHomeID()).append(", ");
        sb.append("ID_bet007: ").append(getID_bet007()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("IssueNum: ").append(getIssueNum()).append(", ");
        sb.append("LotteryName: ").append(getLotteryName()).append(", ");
        sb.append("MatchId: ").append(getMatchId()).append(", ");
        sb.append("Time: ").append(getTime()).append(", ");
        sb.append("ZcBqcEvent: ").append(getZcBqcEvent()).append(", ");
        sb.append("ZcJqcEvent: ").append(getZcJqcEvent()).append(", ");
        sb.append("ZcSfcEvent: ").append(getZcSfcEvent());
        return sb.toString();
    }
    
}
