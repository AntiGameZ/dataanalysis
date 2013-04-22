// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.domain;

import com.ruyicai.dataanalysis.domain.DetailResult;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import org.springframework.transaction.annotation.Transactional;

privileged aspect DetailResult_Roo_Entity {
    
    declare @type: DetailResult: @Entity;
    
    declare @type: DetailResult: @Table(name = "DetailResult");
    
    @PersistenceContext(unitName = "persistenceUnit")
    transient EntityManager DetailResult.entityManager;
    
    @Transactional("transactionManager")
    public void DetailResult.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional("transactionManager")
    public void DetailResult.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            DetailResult attached = DetailResult.findDetailResult(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional("transactionManager")
    public void DetailResult.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional("transactionManager")
    public void DetailResult.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional("transactionManager")
    public DetailResult DetailResult.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        DetailResult merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager DetailResult.entityManager() {
        EntityManager em = new DetailResult().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long DetailResult.countDetailResults() {
        return entityManager().createQuery("SELECT COUNT(o) FROM DetailResult o", Long.class).getSingleResult();
    }
    
    public static List<DetailResult> DetailResult.findAllDetailResults() {
        return entityManager().createQuery("SELECT o FROM DetailResult o", DetailResult.class).getResultList();
    }
    
    public static DetailResult DetailResult.findDetailResult(int id) {
        return entityManager().find(DetailResult.class, id);
    }
    
    public static List<DetailResult> DetailResult.findDetailResultEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM DetailResult o", DetailResult.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
