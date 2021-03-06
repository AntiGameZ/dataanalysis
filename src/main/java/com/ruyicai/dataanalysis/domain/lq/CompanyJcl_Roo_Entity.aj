// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.domain.lq;

import com.ruyicai.dataanalysis.domain.lq.CompanyJcl;
import java.lang.Integer;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import org.springframework.transaction.annotation.Transactional;

privileged aspect CompanyJcl_Roo_Entity {
    
    declare @type: CompanyJcl: @Entity;
    
    declare @type: CompanyJcl: @Table(name = "companyjcl");
    
    @PersistenceContext(unitName = "persistenceUnit")
    transient EntityManager CompanyJcl.entityManager;
    
    @Transactional("transactionManager")
    public void CompanyJcl.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            CompanyJcl attached = CompanyJcl.findCompanyJcl(this.companyId);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional("transactionManager")
    public void CompanyJcl.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional("transactionManager")
    public void CompanyJcl.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional("transactionManager")
    public CompanyJcl CompanyJcl.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CompanyJcl merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager CompanyJcl.entityManager() {
        EntityManager em = new CompanyJcl().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long CompanyJcl.countCompanyJcls() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CompanyJcl o", Long.class).getSingleResult();
    }
    
    public static List<CompanyJcl> CompanyJcl.findAllCompanyJcls() {
        return entityManager().createQuery("SELECT o FROM CompanyJcl o", CompanyJcl.class).getResultList();
    }
    
    public static CompanyJcl CompanyJcl.findCompanyJcl(Integer companyId) {
        if (companyId == null) return null;
        return entityManager().find(CompanyJcl.class, companyId);
    }
    
    public static List<CompanyJcl> CompanyJcl.findCompanyJclEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CompanyJcl o", CompanyJcl.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
