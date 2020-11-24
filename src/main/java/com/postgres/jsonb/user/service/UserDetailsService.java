package com.postgres.jsonb.user.service;

import com.postgres.jsonb.user.entity.UserDetails;
import com.postgres.jsonb.user.entity.UserPreference;
import com.postgres.jsonb.user.model.WSUserDetailsRequest;
import com.postgres.jsonb.user.model.WSUserDetailsResponse;
import com.postgres.jsonb.user.model.WSUserPreference;
import com.postgres.jsonb.user.repository.UserDetailsRepository;
import com.postgres.jsonb.user.util.FrequencyEnum;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created on 11/November/2020 By Author Eresh, Gorantla
 **/
@Service
public class UserDetailsService {

	final UserDetailsRepository userDetailsRepository;

	@Autowired
	@Qualifier("auditHistory")
	ExecutorService auditExecutorService;

	public UserDetailsService(UserDetailsRepository userDetailsRepository) {
		this.userDetailsRepository = userDetailsRepository;
	}

	public void dataSeed() {
		/*for (int index = 0; index < 4; index++) {
			CompletableFuture.runAsync(() -> seedData(), auditExecutorService);
		}*/
		seedData();
	}

	public void seedData() {
		List<UserDetails> userDetails = IntStream.range(0, 10000).mapToObj(index  -> this.randomUserDetails(index)).collect(Collectors.toList());
		userDetailsRepository.saveAll(userDetails);
	}

	private UserDetails randomUserDetails(Integer index) {
		UserDetails userDetails = new UserDetails();
		UserPreference userPreference = new UserPreference();
		userPreference.setSmsFlag(index % 5 == 0);
		userPreference.setPushFlag(index % 3 == 0);
		userPreference.setEmailFlag(index % 2 == 0);
		userPreference.setFrequency(generateRandomFrequencyEnum().getFrequency());
		userDetails.setUserPreference(userPreference);
		userDetails.setSecurityNumber(RandomStringUtils.randomAlphabetic(4) + RandomStringUtils.randomNumeric(8) + index);
		userDetails.setLastName(RandomStringUtils.randomAlphabetic(8));
		userDetails.setFirstName(RandomStringUtils.randomAlphabetic(8));
		userDetails.setContactNumber(RandomStringUtils.randomNumeric(10));
		userDetails.setCreatedAt(createRandomDate(2010, 2020));
		return userDetails;
	}

	private int createRandomIntBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	private ZonedDateTime createRandomDate(int startYear, int endYear) {
		int day = createRandomIntBetween(1, 28);
		int month = createRandomIntBetween(1, 12);
		int hour = createRandomIntBetween(0, 23);
		int minutes = createRandomIntBetween(1, 59);
		int seconds = createRandomIntBetween(1, 59);
		int nanoSeconds = createRandomIntBetween(100, 900);
		int year = createRandomIntBetween(startYear, endYear);
		LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minutes, seconds, nanoSeconds);
		return localDateTime.atZone(ZoneId.systemDefault());
	}

	public void deleteUserDetails(Long id) {
		userDetailsRepository.deleteById(id);
	}

	public WSUserDetailsResponse saveUserDetails(WSUserDetailsRequest request) {
			UserDetails userDetails = toUserDetails(request);
			userDetails = userDetailsRepository.save(userDetails);
			return new WSUserDetailsResponse(userDetails);
	}

	public FrequencyEnum generateRandomFrequencyEnum() {
		FrequencyEnum[] frequencyEnums = FrequencyEnum.values();
		Random random = new Random();
		final Integer index = random.ints(0, (frequencyEnums.length - 1)).limit(1).findFirst().getAsInt();
		return frequencyEnums[index];
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