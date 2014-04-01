// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.dto;

import com.ruyicai.dataanalysis.dto.KaiLiDto;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect KaiLiDto_Roo_Json {
    
    public String KaiLiDto.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static KaiLiDto KaiLiDto.fromJsonToKaiLiDto(String json) {
        return new JSONDeserializer<KaiLiDto>().use(null, KaiLiDto.class).deserialize(json);
    }
    
    public static String KaiLiDto.toJsonArray(Collection<KaiLiDto> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<KaiLiDto> KaiLiDto.fromJsonArrayToKaiLiDtoes(String json) {
        return new JSONDeserializer<List<KaiLiDto>>().use(null, ArrayList.class).use("values", KaiLiDto.class).deserialize(json);
    }
    
}
