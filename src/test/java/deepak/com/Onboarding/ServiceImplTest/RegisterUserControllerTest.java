package deepak.com.Onboarding.ServiceImplTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import deepak.com.Onboarding.Controller.RegisterUser;
import deepak.com.Onboarding.Model.UserDetails;
import deepak.com.Onboarding.ServiceImpl.OnboardingService;
import deepak.com.Onboarding.generic.ResponseDto;
import deepak.com.Onboarding.generic.ResponseMessage;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@Import(RegisterUser.class)
public class RegisterUserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	// ObjectWriter objectWriter =objectMapper.writer();

	// @MockBean
	// private UserDetailsDAO userDetailsDAO;

	@MockBean
	private OnboardingService onboardingService;

	UserDetails userDetails;

	@Autowired
	WebApplicationContext wac;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
	}

	@Test
	public void signUpTest() throws Exception {
		userDetails = new UserDetails(1L, "Deepak", "Kumar", "abc@gmail.com", "7989889014", new Date(), new Date(),
				new Date(), "123456", "Male", false, new Date(), false, "ACTIVE");
		doReturn(new ResponseEntity<>(
				new ResponseDto(ResponseMessage.SUCCESS_API_CODE, ResponseMessage.SUCCESS_API_MSG, null),
				HttpStatus.OK)).when(onboardingService).signUp(any());

		mockMvc.perform(MockMvcRequestBuilders.post("/signUp").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(MockMvcResultMatchers.status().is(404));
				//.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
				//.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[2].FirstName", ("Deepak")));

	}

}
