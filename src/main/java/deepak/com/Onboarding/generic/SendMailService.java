package deepak.com.Onboarding.generic;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import deepak.com.Onboarding.DTO.RegisterUserReqDto;

@Service
public class SendMailService {

	private static final Logger logger = LoggerFactory.getLogger(SendMailService.class);

	public ResponseDto sendOtpViaMail(RegisterUserReqDto registerUserReqDto, Integer otp) {
		logger.info("Inside SendMailService :: Inside sendOtpViaMail");

		String to = registerUserReqDto.getEmailId();
		String from = "thenikdeepu@gmail.com";
		String host = "smtp.gmail.com";
		String text = otp + " is your OTP. This OTP will expires in next 3 minutes. Don't Share your OTP with anyone."
				+ "Thankyou.";
		String subject = "This is for demo purpose";
		String password = "fvtmcvwyviacuhsi";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 587);
		// props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setContent(text, "text/html; charset=utf-8");
			Transport.send(message);
			logger.info("Mail sent Succesfully");
			return new ResponseDto(ResponseMessage.SUCCESS_API_MSG, ResponseMessage.SUCCESS_API_CODE, null);
		} catch (Exception e) {
			logger.info("Error while sending mail");
			logger.info(e.getMessage(), e);
			return new ResponseDto(ResponseMessage.ERROR_CODE_MESSAGE, ResponseMessage.ERROR_CODE, null);
		}
	}

}
