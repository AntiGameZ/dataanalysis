// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.domain.jcl;

import java.lang.String;

privileged aspect EuropeCompanyJcl_Roo_ToString {
    
    public String EuropeCompanyJcl.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CompanyId: ").append(getCompanyId()).append(", ");
        sb.append("EuropeCompanyJclCache: ").append(getEuropeCompanyJclCache()).append(", ");
        sb.append("NameC: ").append(getNameC()).append(", ");
        sb.append("NameE: ").append(getNameE());
        return sb.toString();
    }
    
}
