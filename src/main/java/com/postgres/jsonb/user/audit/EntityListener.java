package com.postgres.jsonb.user.audit;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.postgres.jsonb.user.config.CustomObjectMapper;
import com.postgres.jsonb.user.entity.AuditHistory;
import com.postgres.jsonb.user.repository.AuditHistoryRepository;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.MANDATORY;

public class EntityListener  {

	private ObjectMapper objectMapper;


	public EntityListener() {
		this.objectMapper = new CustomObjectMapper();
	}

    @PostPersist
    public void postPersist(Auditable<Long> target)  {
    	perform(new AuditEntry(target.getClass().getSimpleName(),target,Action.INSERTED));
    }

    @PreUpdate
    public void preUpdate(Auditable<Long> target) {

    	perform(new AuditEntry(target.getClass().getSimpleName(),target,Action.UPDATED));
    }

    @PreRemove
    public void preRemove(Auditable<Long> target) {
    	perform(new AuditEntry(target.getClass().getSimpleName(),target,Action.DELETED));
    }
    
    @Transactional(MANDATORY)
    void perform(AuditEntry entry) {
    	AuditHistoryRepository auditHistoryRepository = ContextUtil.getBean("auditHistoryRepository", AuditHistoryRepository.class);
    	AuditHistory history =  new AuditHistory(entry);
    	auditHistoryRepository.save(history);
    }
}