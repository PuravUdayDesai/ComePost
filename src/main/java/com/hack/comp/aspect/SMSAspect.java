package com.hack.comp.aspect;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSAspect
{
    public static final String ACCOUNT_SID =
            "<TWILIO ACCOUNT SID>";
    public static final String AUTH_TOKEN =
            "<TWILIO AUTH TOKEN>";
    
    public static void sendSMS(String body, String recepient)
    {
		
		  System.out.println("IN Message to: "+recepient);
		  Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
		 
		Message message = Message .creator(
				new PhoneNumber("+91"+recepient), // to
				new PhoneNumber("<FROM_NUMBER>"),// from 
				body //body
		 ) .create();
		 System.out.println(message.getSid());
		 
    }

}
