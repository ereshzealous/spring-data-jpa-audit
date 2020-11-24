package com.postgres.jsonb.user.repository.specification;

import com.postgres.jsonb.user.entity.UserDetails;
import com.postgres.jsonb.user.vo.UserDetailsSearchVO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 24/November/2020 By Author Eresh, Gorantla
 **/
public class UserDetailsSpecification implements Specification<UserDetails> {

	private UserDetailsSearchVO searchVO;

	public UserDetailsSpecification(UserDetailsSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	@Override
	public Predicate toPredicate(Root<UserDetails> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();
		if (searchVO.getFromDate() != null && searchVO.getToDate() != null) {
			predicates.add(builder.and(builder.greaterThanOrEqualTo(root.get("createdAt"), searchVO.getFromDate()),
			                           builder.lessThanOrEqualTo(root.get("createdAt"), searchVO.getToDate())));
		}
		if (StringUtils.isNotBlank(this.searchVO.getQuery())) {
			predicates.add(builder.or(builder.like(builder.upper(root.get("firstName")), StringUtils.upperCase("%" + searchVO.getQuery() + "%")),
			                          builder.like(builder.upper(root.get("lastName")), StringUtils.upperCase("%" + searchVO.getQuery() + "%")),
			                          builder.like(builder.upper(root.get("securityNumber")), StringUtils.upperCase("%" + searchVO.getQuery() + "%"))));
		}
		if (this.searchVO.getSmsPreference() != null) {
			predicates.add(builder.and(builder.equal(builder.function("jsonb_extract_path_text", String.class,
			                                                          root.get("userPreference"), builder.literal("smsFlag")), searchVO.getSmsPreference().toString())));
		}
		if (this.searchVO.getPushPreference() != null) {
			predicates.add(builder.and(builder.equal(builder.function("jsonb_extract_path_text", String.class,
			                                                          root.get("userPreference"), builder.literal("pushFlag")), searchVO.getPushPreference().toString())));
		}
		if (this.searchVO.getEmailPreference() != null) {
			predicates.add(builder.and(builder.equal(builder.function("jsonb_extract_path_text", String.class,
			                                                          root.get("userPreference"), builder.literal("emailFlag")), searchVO.getEmailPreference().toString())));
		}
		if (StringUtils.isNotBlank(searchVO.getFrequency())) {
			predicates.add(builder.and(builder.equal(builder.function("jsonb_extract_path_text", String.class,
			                                                          root.get("userPreference"), builder.literal("frequency")), searchVO.getFrequency())));
		}
		query.orderBy(builder.desc(root.get("createdAt")));
		return builder.and(predicates.toArray(new Predicate[predicates.size()]));
	}
}