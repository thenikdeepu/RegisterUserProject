package deepak.com.Onboarding.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import deepak.com.Onboarding.DTO.LoginReqDto;
import deepak.com.Onboarding.DTO.RegisterUserReqDto;
import deepak.com.Onboarding.DTO.VerifyOtpReqDto;
import deepak.com.Onboarding.ServiceImpl.AuthenticationService;
import deepak.com.Onboarding.ServiceImpl.OnboardingService;
import deepak.com.Onboarding.generic.ResponseDto;
import deepak.com.Onboarding.generic.ResponseMessage;

@RestController
@RequestMapping("/user/onboard")
public class RegisterUser {

	private static final Logger logger = LoggerFactory.getLogger(RegisterUser.class);

	@Autowired
	private OnboardingService onboardingService;
	
	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/signUp")
	public ResponseEntity<?> signUp(@Valid @RequestBody RegisterUserReqDto RegisterUserReqDto, Errors error,
			HttpServletRequest request) {
		logger.info("Inside RegisterUser :: Inside signUp");
		if (error.hasErrors()) {
			return new ResponseEntity<>(
					new ResponseDto(ResponseMessage.ERROR_CODE, error.getAllErrors().get(0).getDefaultMessage(), null),
					HttpStatus.BAD_REQUEST);
		} else {
			return onboardingService.signUp(RegisterUserReqDto);
		}
	}

	@PostMapping("/verifyOtp")
	public ResponseEntity<?> verifyOtp(@Valid @RequestBody VerifyOtpReqDto verifyOtpReqDto, Errors error,
			HttpServletRequest request) {
		logger.info("Inside RegisterUser :: Inside verifyOtp");
		if (error.hasErrors()) {
			return new ResponseEntity<>(new ResponseDto(ResponseMessage.ERROR_CODE, ResponseMessage.ERROR_CODE_MESSAGE,
					error.getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
		} else {
			return onboardingService.verifyOtp(verifyOtpReqDto);
		}
	}
//	@PostMapping("/convertToPdf")
//	public ResponseEntity<?> convertToPdf(@Valid @RequestParam(value="xlsFile",required = true) MultipartFile file,Errors error,HttpServletRequest request){
//		logger.info("Inside RegisterUser: Inside convertToPdf");
//		if(error.hasErrors()) {
//			c
//		}else {
//			return onboardingService.convertToPdf(file);
//		}
//	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginReqDto loginReqDto, Errors error,
			HttpServletRequest request) {
    logger.info("Inside RegisterUser :: Inside login");
    if(error.hasErrors()) {
    	return new ResponseEntity<>(new ResponseDto(ResponseMessage.ERROR_CODE,ResponseMessage.ERROR_CODE_MESSAGE,error.getAllErrors().get(0).getDefaultMessage()),HttpStatus.BAD_REQUEST);
    }else {
 return authenticationService.loginAuthenticate(loginReqDto,request);
  
    }
	}

}
