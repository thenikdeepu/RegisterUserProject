package deepak.com.Onboarding.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import deepak.com.Onboarding.Model.OtpDetails;

@Repository
public interface OtpDetailsDAO extends JpaRepository<OtpDetails, Long> {

	@Query(value = "Select * from OtpDetails where EmailId=:emailId  AND  TIMESTAMPDIFF(MINUTE,CreatedOn,sysDate()) < 30 order by CreatedOn desc ", nativeQuery = true)
	List<OtpDetails> findByEmailId(@Param("emailId") String emailId);

	@Query(value = "Select * from OtpDetails where EmailId=:emailId AND (:mobileNumber is null OR MobileNumber=:mobileNumber) AND Status='ACTIVE' order by CreatedOn desc Limit 1 ", nativeQuery = true)
	OtpDetails getOtpDetails(@Param("emailId") String emailId, @Param("mobileNumber") String mobileNumber);

}
