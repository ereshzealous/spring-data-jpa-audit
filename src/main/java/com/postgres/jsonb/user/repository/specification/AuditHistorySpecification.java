package com.postgres.jsonb.user.repository.specification;

import com.postgres.jsonb.user.entity.AuditHistory;
import com.postgres.jsonb.user.vo.AuditHistorySearchVO;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 18/November/2020 By Author Eresh, Gorantla
 **/
public class AuditHistorySpecification implements Specification<AuditHistory> {

	private AuditHistorySearchVO searchVO;

	public AuditHistorySpecification(AuditHistorySearchVO searchVO) {
		this.searchVO = searchVO;
	}

	@Override
	public Predicate toPredicate(Root<AuditHistory> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.and(builder.equal(builder.upper(root.get("name")), StringUtils.upperCase(searchVO.getEntityName()))));
		if (CollectionUtils.isNotEmpty(searchVO.getIds())) {
			predicates.add(builder.and(builder.function("jsonb_extract_path_text", String.class,
			                                                          root.get("content"), builder.literal("id")).in(searchVO.getIds())));
		}
		query.orderBy(builder.desc(root.get("modifiedDate")));
		query.multiselect(root.get("id"), root.get("action"));
		return builder.and(predicates.toArray(new Predicate[predicates.size()]));
	}
}