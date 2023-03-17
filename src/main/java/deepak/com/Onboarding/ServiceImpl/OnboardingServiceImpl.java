package deepak.com.Onboarding.ServiceImpl;

import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import deepak.com.Onboarding.DAO.UserDetailsDAO;
import deepak.com.Onboarding.DTO.RegisterUserReqDto;
import deepak.com.Onboarding.DTO.VerifyOtpReqDto;
import deepak.com.Onboarding.Model.OtpDetails;
import deepak.com.Onboarding.Model.UserDetails;
import deepak.com.Onboarding.encryption.RSA;
import deepak.com.Onboarding.generic.GenerateOTPService;
import deepak.com.Onboarding.generic.ResponseDto;
import deepak.com.Onboarding.generic.ResponseMessage;
import deepak.com.Onboarding.generic.SendMailService;

@Service
public class OnboardingServiceImpl implements OnboardingService {

	private static final Logger logger = LoggerFactory.getLogger(OnboardingServiceImpl.class);

	@Autowired
	private UserDetailsDAO userDetailDAO;

	@Autowired
	private RSA rsa;

	@Autowired
	private SendMailService sendMailService;

	@Autowired
	private GenerateOTPService generateOTPService;

	@Override
	public ResponseEntity<?> signUp(RegisterUserReqDto registerUserReqDto) {
		logger.info("Inside OnboardingServiceImpl :: Inside signUp");
		logger.info("Inside signUp: User name is {} {}", registerUserReqDto.getFirstName(),
				registerUserReqDto.getLastName());
		logger.info("Inside signUp: User MobileNumber is {}", registerUserReqDto.getMobileNumber());
		ResponseDto responseDto = new ResponseDto();
		try {
			logger.info("Inside signUp: Going to check if user is already exist");
			UserDetails userDetails = new UserDetails();
			userDetails = userDetailDAO.findByEmailIdAndMNumber(registerUserReqDto.getEmailId(),
					registerUserReqDto.getMobileNumber());
			if (userDetails != null) {
				logger.info("Inside signUp: User already Exist");
				responseDto = isFraudOrFreezed(userDetails);
				if (responseDto != null && responseDto.getStatusCode().equalsIgnoreCase("ERR0500")) {
					logger.info("Inside signUp: {} ", responseDto.getMessage());
					return new ResponseEntity<>(
							new ResponseDto(responseDto.getMessage(), responseDto.getStatusCode(), null),
							HttpStatus.OK);
				} else {
					logger.info("User is already exist");
					return new ResponseEntity<>(

							new ResponseDto(responseDto.getMessage(), responseDto.getStatusCode(), null),
							HttpStatus.OK);
				}
			} else {
				logger.info("Inside signUp: User is not exist,Going to register user");

				responseDto = createNewUser(registerUserReqDto);
				if (responseDto != null && responseDto.getStatusCode().equalsIgnoreCase("SUC0200")) {
					logger.info("Inside signUp: User Created Successfully");
					ResponseDto otp = generateOTPService.generateOtp(registerUserReqDto, null);
					logger.info("Inside signUp: otp responseDto response code is {}", otp.getStatusCode());
					if (otp != null && otp.getStatusCode().equalsIgnoreCase(ResponseMessage.SUCCESS_API_CODE)) {
						logger.info("Otp created successfully");
						responseDto = sendMailService.sendOtpViaMail(registerUserReqDto,
								Integer.valueOf(String.valueOf(otp.getResultObj())));
						if (responseDto != null && responseDto.getStatusCode().equalsIgnoreCase("SUC0200")) {
							logger.info("Mail sent successfully");
							return new ResponseEntity<>(new ResponseDto(responseDto.getMessage(),
									responseDto.getStatusCode(), responseDto.getResultObj()), HttpStatus.OK);
						} else {
							logger.info("mail not sent");
							return new ResponseEntity<>(new ResponseDto(responseDto.getMessage(),
									responseDto.getStatusCode(), responseDto.getResultObj()), HttpStatus.OK);
						}
					} else {
						logger.info("Error while creating otp");
						return new ResponseEntity<>(
								new ResponseDto(otp.getMessage(), otp.getStatusCode(), otp.getResultObj()),
								HttpStatus.OK);
					}
				} else {
					logger.info("Error while creating new User");
					return new ResponseEntity<>(new ResponseDto(responseDto.getMessage(), responseDto.getStatusCode(),
							responseDto.getResultObj()), HttpStatus.OK);
				}
			}

		} catch (Exception e) {
			logger.info("Error while signup, Please try again after sometime");
			logger.info(e.getMessage(), e);
			return new ResponseEntity<>("Error while signup, Please try again after sometime", HttpStatus.OK);
		}

	}

	private ResponseDto createNewUser(@Valid RegisterUserReqDto registerUserReqDto) {
		logger.info("Inside OnboardingServiceImpl :: Inside createNewUser");
		try {
			UserDetails userDetails = new UserDetails();
			userDetails.setEmailId(registerUserReqDto.getEmailId());
			userDetails.setDob(registerUserReqDto.getDob());
			userDetails.setFirstName(registerUserReqDto.getFirstName());
			userDetails.setLastName(registerUserReqDto.getLastName());
			userDetails.setGender(registerUserReqDto.getGender());
			userDetails.setPassword(rsa.encrypt(registerUserReqDto.getPassword()));
			userDetails.setMobileNumber(registerUserReqDto.getMobileNumber());
			userDetails.setStatus("DEACTIVE");

			if (saveNewUserDetails(userDetails)) {
				logger.info("User Created Successfully");
				return new ResponseDto("Registered Successfully", "SUC0200",
						userDetails.getFirstName() + " " + userDetails.getLastName());
			} else {
				logger.info("Error while Creating new User");
				return new ResponseDto("Something went wrong,Please try again after sometime", "ERR0500", null);
			}

		} catch (Exception e) {
			logger.info("Error while creating new User");
			logger.info(e.getMessage(), e);
			return new ResponseDto("Error While SignUp", "ERR0500", null);
		}
	}

	private boolean saveNewUserDetails(UserDetails userDetails) {
		logger.info("Inside OnboardingServiceImpl :: Inside saveNewUserDetails");
		try {
			userDetailDAO.save(userDetails);
			return true;

		} catch (Exception e) {
			logger.info("Error while saveNewUserDetails");
			logger.info(e.getMessage(), e);
			return false;
		}
	}

	private ResponseDto isFraudOrFreezed(UserDetails userDetails) {
		logger.info("Inside OnboardingServiceImpl :: Inside isValidForSignUp");
		try {
			logger.info("Inside isFraudOrFreezed: Checking for fraud and Freez");
			if (userDetails.getIsFraud()) {
				logger.info("User is marked as Fraud");
				return new ResponseDto("Sorry, you are not able to signup with this email " + userDetails.getEmailId()
						+ " and MobileNumber " + userDetails.getMobileNumber(), "ERR0500", null);
			}
			if (userDetails != null && userDetails.getUnfreezedDate() != null
					&& userDetails.getUnfreezedDate().compareTo(new Date()) >= 1) {
				logger.info("User is freezed");
				return new ResponseDto(ResponseMessage.ERROR_CODE, ResponseMessage.USER_TEMPORAILY_BLOCKED, null);
			}
			if (userDetails != null) {
				logger.info("User is Already Exist");
				return new ResponseDto("This email " + userDetails.getEmailId() + " and MobileNumber "
						+ userDetails.getMobileNumber() + " is already registered.", "SUC0200", null);
			}

			return new ResponseDto("Success", "SUCC0200", null);
		} catch (Exception e) {
			logger.info("Error while checking is fraud and Freezed");
			logger.info(e.getMessage(), e);
			return new ResponseDto("Error while Error while checking is fraud and Freezed", "ERR0500", null);
		}

	}

	@Override
	public ResponseEntity<?> verifyOtp(VerifyOtpReqDto verifyOtpReqDto) {
		logger.info("Inside OnboardingServiceImpl :: Inside verifyOtp");
		try {
			logger.info("Inside verifyOtp: email id of user is {}", verifyOtpReqDto.getEmailId());
			OtpDetails otpDetails = generateOTPService.verifyOtp(verifyOtpReqDto);
			if (otpDetails != null && otpDetails.getExpiryTime().compareTo(new Date()) >= 1) {
				logger.info("Inside verifyDetails: otp details exist");
				if (rsa.decrypt(otpDetails.getoTP()).equalsIgnoreCase(verifyOtpReqDto.getOtp())) {
					logger.info("Inside verifyOtp: otp matches successfully");
					otpDetails.setStatus("SUCCESS");
					generateOTPService.saveOtpDetails(otpDetails);
					logger.info("Inside verifyOtp: otpDetails status setted to SUCCESS");
					return new ResponseEntity<>(
							new ResponseDto(ResponseMessage.SUCCESS_API_CODE, ResponseMessage.SUCCESS_API_MSG, null),
							HttpStatus.OK);
				} else {
					logger.info("Inside verifyOtp: otp not matched");
					if (otpDetails.getNoOfAttempts() == 1) {
						logger.info("Inside verifyOtp: User marked as freezed");
						UserDetails userDetails = userDetailDAO.findByEmailIdAndMNumber(verifyOtpReqDto.getEmailId(),
								verifyOtpReqDto.getMobileNumber());
						userDetails.setUnfreezedDate(new Date(System.currentTimeMillis() + 7200000L));
						userDetailDAO.save(userDetails);
						return new ResponseEntity<>(new ResponseDto(ResponseMessage.ERROR_CODE,
								ResponseMessage.USER_TEMPORAILY_BLOCKED, null), HttpStatus.OK);
					}
					otpDetails.setNoOfAttempts(otpDetails.getNoOfAttempts() - 1);
					generateOTPService.saveOtpDetails(otpDetails);

					return new ResponseEntity<>(
							new ResponseDto(ResponseMessage.ERROR_CODE, ResponseMessage.INVALID_OTP_MSG, null),
							HttpStatus.OK);
				}

			} else if (otpDetails != null && otpDetails.getExpiryTime().compareTo(new Date()) <= -1) {
				logger.info("Inside verifyotp: otp expired.");
				// otpDetails.setStatus("DEACTIVE");
				// generateOTPService.saveOtpDetails(otpDetails);
				// logger.info("Inside verifyOtp: otpDetails status setted to SUCCESS");
				return new ResponseEntity<>(
						new ResponseDto(ResponseMessage.ERROR_CODE, ResponseMessage.OTP_EXPIRED_MSG, null),
						HttpStatus.OK);
			} else {
				logger.info("No otp details exist for the user");
				return new ResponseEntity<>(
						new ResponseDto(ResponseMessage.ERROR_CODE, ResponseMessage.ERROR_CODE_MESSAGE, null),
						HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.info("Error while verifyOtp");
			logger.info(e.getMessage(), e);
			return new ResponseEntity<>(
					new ResponseDto(ResponseMessage.ERROR_CODE, ResponseMessage.ERROR_CODE_MESSAGE, null),
					HttpStatus.OK);
		}
	}
 
	/*
	 * function which convert xls file into pdf file
	 */
//	@Override
//	public ResponseEntity<?> convertToPdf(MultipartFile file) {
//		logger.info("Inside OnboardingServiceImpl :: Inside convertToPdf");
//		try {
//			FileInputStream input_document = new FileInputStream(new File(file.toString()));
//			Workbook workbook=new HSSFWorkbook(input_document);
//			  HSSFSheet my_worksheet = workbook.getSheetAt(0); 
//			  PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
//			    Document doc = new Document(pdfDoc);
//
//			    Table table = new Table(8);
//			    for (int i = 0; i < 16; i++) {
//			        Paragraph para = new Paragraph("hi");
//			        // now you can use methods like:
//			        // para.setFont()
//			        // para.setFontColor()
//			        // para.setFontSize()
//			        table.addCell(para);
//			    }
//			    doc.add(table);
//
//			    doc.close();
//		}catch(Exception e) {
//			logger.info("Error while convertToPdf");
//			logger.info(e.getMessage(),e);
//			return new ResponseEntity<>(
//					new ResponseDto(ResponseMessage.ERROR_CODE, ResponseMessage.ERROR_CODE_MESSAGE, null),
//					HttpStatus.OK);
//		}
//	}

}
