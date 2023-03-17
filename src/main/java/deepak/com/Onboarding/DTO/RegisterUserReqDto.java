package deepak.com.Onboarding.DTO;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.Length;

import deepak.com.Onboarding.generic.ResponseMessage;

public class RegisterUserReqDto {

	@NotBlank(message = "First name can not be blank.", groups = Default.class)
	@NotEmpty(message = "Last name can not be empty.", groups = Default.class)
	@NotNull(message = "First name can not be null.", groups = Default.class)
	@Length(min = 2, max = 20, message = "First name should be 2 to 20 characters.", groups = Default.class)
	@Pattern(regexp = "^[A-Za-z]+$", message = "Invalid first name.", groups = Default.class)
	public String firstName;

	public String lastName;

	@NotBlank(message = "Mobile number can not be blank.", groups = Default.class)
	@NotEmpty(message = "Mobile number can not be empty.", groups = Default.class)
	@NotNull(message = "Mobile number can not be null.", groups = Default.class)
	@Size(min = 10, max = 10, message = "Mobile number length should be 10.", groups = Default.class)
	@Pattern(regexp = ResponseMessage.MOBILE_NUMBER_REGEX, message = "Invalid mobile number.", groups = Default.class)
	public String mobileNumber;

	@NotBlank(message = "Email Id can not be blank.", groups = Default.class)
	@NotEmpty(message = "Email Id can not be empty.", groups = Default.class)
	@NotNull(message = "Email Id can not be null.", groups = Default.class)
	@Email(message = "Invalid email id.", groups = Default.class)
	public String emailId;

	@Past(message = "DOB is invalid.", groups = Default.class)
	public Date dob;

	@NotBlank(message = "Password can not be blank.", groups = Default.class)
	@NotEmpty(message = "Password can not be empty.", groups = Default.class)
	@NotNull(message = "Password can not be null.", groups = Default.class)
	@Length(min = 8, message = "Password minimum length should be 8.", groups = Default.class)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password should be minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character.", groups = Default.class)
	public String password;

	// @NotBlank(message="Gender can not be blank.",groups=Default.class)
	// @NotEmpty(message="Gender can not be empty.",groups=Default.class)
	// @NotNull(message="Gender can not be null.",groups=Default.class)
	@Pattern(regexp = "^$|^(?:m|M|male|Male|f|F|female|Female|FEMALE|MALE|Not prefer to say)$", message = "Gender should be male,female or Not prefer to say.", groups = Default.class)
	public String gender;

	// @Pattern(regexp = "^$|^(?:true|false)$", message = "Ethnicity should be
	// yes,no or Not prefer to say.", groups = Default.class)
	public boolean isAsian;

	@NotEmpty(message = "Role can not empty.", groups = Default.class)
	@NotNull(message = "Role can not be null.", groups = Default.class)
	@Size(max=1,message="Please select one role at a time.",groups=Default.class)
	public List<@NotEmpty(message = "Role can not empty.", groups = Default.class) @NotNull(message = "Role can not be null.", groups = Default.class) @Pattern(regexp = "(?:user|User|USER|Admin|admin|ADMIN|Co-Admin|co-Admin|Co-admin|co-admin|Not prefer to say)$", message = "Role should be User,Admin or Co-Admin", groups = Default.class) String> roles;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public boolean isAsian() {
		return isAsian;
	}

	public void setAsian(boolean isAsian) {
		this.isAsian = isAsian;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
