package deepak.com.Onboarding.DTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

public class VerifyOtpReqDto {

	@NotBlank(message = "Email Id can not be blank.", groups = Default.class)
	@NotEmpty(message = "Email Id can not be empty.", groups = Default.class)
	@NotNull(message = "Email Id can not be null.", groups = Default.class)
	private String emailId;

	@Length(min = 10, max = 10, message = "MobileNumber should be equal to 10 digits.", groups = Default.class)
	private String mobileNumber;

	@NotBlank(message = "Otp can not be blank.", groups = Default.class)
	@NotEmpty(message = "Otp can not be empty.", groups = Default.class)
	@NotNull(message = "Otp can not be null.", groups = Default.class)
	@Min(value = 6, message = "Otp number should be equal to 6 digits.", groups = Default.class)
	@Max(value = 6, message = "otp number should not less than 6 digits", groups = Default.class)
	@Pattern(regexp = "^0-9^", message = "Invalid otp, please entry a valid otp.", groups = Default.class)
	private String otp;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNumber() {
		if (this.mobileNumber != null && !this.mobileNumber.isEmpty()) {
			return this.mobileNumber;
		}
		return null;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

}
