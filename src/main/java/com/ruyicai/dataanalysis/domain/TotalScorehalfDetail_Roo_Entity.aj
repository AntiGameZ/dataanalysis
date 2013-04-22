// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.domain;

import com.ruyicai.dataanalysis.domain.TotalScorehalfDetail;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import org.springframework.transaction.annotation.Transactional;

privileged aspect TotalScorehalfDetail_Roo_Entity {
    
    declare @type: TotalScorehalfDetail: @Entity;
    
    declare @type: TotalScorehalfDetail: @Table(name = "TotalScorehalfDetail");
    
    @PersistenceContext(unitName = "persistenceUnit")
    transient EntityManager TotalScorehalfDetail.entityManager;
    
    @Transactional("transactionManager")
    public void TotalScorehalfDetail.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional("transactionManager")
    public void TotalScorehalfDetail.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TotalScorehalfDetail attached = TotalScorehalfDetail.findTotalScorehalfDetail(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional("transactionManager")
    public void TotalScorehalfDetail.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional("transactionManager")
    public void TotalScorehalfDetail.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional("transactionManager")
    public TotalScorehalfDetail TotalScorehalfDetail.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        TotalScorehalfDetail merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager TotalScorehalfDetail.entityManager() {
        EntityManager em = new TotalScorehalfDetail().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long TotalScorehalfDetail.countTotalScorehalfDetails() {
        return entityManager().createQuery("SELECT COUNT(o) FROM TotalScorehalfDetail o", Long.class).getSingleResult();
    }
    
    public static List<TotalScorehalfDetail> TotalScorehalfDetail.findAllTotalScorehalfDetails() {
        return entityManager().createQuery("SELECT o FROM TotalScorehalfDetail o", TotalScorehalfDetail.class).getResultList();
    }
    
    public static TotalScorehalfDetail TotalScorehalfDetail.findTotalScorehalfDetail(int id) {
        return entityManager().find(TotalScorehalfDetail.class, id);
    }
    
    public static List<TotalScorehalfDetail> TotalScorehalfDetail.findTotalScorehalfDetailEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM TotalScorehalfDetail o", TotalScorehalfDetail.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
