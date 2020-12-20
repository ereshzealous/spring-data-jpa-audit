package com.postgres.jsonb.user.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postgres.jsonb.settings.cache.SettingsCacheConfig;
import com.postgres.jsonb.settings.entity.Settings;
import com.postgres.jsonb.user.config.CustomObjectMapper;
import com.postgres.jsonb.user.entity.UserDetails;
import com.postgres.jsonb.user.entity.UserPreference;
import com.postgres.jsonb.user.model.WSSaveUserDetailsRequest;
import com.postgres.jsonb.user.model.WSUserDetails;
import com.postgres.jsonb.user.model.WSUserDetailsRequest;
import com.postgres.jsonb.user.model.WSUserDetailsResponse;
import com.postgres.jsonb.user.model.WSUserPreference;
import com.postgres.jsonb.user.model.WSUserSearchResponse;
import com.postgres.jsonb.user.repository.UserDetailsRepository;
import com.postgres.jsonb.user.repository.specification.UserDetailsSpecification;
import com.postgres.jsonb.user.util.CommonUtils;
import com.postgres.jsonb.user.util.Crop;
import com.postgres.jsonb.user.util.Disability;
import com.postgres.jsonb.user.util.FarmSize;
import com.postgres.jsonb.user.util.FrequencyEnum;
import com.postgres.jsonb.user.util.Gender;
import com.postgres.jsonb.user.util.Hobby;
import com.postgres.jsonb.user.util.MaritalStatus;
import com.postgres.jsonb.user.util.Profession;
import com.postgres.jsonb.user.validation.ValidationUtil;
import com.postgres.jsonb.user.vo.UserDetailsSearchVO;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	final ExecutorService auditExecutorService;

	final ValidationUtil validationUtil;

	private final SettingsCacheConfig settingsCacheConfig;

	private ObjectMapper objectMapper;

	public UserDetailsService(UserDetailsRepository userDetailsRepository, @Qualifier("auditHistory") ExecutorService auditExecutorService,
	                          ValidationUtil validationUtil, SettingsCacheConfig settingsCacheConfig) {
		ObjectMapper objectMapper = new CustomObjectMapper();
		objectMapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
		this.objectMapper = objectMapper;
		this.userDetailsRepository = userDetailsRepository;
		this.auditExecutorService = auditExecutorService;
		this.validationUtil = validationUtil;
		this.settingsCacheConfig = settingsCacheConfig;
	}

	public void dataSeed() {
		for (int index = 0; index < 10; index++) {
			CompletableFuture.runAsync(() -> seedData(), auditExecutorService);
		}
		//seedData();
	}

	public void seedData() {
		List<UserDetails> userDetails = IntStream.range(0, 25000).mapToObj(index  -> this.randomUserDetails(index)).collect(Collectors.toList());
		userDetailsRepository.saveAll(userDetails);
	}

	public WSUserSearchResponse searchUsers(UserDetailsSearchVO searchVO) {
		WSUserSearchResponse response = new WSUserSearchResponse();
		Page<UserDetails> userDetailsPage = userDetailsRepository.findAll(new UserDetailsSpecification(searchVO), PageRequest.of(searchVO.getPage(), searchVO.getSize()));
		if (userDetailsPage != null) {
			List<UserDetails> userDetails = userDetailsPage.getContent();
			List<WSUserDetails> details = userDetails.stream().map(WSUserDetails::new).collect(Collectors.toList());
			response.setTotalRecords(userDetailsPage.getTotalElements());
			response.setUserDetails(details);
		}
		return response;
	}

	private UserDetails randomUserDetails(Integer index) {
		UserDetails userDetails = new UserDetails();
		String country = "Kenya";
		userDetails.setDetails(objectMapper.convertValue(generateDetails(country), JsonNode.class));
		userDetails.setSecurityNumber(RandomStringUtils.randomAlphabetic(4) + RandomStringUtils.randomNumeric(8) + index);
		userDetails.setLastName(RandomStringUtils.randomAlphabetic(8));
		userDetails.setFirstName(RandomStringUtils.randomAlphabetic(8));
		userDetails.setContactNumber(RandomStringUtils.randomNumeric(10));
		userDetails.setCountry(country);
		userDetails.setCreatedAt(CommonUtils.createRandomDate(2010, 2019));
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
		userDetails.setCountry(userDetails.getId() == null ? request.getCountry() : userDetails.getCountry());
		userDetails.setDetails(objectMapper.convertValue(request.getDetails(), JsonNode.class));
		//userDetails.setUserPreference(toUserPreference(request.getPreference() == null ? new WSUserPreference() : request.getPreference(),
		   //                                            userDetails.getUserPreference()));
		return userDetails;
	}

	public WSUserDetailsResponse saveUserDetails(WSSaveUserDetailsRequest request) {
		validationUtil.validateUserAdditionalDetails(request.getDetails(), request.getCountry());
		List<Settings> settings = settingsCacheConfig.getSettingsByEntityAndCountry("UserDetails", request.getCountry());
		List<String> conditionalProperties = settings.stream().map(Settings::getConditionalProperty).distinct().collect(Collectors.toList());
		for (String conditionProperty : conditionalProperties) {
			Object o = request.getDetails().get(conditionProperty);
			String value = (String) o;
			if (StringUtils.isNotBlank(value)) {
				
			}
		}
		return null;
	}

	private UserDetails toUserDetails(WSSaveUserDetailsRequest request) {
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
		userDetails.setCountry(userDetails.getId() == null ? request.getCountry() : userDetails.getCountry());
		userDetails.setDetails(objectMapper.convertValue(request.getDetails(), JsonNode.class));
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

	private Map<String, Object> generateDetails(String country) {
		Map<String, Object> objectMap = new HashMap<>();
		String profession = Profession.generateRandomProfession().getValue();
		if ("USA".equalsIgnoreCase(country)) {
			objectMap.put("profession", Profession.generateRandomProfession().getValue());
			objectMap.put("gender", Gender.generateRandomGender().getGender());
			objectMap.put("alternateContacts", CommonUtils.generateAlternateContacts(country));
			objectMap.put("dateOfBirth", CommonUtils.createDateOfBirth(1960, 2000));
			objectMap.put("familyMembers", CommonUtils.generateRandomNumber(10));
			objectMap.put("maritalStatus", MaritalStatus.generateMaritalStatus().getStatus());
			objectMap.put("siblings", CommonUtils.generateSiblingsInfo());
			objectMap.put("info", CommonUtils.generateInfoForProfession(profession));
		}
		if ("India".equalsIgnoreCase(country)) {
			objectMap.put("profession", profession);
			objectMap.put("gender", Gender.generateRandomGender().getGender());
			objectMap.put("alternateContacts", CommonUtils.generateAlternateContacts(country));
			objectMap.put("dateOfBirth", CommonUtils.createDateOfBirth(1960, 2000));
			objectMap.put("maritalStatus", MaritalStatus.generateMaritalStatus().getStatus());
			objectMap.put("fatherName", RandomStringUtils.randomAlphabetic(10));
			objectMap.put("motherMaidenName", RandomStringUtils.randomAlphabetic(10));
			objectMap.put("pinCode", RandomStringUtils.randomNumeric(6));
			objectMap.put("emailId", RandomStringUtils.randomAlphanumeric(10) + "@" + RandomStringUtils.randomAlphabetic(5) + ".com");
			objectMap.put("info", CommonUtils.generateInfoForProfession(profession));
		}
		if ("Australia".equals(country)) {
			String maritalStatus = MaritalStatus.generateMaritalStatus().getStatus();
			Boolean hasDisability = CommonUtils.generateRandomBoolean();
			objectMap.put("profession", Profession.generateRandomProfession().getValue());
			objectMap.put("gender", Gender.generateRandomGender().getGender());
			objectMap.put("alternateContacts", CommonUtils.generateAlternateContacts(country));
			objectMap.put("dateOfBirth", CommonUtils.createDateOfBirth(1960, 2000));
			objectMap.put("maritalStatus", maritalStatus);
			if (MaritalStatus.MARRIED.getStatus().equalsIgnoreCase(maritalStatus))
				objectMap.put("spouseName", RandomStringUtils.randomAlphabetic(10));
			objectMap.put("fatherName", RandomStringUtils.randomAlphabetic(10));
			objectMap.put("familyMembers", CommonUtils.generateRandomNumber(10));
			objectMap.put("taxPayer", CommonUtils.generateRandomBoolean());
			objectMap.put("hasDisability", hasDisability);
			if (hasDisability)
				objectMap.put("disability", Disability.generateDisability().getValue());
			objectMap.put("hobbies", CommonUtils.generateHobbies());
			objectMap.put("info", CommonUtils.generateInfoForProfession(profession));
		}
		if ("Kenya".equalsIgnoreCase(country)) {
			String maritalStatus = MaritalStatus.generateMaritalStatus().getStatus();
			profession = Profession.FARMER.getValue();
			objectMap.put("profession", profession);
			objectMap.put("gender", Gender.generateRandomGender().getGender());
			objectMap.put("dateOfBirth", CommonUtils.createDateOfBirth(1960, 2000));
			objectMap.put("maritalStatus", maritalStatus);
			objectMap.put("info", CommonUtils.generateInfoForProfession(profession));
			objectMap.put("fatherName", RandomStringUtils.randomAlphabetic(10));
			objectMap.put("familyMembers", CommonUtils.generateRandomNumber(10));
			if (MaritalStatus.MARRIED.getStatus().equalsIgnoreCase(maritalStatus))
				objectMap.put("spouseName", RandomStringUtils.randomAlphabetic(10));

		}
		if ("Tanzania".equalsIgnoreCase(country)) {
			String maritalStatus = MaritalStatus.generateMaritalStatus().getStatus();
			profession = Profession.FARMER.getValue();
			objectMap.put("profession", profession);
			objectMap.put("gender", Gender.generateRandomGender().getGender());
			objectMap.put("dateOfBirth", CommonUtils.createDateOfBirth(1960, 2000));
			objectMap.put("maritalStatus", maritalStatus);
			objectMap.put("info", CommonUtils.generateInfoForProfession(profession));
			objectMap.put("fatherName", RandomStringUtils.randomAlphabetic(10));
			objectMap.put("familyMembers", CommonUtils.generateRandomNumber(10));
			if (MaritalStatus.MARRIED.getStatus().equalsIgnoreCase(maritalStatus))
				objectMap.put("spouseName", RandomStringUtils.randomAlphabetic(10));
		}
		return objectMap;
	}
}