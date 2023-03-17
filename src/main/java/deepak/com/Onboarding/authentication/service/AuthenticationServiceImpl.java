package deepak.com.Onboarding.authentication.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import deepak.com.Onboarding.DTO.LoginReqDto;
import deepak.com.Onboarding.generic.ResponseDto;
import deepak.com.Onboarding.generic.ResponseMessage;

public class AuthenticationServiceImpl implements AuthenticationService {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	@Override
	public ResponseEntity<?> loginAuthenticate(LoginReqDto loginReqDto, HttpServletRequest request) {
		logger.info("Inside AuthenticationServiceImpl :: Inside loginAuthenticate");
		try {
			return null;
		} catch (Exception e) {
			logger.error("Error while loginAuthenticate");
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(
					new ResponseDto(ResponseMessage.ERROR_CODE, ResponseMessage.ERROR_CODE_MESSAGE, null),
					HttpStatus.OK);
		}
	}

}
