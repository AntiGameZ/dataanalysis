// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.dto;

import com.ruyicai.dataanalysis.dto.LetgoalDto;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect LetgoalDto_Roo_Json {
    
    public String LetgoalDto.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static LetgoalDto LetgoalDto.fromJsonToLetgoalDto(String json) {
        return new JSONDeserializer<LetgoalDto>().use(null, LetgoalDto.class).deserialize(json);
    }
    
    public static String LetgoalDto.toJsonArray(Collection<LetgoalDto> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<LetgoalDto> LetgoalDto.fromJsonArrayToLetgoalDtoes(String json) {
        return new JSONDeserializer<List<LetgoalDto>>().use(null, ArrayList.class).use("values", LetgoalDto.class).deserialize(json);
    }
    
}
