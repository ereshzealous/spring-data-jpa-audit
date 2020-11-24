package com.postgres.jsonb.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.postgres.jsonb.user.entity.AuditHistory;
import com.postgres.jsonb.user.model.WSAuditHistoryChangeLog;
import com.postgres.jsonb.user.model.WSAuditHistoryDetails;
import com.postgres.jsonb.user.model.WSAuditHistoryRequest;
import com.postgres.jsonb.user.model.WSAuditHistoryResponse;
import com.postgres.jsonb.user.repository.AuditHistoryRepository;
import com.postgres.jsonb.user.repository.specification.AuditHistorySpecification;
import com.postgres.jsonb.user.vo.AuditHistorySearchVO;
import com.postgres.jsonb.user.vo.AuditHistoryVO;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

	private List<String> retrieveIdsForFilterQuery(WSAuditHistoryRequest request) {
		String entityName = StringUtils.upperCase(request.getEntityName());
		request.setEntityName(entityName);
		if (request.getFromDate() != null && request.getToDate() != null && StringUtils.isBlank(request.getQuery())) {
			return auditHistoryRepository.findAuditHistoriesByModifiedDate(request.getFromDate(), request.getToDate(),
			                                                               StringUtils.upperCase(request.getEntityName()));
		}
		if (request.getFromDate() == null && StringUtils.isNotBlank(request.getQuery())) {
			request.setQuery("%" + request.getQuery() + "%");
			request.setEntityName("%" + entityName + "%");
			return auditHistoryRepository.findAuditHistoryByQuery(request.getQuery(), request.getEntityName());
		}
		if (request.getFromDate() != null && request.getToDate() != null && StringUtils.isNotBlank(request.getQuery())) {
			request.setQuery("%" + request.getQuery() + "%");
			request.setEntityName("%" + entityName + "%");
			return auditHistoryRepository.findAuditHistoryByQueryAndModifiedDate(request.getQuery(), request.getEntityName(),
			                                                                     request.getFromDate(), request.getToDate());
		}
		return Collections.emptyList();
	}

	public WSAuditHistoryResponse getAuditHistoryOfUserId(WSAuditHistoryRequest request) {
		WSAuditHistoryResponse response = new WSAuditHistoryResponse();
		String entityName = StringUtils.upperCase(request.getEntityName());
		List<String> allIds = retrieveIdsForFilterQuery(request);
		request.setEntityName(entityName);
		allIds = allIds.stream().distinct().collect(Collectors.toList());
		List<String> ids = getIdsFromGrouping(request, allIds);
		if (CollectionUtils.isNotEmpty(ids)) {
			List<AuditHistory> auditHistories = getAuditHistories(request, ids);
			List<AuditHistoryVO> auditHistoryVOS;
			List<WSAuditHistoryDetails> historyDetails = new ArrayList<>();
			auditHistoryVOS = auditHistories.stream().map(AuditHistoryVO::new).collect(Collectors.toList());
			for (String id : ids) {
				List<AuditHistoryVO> historyVOS = auditHistoryVOS.stream().filter(data -> id.equalsIgnoreCase((String) data.getContent().get("id"))).
						collect(Collectors.toList());
				WSAuditHistoryDetails auditHistoryDetails = createAuditHistoryDetails(auditHistories, auditHistoryVOS, id, historyVOS);
				historyDetails.add(auditHistoryDetails);
			}
			response.setHistoryDetails(historyDetails);
			response.setTotalRecords(NumberUtils.toLong(String.valueOf(allIds.size())));
		}
		return response;
	}

	private WSAuditHistoryDetails createAuditHistoryDetails(List<AuditHistory> auditHistories, List<AuditHistoryVO> auditHistoryVOS, String id,
	                                                        List<AuditHistoryVO> historyVOS) {
		WSAuditHistoryDetails auditHistoryDetails = null;
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
		return auditHistoryDetails;
	}

	private List<AuditHistory> getAuditHistories(WSAuditHistoryRequest request, List<String> ids) {
		AuditHistorySearchVO auditHistorySearchVO = new AuditHistorySearchVO();
		auditHistorySearchVO.setEntityName(request.getEntityName());
		auditHistorySearchVO.setIds(ids.stream().map(Object::toString).collect(Collectors.toList()));
		AuditHistorySpecification historySpecification = new AuditHistorySpecification(auditHistorySearchVO);
		List<AuditHistory> auditHistories = auditHistoryRepository.findAll(historySpecification);
		return auditHistories;
	}

	private List<String> getIdsFromGrouping(WSAuditHistoryRequest request, List<String> allIds) {
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
		return ids;
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
}