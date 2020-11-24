package com.postgres.jsonb.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.postgres.jsonb.user.entity.AuditHistory;
import com.postgres.jsonb.user.model.WSAuditHistoryChangeLog;
import com.postgres.jsonb.user.model.WSAuditHistoryDetails;
import com.postgres.jsonb.user.model.WSAuditHistoryRequest;
import com.postgres.jsonb.user.model.WSAuditHistoryResponse;
import com.postgres.jsonb.user.repository.AuditHistoryRepository;
import com.postgres.jsonb.user.repository.specification.UserAuditHistorySpecification;
import com.postgres.jsonb.user.vo.AuditHistorySearchVO;
import com.postgres.jsonb.user.vo.AuditHistoryVO;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created on 16/November/2020 By Author Eresh, Gorantla
 **/
@Service
public class AuditHistoryService {

	final AuditHistoryRepository auditHistoryRepository;
	final ObjectMapper objectMapper;

	public AuditHistoryService(AuditHistoryRepository auditHistoryRepository, @Qualifier("jsonMapper") ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		this.auditHistoryRepository = auditHistoryRepository;
	}

	public AuditHistory getAuditHistory(Long id) {
		return auditHistoryRepository.getOne(id);
	}

	public WSAuditHistoryResponse getAuditHistoryOfUserId(WSAuditHistoryRequest request) {
		WSAuditHistoryResponse response = new WSAuditHistoryResponse();
		List<String> allIds = auditHistoryRepository.findAuditHistoriesByModifiedDate(request.getFromDate(), request.getToDate(), request.getEntityName());
		allIds = allIds.stream().distinct().collect(Collectors.toList());
		AtomicInteger offset = new AtomicInteger();
		Collection<List<String>> collections = allIds.stream()
		                 .collect(Collectors.groupingBy(it -> offset.getAndIncrement() / request.getSize()))
		                 .values();
		List<String> ids = new ArrayList<>();
		Integer index = 0;
		for (Collection collection : collections) {
			if (request.getPage().equals(index)) {
				ids = new ArrayList<>(collection);
				break;
			}
			index++;
		}
		if (CollectionUtils.isNotEmpty(ids)) {
			UserAuditHistorySpecification historySpecification = new UserAuditHistorySpecification(new AuditHistorySearchVO(ids.stream().map(Object::toString).collect(
					Collectors.toList()), request.getEntityName()));
			List<AuditHistory> auditHistories = auditHistoryRepository.findAll(historySpecification);
			List<AuditHistoryVO> auditHistoryVOS;
			if (CollectionUtils.isNotEmpty(auditHistories)) {
				List<WSAuditHistoryDetails> historyDetails = new ArrayList<>();
				auditHistoryVOS = auditHistories.stream().map(AuditHistoryVO::new).collect(Collectors.toList());
				for (String id : ids) {
					WSAuditHistoryDetails auditHistoryDetails = null;
					List<AuditHistoryVO> historyVOS = auditHistoryVOS.stream().filter(data -> id.equalsIgnoreCase((String) data.getContent().get("id"))).collect(
							Collectors.toList());
					if (CollectionUtils.isNotEmpty(historyVOS)) {
						List<WSAuditHistoryChangeLog> changeLogs = new ArrayList<>();
						auditHistoryDetails = new WSAuditHistoryDetails();
						auditHistoryDetails.setEntity(historyVOS.get(0).getEntityName());
						auditHistoryDetails.setEntityId(id);
						WSAuditHistoryChangeLog firstRecordChangeLog = createChangeLogForFirstRecord(auditHistoryVOS.get(0));
						changeLogs.add(firstRecordChangeLog);
						for (int indexValue = 1; indexValue < auditHistories.size(); indexValue++) {
							WSAuditHistoryChangeLog auditHistoryChangeLog = createChangeLogForAlternateRecords(auditHistoryVOS.get(indexValue - 1),
							                                                                                   auditHistoryVOS.get(indexValue));
							changeLogs.add(auditHistoryChangeLog);
						}
						auditHistoryDetails.setChangeLogs(changeLogs);
					}
					historyDetails.add(auditHistoryDetails);
				}
				response.setHistoryDetails(historyDetails);
				response.setTotalRecords(NumberUtils.toLong(String.valueOf(allIds.size())));
			}
		}
		return response;
	}

	public Long getIdFromMap(Map<String, Object> map) {
		return Long.valueOf((Integer) map.getOrDefault("id", NumberUtils.LONG_MINUS_ONE));
	}

	private WSAuditHistoryChangeLog createChangeLogForFirstRecord(AuditHistoryVO auditHistory) {
		WSAuditHistoryChangeLog historyChangeLog = new WSAuditHistoryChangeLog();
		historyChangeLog.setData(auditHistory.getContent());
		historyChangeLog.setChanges(auditHistory.getContent().keySet().stream()
		                                   .collect(Collectors.toList()));
		historyChangeLog.setModifiedDate(auditHistory.getModifiedDate());
		historyChangeLog.setAuditAction(auditHistory.getAction().toString());
		return historyChangeLog;
	}

	private WSAuditHistoryChangeLog createChangeLogForAlternateRecords(AuditHistoryVO leftBoundary, AuditHistoryVO rightBoundary) {
		WSAuditHistoryChangeLog auditHistoryChangeLog = new WSAuditHistoryChangeLog();
		Map<String, Object> leftBoundaryMap = leftBoundary.getContent();
		Map<String, Object> rightBoundaryMap = rightBoundary.getContent();
		auditHistoryChangeLog.setData(rightBoundaryMap);
		auditHistoryChangeLog.setModifiedDate(rightBoundary.getModifiedDate());
		MapDifference<String, Object> difference = Maps.difference(leftBoundaryMap, rightBoundaryMap);
		auditHistoryChangeLog.setChanges(difference.entriesDiffering().keySet().stream().collect(Collectors.toList()));
		auditHistoryChangeLog.setAuditAction(rightBoundary.getAction().toString());
		return auditHistoryChangeLog;
	}

	private Map<String, Object> serializeAuditContentAsMap(JsonNode entityContent) {
		TypeReference<Map<String, Object>> type = new TypeReference<Map<String, Object>>() {};
		Map<String, Object> mapData = new HashMap<>();
		try {
			mapData = objectMapper.readValue(objectMapper.writeValueAsString(entityContent), type);
		} catch (JsonProcessingException e) {

		}
		return mapData;
	}

}