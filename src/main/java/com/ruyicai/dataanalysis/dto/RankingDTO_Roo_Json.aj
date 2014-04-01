// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.dto;

import com.ruyicai.dataanalysis.dto.RankingDTO;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect RankingDTO_Roo_Json {
    
    public String RankingDTO.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static RankingDTO RankingDTO.fromJsonToRankingDTO(String json) {
        return new JSONDeserializer<RankingDTO>().use(null, RankingDTO.class).deserialize(json);
    }
    
    public static String RankingDTO.toJsonArray(Collection<RankingDTO> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<RankingDTO> RankingDTO.fromJsonArrayToRankingDTO(String json) {
        return new JSONDeserializer<List<RankingDTO>>().use(null, ArrayList.class).use("values", RankingDTO.class).deserialize(json);
    }
    
}