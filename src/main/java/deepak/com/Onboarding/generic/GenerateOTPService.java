package deepak.com.Onboarding.generic;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import deepak.com.Onboarding.DAO.OtpDetailsDAO;
import deepak.com.Onboarding.DTO.RegisterUserReqDto;
import deepak.com.Onboarding.DTO.VerifyOtpReqDto;
import deepak.com.Onboarding.Model.OtpDetails;
import deepak.com.Onboarding.encryption.RSA;

@Service
public class GenerateOTPService {

	private static final Logger logger = LoggerFactory.getLogger(GenerateOTPService.class);

	@Autowired
	private OtpDetailsDAO otpDetailsDAO;

	@Autowired
	private RSA rsa;

	public ResponseDto generateOtp(RegisterUserReqDto registerUserReqDto, String string) {
		logger.info("Inside GenerateOTPService :: Inside generateOtp");
		try {
			logger.info("Inside generateOtp: user mail id is {}", registerUserReqDto.getEmailId());
			List<OtpDetails> otpDetails = otpDetailsDAO.findByEmailId(registerUserReqDto.getEmailId());
			logger.info("Inside generateOtp :: otpDetails is {}", otpDetails);
			if (otpDetails == null || otpDetails.isEmpty()) {
				logger.info("No limit exceeds of generare otp");
				int otp = getOtp();
				if (createOTPDetails(registerUserReqDto, otp)) {
					logger.info("Otp details saved succesfully");
					return new ResponseDto(ResponseMessage.SUCCESS_API_MSG, ResponseMessage.SUCCESS_API_CODE, otp);
				} else {
					logger.info("Otp details not saved successflly");
					return new ResponseDto(ResponseMessage.ERROR_CODE, ResponseMessage.ERROR_CODE_MESSAGE, null);

				}
			} else if (otpDetails != null && !otpDetails.isEmpty() && otpDetails.size() <= 10) {
				logger.info("No limit exceeds of generate otp otp details list is {}", otpDetails.size());
				for (OtpDetails details : otpDetails) {
					details.setStatus("DEACTIVE");
				}
				otpDetailsDAO.saveAll(otpDetails);
				int otp = getOtp();
				if (createOTPDetails(registerUserReqDto, otp)) {
					logger.info("Otp details saved succesfully");
					return new ResponseDto(ResponseMessage.SUCCESS_API_MSG, ResponseMessage.SUCCESS_API_CODE, otp);
				} else {
					logger.info("Otp details not saved successflly");
					return new ResponseDto(ResponseMessage.ERROR_CODE, ResponseMessage.ERROR_CODE_MESSAGE, null);
				}
			} else {
				for (OtpDetails details : otpDetails) {
					details.setStatus("DEACTIVE");
				}
				otpDetailsDAO.saveAll(otpDetails);
				logger.info("Not able to generate otp for next 30 minutes");
				return new ResponseDto(ResponseMessage.ERROR_CODE, ResponseMessage.OTP_GENERATE_EXCEED_MSG, null);
			}

		} catch (Exception e) {
			logger.info("Error while generateOtp");
			logger.info(e.getMessage(), e);
			return new ResponseDto(ResponseMessage.ERROR_CODE, ResponseMessage.ERROR_CODE_MESSAGE, null);
		}
	}

	private boolean createOTPDetails(RegisterUserReqDto registerUserReqDto, int otp) {
		logger.info("Inside GenerateOTPService :: Inside createdOTPDetails");
		try {
			OtpDetails otpDetails = new OtpDetails();
			otpDetails.setEmailId(registerUserReqDto.getEmailId());
			otpDetails.setMobileNumber(registerUserReqDto.getMobileNumber());
			otpDetails.setExpiryTime(new Date(System.currentTimeMillis() + (180000L)));
			otpDetails.setoTP(rsa.encrypt(String.valueOf(otp)));

			if (saveOtpDetails(otpDetails)) {
				logger.info("Otp Details Saved Successfully");
				return true;
			} else {
				logger.info("Otp details not saved successfully");
				return false;
			}

		} catch (Exception e) {
			logger.info("Error while createdOTPDetails");
			logger.info(e.getMessage(), e);
			return false;
		}
	}

	public boolean saveOtpDetails(OtpDetails otpDetails) {
		logger.info("Inside GenerateOTPService :: Inside saveOtpDetails");
		try {
			otpDetailsDAO.save(otpDetails);
			return true;
		} catch (Exception e) {
			logger.info("Error while saveOtpDetails");
			logger.info(e.getMessage(), e);
			return false;
		}
	}

	public int getOtp() {
		return (int) (Math.random() * 100000) * 10;
	}

	public OtpDetails verifyOtp(VerifyOtpReqDto verifyOtpReqDto) {
		logger.info("Inside GenerateOTPService :: inside verifyOtp");
		try {
			logger.info("Inside verifyOtp: going to fetch Active Otp for user {}", verifyOtpReqDto.getEmailId());
			OtpDetails otpDetails=otpDetailsDAO.getOtpDetails(verifyOtpReqDto.getEmailId(),verifyOtpReqDto.getMobileNumber());
			return otpDetails;
			
		}catch(Exception e) {
			logger.info("Error while verifyOtp");
			logger.info(e.getMessage(),e);
			return null;
		}
	}

}
