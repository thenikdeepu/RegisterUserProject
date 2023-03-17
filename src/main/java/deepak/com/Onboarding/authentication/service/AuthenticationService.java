package deepak.com.Onboarding.authentication.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import deepak.com.Onboarding.DTO.LoginReqDto;

public interface AuthenticationService {

	ResponseEntity<?> loginAuthenticate(LoginReqDto loginReqDto, HttpServletRequest request);

}
