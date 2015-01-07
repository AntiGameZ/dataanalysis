// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.domain;

import com.ruyicai.dataanalysis.domain.CupMatchRankingPK;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect CupMatchRankingPK_Roo_Json {
    
    public String CupMatchRankingPK.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static CupMatchRankingPK CupMatchRankingPK.fromJsonToCupMatchRankingPK(String json) {
        return new JSONDeserializer<CupMatchRankingPK>().use(null, CupMatchRankingPK.class).deserialize(json);
    }
    
    public static String CupMatchRankingPK.toJsonArray(Collection<CupMatchRankingPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<CupMatchRankingPK> CupMatchRankingPK.fromJsonArrayToCupMatchRankingPKs(String json) {
        return new JSONDeserializer<List<CupMatchRankingPK>>().use(null, ArrayList.class).use("values", CupMatchRankingPK.class).deserialize(json);
    }
    
}
