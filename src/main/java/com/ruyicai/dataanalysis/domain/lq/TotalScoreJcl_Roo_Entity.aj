// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.domain.lq;

import com.ruyicai.dataanalysis.domain.lq.TotalScoreJcl;
import java.lang.Integer;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import org.springframework.transaction.annotation.Transactional;

privileged aspect TotalScoreJcl_Roo_Entity {
    
    declare @type: TotalScoreJcl: @Entity;
    
    declare @type: TotalScoreJcl: @Table(name = "totalscorejcl");
    
    @PersistenceContext(unitName = "persistenceUnit")
    transient EntityManager TotalScoreJcl.entityManager;
    
    @Transactional("transactionManager")
    public void TotalScoreJcl.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional("transactionManager")
    public void TotalScoreJcl.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TotalScoreJcl attached = TotalScoreJcl.findTotalScoreJcl(this.oddsId);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional("transactionManager")
    public void TotalScoreJcl.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional("transactionManager")
    public void TotalScoreJcl.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional("transactionManager")
    public TotalScoreJcl TotalScoreJcl.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        TotalScoreJcl merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager TotalScoreJcl.entityManager() {
        EntityManager em = new TotalScoreJcl().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long TotalScoreJcl.countTotalScoreJcls() {
        return entityManager().createQuery("SELECT COUNT(o) FROM TotalScoreJcl o", Long.class).getSingleResult();
    }
    
    public static List<TotalScoreJcl> TotalScoreJcl.findAllTotalScoreJcls() {
        return entityManager().createQuery("SELECT o FROM TotalScoreJcl o", TotalScoreJcl.class).getResultList();
    }
    
    public static TotalScoreJcl TotalScoreJcl.findTotalScoreJcl(Integer oddsId) {
        if (oddsId == null) return null;
        return entityManager().find(TotalScoreJcl.class, oddsId);
    }
    
    public static List<TotalScoreJcl> TotalScoreJcl.findTotalScoreJclEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM TotalScoreJcl o", TotalScoreJcl.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
