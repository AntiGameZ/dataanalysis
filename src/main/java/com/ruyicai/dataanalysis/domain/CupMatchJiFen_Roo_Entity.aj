// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.dataanalysis.domain;

import com.ruyicai.dataanalysis.domain.CupMatchJiFen;
import com.ruyicai.dataanalysis.domain.CupMatchJiFenPK;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import org.springframework.transaction.annotation.Transactional;

privileged aspect CupMatchJiFen_Roo_Entity {
    
    declare @type: CupMatchJiFen: @Entity;
    
    declare @type: CupMatchJiFen: @Table(name = "CupMatchJiFen");
    
    @PersistenceContext(unitName = "persistenceUnit")
    transient EntityManager CupMatchJiFen.entityManager;
    
    @Transactional("transactionManager")
    public void CupMatchJiFen.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional("transactionManager")
    public void CupMatchJiFen.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            CupMatchJiFen attached = CupMatchJiFen.findCupMatchJiFen(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional("transactionManager")
    public void CupMatchJiFen.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional("transactionManager")
    public void CupMatchJiFen.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional("transactionManager")
    public CupMatchJiFen CupMatchJiFen.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CupMatchJiFen merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager CupMatchJiFen.entityManager() {
        EntityManager em = new CupMatchJiFen().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long CupMatchJiFen.countCupMatchJiFens() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CupMatchJiFen o", Long.class).getSingleResult();
    }
    
    public static List<CupMatchJiFen> CupMatchJiFen.findAllCupMatchJiFens() {
        return entityManager().createQuery("SELECT o FROM CupMatchJiFen o", CupMatchJiFen.class).getResultList();
    }
    
    public static CupMatchJiFen CupMatchJiFen.findCupMatchJiFen(CupMatchJiFenPK id) {
        if (id == null) return null;
        return entityManager().find(CupMatchJiFen.class, id);
    }
    
    public static List<CupMatchJiFen> CupMatchJiFen.findCupMatchJiFenEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CupMatchJiFen o", CupMatchJiFen.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
