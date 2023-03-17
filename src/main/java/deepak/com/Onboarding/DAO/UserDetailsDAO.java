package deepak.com.Onboarding.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import deepak.com.Onboarding.Model.UserDetails;

@Repository
public interface UserDetailsDAO extends JpaRepository<UserDetails, Long> {

	@Query(value = "SELECT * from UserDetails where EmailId=:emailId AND (:mobileNumber is NULL OR MobileNumber=:mobileNumber) ", nativeQuery = true)
	UserDetails findByEmailIdAndMNumber(@Param("emailId") String emailId, @Param("mobileNumber") String mobileNumber);

}
