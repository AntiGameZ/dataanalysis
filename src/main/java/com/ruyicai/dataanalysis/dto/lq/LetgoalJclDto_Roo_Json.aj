// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.dto.lq;

import com.ruyicai.dataanalysis.dto.lq.LetgoalJclDto;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect LetgoalJclDto_Roo_Json {
    
    public String LetgoalJclDto.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static LetgoalJclDto LetgoalJclDto.fromJsonToLetgoalJclDto(String json) {
        return new JSONDeserializer<LetgoalJclDto>().use(null, LetgoalJclDto.class).deserialize(json);
    }
    
    public static String LetgoalJclDto.toJsonArray(Collection<LetgoalJclDto> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<LetgoalJclDto> LetgoalJclDto.fromJsonArrayToLetgoalJclDtoes(String json) {
        return new JSONDeserializer<List<LetgoalJclDto>>().use(null, ArrayList.class).use("values", LetgoalJclDto.class).deserialize(json);
    }
    
}
