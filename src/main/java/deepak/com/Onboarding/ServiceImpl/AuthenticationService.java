package deepak.com.Onboarding.ServiceImpl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import deepak.com.Onboarding.DTO.LoginReqDto;

public interface AuthenticationService {

	ResponseEntity<?> loginAuthenticate(LoginReqDto loginReqDto, HttpServletRequest request);

}
