# Sending-Notification-to-whatsapp
  
#    ****************** steps need to follow to send notification or message to whatsapp using Twilio in Spring Boot ******************
# 1) Add the following Depenedency In pom.xml
#      <dependency>
#			<groupId>com.twilio.sdk</groupId>
#			<artifactId>twilio</artifactId>
#			<version>7.34.0</version>
#		</dependency>
#2) If Dont have account in Twilio, then create account then you will get following details for twilio free service,
#    * twilio.account_sid=AC13796e1a4d757cd3a5079b590165a715
#    * twilio.auth_token=828157bd68a0dfdac4cc265f3f8c98b2
#    * twilio.trial_number=+19036648367 (This is for sending sms to phone number)



twilio.account_sid=AC13796e1a4d757cd3a5079b590165a715
twilio.auth_token=828157bd68a0dfdac4cc265f3f8c98b2

twilio.trial_number=+19036648367  (for sending msg to mobile number)
twilio.trial_number=+14155238886  (for sending msg to whatsapp)



#    ****************** steps need to follow to send notification or message to whatsapp using Twilio in Spring Boot ******************
# 1) Add the following Depenedency In pom.xml
#      <dependency>
#			<groupId>com.twilio.sdk</groupId>
#			<artifactId>twilio</artifactId>
#			<version>7.34.0</version>
#		</dependency>
#2) If Dont have account in Twilio, then create account then you will get following details for twilio free service,
#    * twilio.account_sid=AC13796e1a4d757cd3a5079b590165a715
#    * twilio.auth_token=828157bd68a0dfdac4cc265f3f8c98b2
#    * twilio.trial_number=+19036648367 (This is for sending sms to phone number)


   ***************** Service Class **********************
  
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class TwilioService {

	private static final String ACCOUNT_SID = "AC13796e1a4d757cd3a5079b590165a715";

	private static final String AUTH_TOKEN = "828157bd68a0dfdac4cc265f3f8c98b2";

	private static final String FROM_NUMBER = "+19036648367"; // sms to number
//	private static final String FROM_NUMBER = "+14155238886";// whatsapp

	public String smsSubmit(OtpRequest otpRequest) {

		try {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			String response = "";

			/**
			 * These two line for sending otp to number
			 */
			long number = ThreadLocalRandom.current().nextLong(100000, 1000000);
			String otpMsg = "     Your OTP is - " + number
					+ "\n Please verify this OTP For using the application.. \n Thank you...";

			/**
			 * This is for sending sms or message to phone number and for sending twilio
			 * whatsapp sandbox setting link to registered mobile number Once,you click that
			 * link,it will go to whatsapp with that join code ,then you send that code by
			 * cliking send button,then you change the trial free number to whatsapp trial
			 * number and add message and run the application ,then that will go to your
			 * whatsapp as message
			 */

			// This will help only for sending whatsapp sandbbox activation link to your
			// registered number in Twilio.You will get this link in whatsapp sandbox
			// activation page
//			String msg = "http://wa.me/+14155238886?text=join%20officer-tank";  

			/**
			 * This is for sending notification to mobile number through sms
			 */
			if (otpRequest.getPhoneNumber() != null) {
				Message.creator(new PhoneNumber(otpRequest.getPhoneNumber()), new PhoneNumber(FROM_NUMBER),
						otpRequest.getMessage() + "\n" + otpMsg).create();
				response = "otp has been sent to your mobile phone " + otpRequest.getPhoneNumber();
			}

			/**
			 * These is for sending message to whatsapp using wahtsapp free trial number
			 * which is given by Twilio
			 */
			// Here, You should write the code like this only for sending msg to whatsapp
			if (otpRequest.getPhoneNumber() != null) {
				Message.creator(new PhoneNumber("whatsapp:" + otpRequest.getPhoneNumber()),
						new PhoneNumber("whatsapp:+14155238886"), otpRequest.getMessage() + "\n" + otpMsg).create();
				response = "otp has been sent to your whatsapp number " + otpRequest.getPhoneNumber();
			}
			return response;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

}


  
   ********************** Controller Class *********************

@RestController
public class TwilioOTPHandler {
	@Autowired
	private TwilioService service;

	@PostMapping("/sendOtpToWhatsapp")
	public ResponseEntity<?> sendSms(@RequestBody OtpRequest otpRequest) {
		return ResponseEntity.ok(service.smsSubmit(otpRequest));
	}

}
