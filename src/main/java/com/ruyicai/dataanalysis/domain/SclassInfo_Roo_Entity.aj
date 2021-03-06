// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.domain;

import com.ruyicai.dataanalysis.domain.SclassInfo;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import org.springframework.transaction.annotation.Transactional;

privileged aspect SclassInfo_Roo_Entity {
    
    declare @type: SclassInfo: @Entity;
    
    declare @type: SclassInfo: @Table(name = "SclassInfo");
    
    @PersistenceContext(unitName = "persistenceUnit")
    transient EntityManager SclassInfo.entityManager;
    
    @Transactional("transactionManager")
    public void SclassInfo.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional("transactionManager")
    public void SclassInfo.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            SclassInfo attached = SclassInfo.findSclassInfo(this.infoID);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional("transactionManager")
    public void SclassInfo.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional("transactionManager")
    public void SclassInfo.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional("transactionManager")
    public SclassInfo SclassInfo.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        SclassInfo merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager SclassInfo.entityManager() {
        EntityManager em = new SclassInfo().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long SclassInfo.countSclassInfoes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM SclassInfo o", Long.class).getSingleResult();
    }
    
    public static List<SclassInfo> SclassInfo.findAllSclassInfoes() {
        return entityManager().createQuery("SELECT o FROM SclassInfo o", SclassInfo.class).getResultList();
    }
    
    public static SclassInfo SclassInfo.findSclassInfo(int infoID) {
        return entityManager().find(SclassInfo.class, infoID);
    }
    
    public static List<SclassInfo> SclassInfo.findSclassInfoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM SclassInfo o", SclassInfo.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
