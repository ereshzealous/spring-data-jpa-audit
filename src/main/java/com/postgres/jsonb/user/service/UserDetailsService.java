package com.postgres.jsonb.user.service;

import com.postgres.jsonb.user.repository.UserDetailsRepository;
import com.postgres.jsonb.user.entity.UserDetails;
import com.postgres.jsonb.user.entity.UserPreference;
import com.postgres.jsonb.user.model.WSUserDetailsRequest;
import com.postgres.jsonb.user.model.WSUserDetailsResponse;
import com.postgres.jsonb.user.model.WSUserPreference;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created on 11/November/2020 By Author Eresh, Gorantla
 **/
@Service
public class UserDetailsService {

	final UserDetailsRepository userDetailsRepository;

	public UserDetailsService(UserDetailsRepository userDetailsRepository) {
		this.userDetailsRepository = userDetailsRepository;
	}

	public void dataSeed() {
		List<UserDetails> userDetails = IntStream.range(0, 5000).mapToObj(index  -> this.randomUserDetails(index)).collect(Collectors.toList());
		userDetailsRepository.saveAll(userDetails);
	}

	private UserDetails randomUserDetails(Integer index) {
		UserDetails userDetails = new UserDetails();
		userDetails.setUserPreference(new UserPreference());
		userDetails.setSecurityNumber(RandomStringUtils.randomAlphabetic(4) + RandomStringUtils.randomNumeric(8) + index);
		userDetails.setLastName(RandomStringUtils.randomAlphabetic(8));
		userDetails.setFirstName(RandomStringUtils.randomAlphabetic(8));
		userDetails.setContactNumber(RandomStringUtils.randomNumeric(10));
		return userDetails;
	}

	public void deleteUserDetails(Long id) {
		userDetailsRepository.deleteById(id);
	}

	public WSUserDetailsResponse saveUserDetails(WSUserDetailsRequest request) {
			UserDetails userDetails = toUserDetails(request);
			userDetails = userDetailsRepository.save(userDetails);
			return new WSUserDetailsResponse(userDetails);

	}

	private UserDetails toUserDetails(WSUserDetailsRequest request) {
		UserDetails userDetails = null;
		if (request.getId() == null) {
			userDetails = new UserDetails();
		} else {
			userDetails = userDetailsRepository.getOne(request.getId());
		}
		userDetails.setContactNumber(request.getContactNumber());
		userDetails.setFirstName(request.getFirstName());
		userDetails.setLastName(request.getLastName());
		userDetails.setSecurityNumber(request.getSecurityNumber());
		userDetails.setUserPreference(toUserPreference(request.getPreference() == null ? new WSUserPreference() : request.getPreference(),
		                                               userDetails.getUserPreference()));
		return userDetails;
	}

	private UserPreference toUserPreference(WSUserPreference preference, UserPreference userPreference) {
		if (userPreference == null) {
			userPreference = new UserPreference();
		}
		userPreference.setEmailFlag(preference.getEmailFlag());
		userPreference.setFrequency(preference.getFrequency());
		userPreference.setPushFlag(preference.getPushFlag());
		userPreference.setSmsFlag(preference.getSmsFlag());
		return userPreference;
	}
}