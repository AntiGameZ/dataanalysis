// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.domain.lq;

import com.ruyicai.dataanalysis.domain.lq.ScheduleJcl;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect ScheduleJcl_Roo_Json {
    
    public static String ScheduleJcl.toJsonArray(Collection<ScheduleJcl> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<ScheduleJcl> ScheduleJcl.fromJsonArrayToScheduleJcls(String json) {
        return new JSONDeserializer<List<ScheduleJcl>>().use(null, ArrayList.class).use("values", ScheduleJcl.class).deserialize(json);
    }
    
}