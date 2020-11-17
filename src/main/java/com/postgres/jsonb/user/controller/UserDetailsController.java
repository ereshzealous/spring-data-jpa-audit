package com.postgres.jsonb.user.controller;

import com.postgres.jsonb.user.model.WSUserDetailsRequest;
import com.postgres.jsonb.user.model.WSUserDetailsResponse;
import com.postgres.jsonb.user.service.UserDetailsService;
import com.postgres.jsonb.user.util.FrequencyEnum;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created on 11/November/2020 By Author Eresh, Gorantla
 **/
@RestController
@RequestMapping("/api")
public class UserDetailsController {

	final UserDetailsService userDetailsService;

	public UserDetailsController(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@PutMapping("/user")
	public ResponseEntity<WSUserDetailsResponse> saveUserDetails(@RequestBody WSUserDetailsRequest request) {
		WSUserDetailsResponse response = userDetailsService.saveUserDetails(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/data")
	public void dataSeed() {
		userDetailsService.dataSeed();
	}

	@DeleteMapping("/user/{id}")
	public Boolean deleteUser(@PathVariable Long id) {
		userDetailsService.deleteUserDetails(id);
		return true;
	}

	private FrequencyEnum generateFrequencyEnum() {
		FrequencyEnum[] farmSizeEnums = FrequencyEnum.values();
		Random random = new Random();
		final Integer index = random.ints(0, (farmSizeEnums.length - 1)).limit(1).findFirst().getAsInt();
		return farmSizeEnums[index];
	}
}