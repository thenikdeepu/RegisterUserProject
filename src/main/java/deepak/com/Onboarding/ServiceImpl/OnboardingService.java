package deepak.com.Onboarding.ServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import deepak.com.Onboarding.DTO.RegisterUserReqDto;
import deepak.com.Onboarding.DTO.VerifyOtpReqDto;


@Service
public interface OnboardingService {

	ResponseEntity<?> signUp(RegisterUserReqDto registerUserReqDto);

	ResponseEntity<?> verifyOtp(VerifyOtpReqDto verifyOtpReqDto);

	//ResponseEntity<?> convertToPdf(MultipartFile file);

}
