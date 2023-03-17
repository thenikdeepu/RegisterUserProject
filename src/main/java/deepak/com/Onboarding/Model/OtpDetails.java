package deepak.com.Onboarding.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "OtpDetails", indexes = { @Index(name = "OtpDetails_Index", columnList = "EmailId,MobileNumber") })
public class OtpDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UniqueId", nullable = false, updatable = false)
	private Long uniqueId;

	@CreationTimestamp
	@Column(name = "CreatedOn", nullable = false)
	private Date createdOn;

	@UpdateTimestamp
	@Column(name = "UpdatedOn", nullable = true)
	private Date updatedOn;

	@Column(name = "EmailId", columnDefinition = "VARCHAR(100)", nullable = false)
	private String emailId;

	@Column(name = "MobileNumber", columnDefinition = "VARCHAR(10)", nullable = false)
	private String mobileNumber;

	@Column(name = "IsActive", columnDefinition = "BOOLEAN", nullable = false)
	private Boolean isActive=true;

	@Column(name = "ExpiryTime", nullable = true)
	private Date expiryTime;

	@Column(name = "NoOfAttempts", columnDefinition = "INT", nullable = false)
	private Integer noOfAttempts = 3;

	@Column(name = "Status", columnDefinition = "VARCHAR(10)", nullable = false)
	private String status = "ACTIVE";

	@Column(name = "OTP", columnDefinition = "VARCHAR(255)", nullable = false)
	private String oTP;

	public Long getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(Long uniqueId) {
		this.uniqueId = uniqueId;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	public Integer getNoOfAttempts() {
		return noOfAttempts;
	}

	public void setNoOfAttempts(Integer noOfAttempts) {
		this.noOfAttempts = noOfAttempts;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getoTP() {
		return oTP;
	}

	public void setoTP(String oTP) {
		this.oTP = oTP;
	}

}
