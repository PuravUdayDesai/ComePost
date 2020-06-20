package com.hack.comp.aspect;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSAspect
{
    public static final String ACCOUNT_SID =
            "AC3d68da5bc41cab3eba2fd5c2372328b5";
    public static final String AUTH_TOKEN =
            "64a8ef112bcd412fa408fdb51d369190";
    
    public static void sendSMS(String body, String recepient)
    {
		
//		  System.out.println("IN Message to: "+recepient);
//		  Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
//		 
//		Message message = Message .creator(
//				new PhoneNumber("+91"+recepient), // to
//				new PhoneNumber("+12057369687"),// from 
//				body //body
//		 ) .create();
//		 System.out.println(message.getSid());
		 
    }

}
