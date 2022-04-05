package com.example.demo.resource;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.response.ResponseMessage;
import com.example.demo.service.OtpRequest;
import com.example.demo.service.TwilioService;

@RestController
public class TwilioOTPHandler {
	@Autowired
	private TwilioService service;

	@PostMapping("/sendOtpToWhatsapp")
	public ResponseEntity<ResponseMessage> sendSms(@RequestBody OtpRequest otpRequest) {
		String notification = service.smsSubmit(otpRequest);
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
				"User details..", notification);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);

	}

}
