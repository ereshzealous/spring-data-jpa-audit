package com.postgres.jsonb.user.audit;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postgres.jsonb.user.config.CustomObjectMapper;
import com.postgres.jsonb.user.entity.AuditHistory;
import com.postgres.jsonb.user.entity.UserDetails;
import com.postgres.jsonb.user.repository.AuditHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static javax.transaction.Transactional.TxType.MANDATORY;

public class EntityListener  {

	private ObjectMapper objectMapper;

	@Autowired
	@Qualifier("auditHistory")
	ExecutorService auditExecutorService;


	public EntityListener() {
		ObjectMapper objectMapper = new CustomObjectMapper();
		objectMapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
		this.objectMapper = objectMapper;
	}

    @PostPersist
    public void postPersist(Auditable<Long> target)  {
    	perform(new AuditEntry(target.getClass().getSimpleName(),target,Action.INSERTED));
    }

    @PostUpdate
    public void postUpdate(Auditable<Long> target) {

    	perform(new AuditEntry(target.getClass().getSimpleName(),target,Action.UPDATED));
    }

    @PostRemove
    public void postRemove(Auditable<Long> target) {
    	perform(new AuditEntry(target.getClass().getSimpleName(),target,Action.DELETED));
    }
    
    @Transactional(MANDATORY)
    void perform(AuditEntry entry) {
	    CompletableFuture.runAsync(() -> saveAuditHistory(entry), auditExecutorService);
	    //saveAuditHistory(entry);
    }

    public void saveAuditHistory(AuditEntry entry) {
	    AuditHistoryRepository auditHistoryRepository = ContextUtil.getBean("auditHistoryRepository", AuditHistoryRepository.class);
	    AuditHistory history =  new AuditHistory(entry);
	    UserDetails userDetails = (UserDetails) entry.getContent();
	    history.setContent(generateEntityContent(entry.getContent()));
	    history.setModifiedDate(userDetails.getCreatedAt());
	    auditHistoryRepository.save(history);
    }

	private JsonNode generateEntityContent(Object object) {
		JsonNode jsonNode = objectMapper.convertValue(object, JsonNode.class);
		return jsonNode;
	}
}