package deepak.com.Onboarding.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "UserDetails")
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UserID", nullable = false)
	private Long userID;

	@Column(name = "FirstName", columnDefinition = "VARCHAR(30)", nullable = false)
	private String firstName;

	@Column(name = "LastName", columnDefinition = "VARCHAR(30)", nullable = false)
	private String lastName;

	@Column(name = "EmailId", columnDefinition = "VARCHAR(50)", nullable = false)
	private String emailId;

	@Column(name = "MobileNumber", columnDefinition = "VARCHAR(10)", nullable = false)
	private String mobileNumber;

	@Column(name = "DOB", nullable = true)
	private Date dob;

	@CreationTimestamp
	@Column(name = "CreatedOn", nullable = false)
	private Date createdOn;

	@UpdateTimestamp
	@Column(name = "UpdatedOn", nullable = true)
	private Date updatedOn;

	@Column(name = "Password", columnDefinition = "VARCHAR(255)", nullable = false)
	private String password;

	@Column(name = "Gender", columnDefinition = "VARCHAR(10)", nullable = false)
	private String gender;

	@Column(name = "IsFreezed", nullable = true)
	private Boolean isFreezed = false;

	@Column(name = "UnfreezedDate", nullable = true)
	private Date unfreezedDate;

	@Column(name = "IsFraud", nullable = false)
	private Boolean isFraud = false;

	@Column(name = "Status", columnDefinition = "VARCHAR(10)", nullable = false)
	private String status;

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
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

	public Boolean getIsFreezed() {
		return isFreezed;
	}

	public void setIsFreezed(Boolean isFreezed) {
		this.isFreezed = isFreezed;
	}

	public Date getUnfreezedDate() {
		return unfreezedDate;
	}

	public void setUnfreezedDate(Date unfreezedDate) {
		this.unfreezedDate = unfreezedDate;
	}

	public Boolean getIsFraud() {
		return isFraud;
	}

	public void setIsFraud(Boolean isFraud) {
		this.isFraud = isFraud;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserDetails(Long userID, String firstName, String lastName, String emailId, String mobileNumber, Date dob,
			Date createdOn, Date updatedOn, String password, String gender, Boolean isFreezed, Date unfreezedDate,
			Boolean isFraud, String status) {
		super();
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.dob = dob;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.password = password;
		this.gender = gender;
		this.isFreezed = isFreezed;
		this.unfreezedDate = unfreezedDate;
		this.isFraud = isFraud;
		this.status = status;
	}

	public UserDetails() {
		// TODO Auto-generated constructor stub
	}

}
