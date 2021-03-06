// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.domain.lq;

import com.ruyicai.dataanalysis.domain.lq.EuropeCompanyJcl;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import org.springframework.transaction.annotation.Transactional;

privileged aspect EuropeCompanyJcl_Roo_Entity {
    
    declare @type: EuropeCompanyJcl: @Entity;
    
    declare @type: EuropeCompanyJcl: @Table(name = "europecompanyjcl");
    
    @PersistenceContext(unitName = "persistenceUnit")
    transient EntityManager EuropeCompanyJcl.entityManager;
    
    @Transactional("transactionManager")
    public void EuropeCompanyJcl.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            EuropeCompanyJcl attached = EuropeCompanyJcl.findEuropeCompanyJcl(this.companyId);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional("transactionManager")
    public void EuropeCompanyJcl.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional("transactionManager")
    public void EuropeCompanyJcl.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional("transactionManager")
    public EuropeCompanyJcl EuropeCompanyJcl.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EuropeCompanyJcl merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager EuropeCompanyJcl.entityManager() {
        EntityManager em = new EuropeCompanyJcl().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long EuropeCompanyJcl.countEuropeCompanyJcls() {
        return entityManager().createQuery("SELECT COUNT(o) FROM EuropeCompanyJcl o", Long.class).getSingleResult();
    }
    
    public static List<EuropeCompanyJcl> EuropeCompanyJcl.findAllEuropeCompanyJcls() {
        return entityManager().createQuery("SELECT o FROM EuropeCompanyJcl o", EuropeCompanyJcl.class).getResultList();
    }
    
    public static List<EuropeCompanyJcl> EuropeCompanyJcl.findEuropeCompanyJclEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM EuropeCompanyJcl o", EuropeCompanyJcl.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
