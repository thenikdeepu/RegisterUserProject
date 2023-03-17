package deepak.com.Onboarding.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

public class LoginReqDto {

	@NotBlank(message = "Email Id can not be blank.", groups = Default.class)
	@NotEmpty(message = "Email Id can not be empty.", groups = Default.class)
	@NotNull(message = "Email Id can not be null.", groups = Default.class)
	@Email(message = "Email Id is invalid.", groups = Default.class)
	private String email;

	@NotBlank(message = "Password can not be blank.", groups = Default.class)
	@NotEmpty(message = "Password Id can not be empty.", groups = Default.class)
	@NotNull(message = "Password Id can not be null.", groups = Default.class)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@$!%*?&])[A-Za-z\\\\d@$!%*?&]{8,}$", message = "Invalid password.", groups = Default.class)
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
