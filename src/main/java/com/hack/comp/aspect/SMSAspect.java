package com.hack.comp.aspect;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.SmsSubmissionResponseMessage;
import com.nexmo.client.sms.messages.TextMessage;

public class SMSAspect
{

    public static void sendSMS(String body, String recepient)
    {
    	System.out.println(recepient);
        NexmoClient client = NexmoClient.builder().apiKey( "1c6adeec" ).apiSecret( "Q4pJriL0r4XsQVV9" ).build();

        SmsSubmissionResponse responses = client.getSmsClient()
                .submitMessage( new TextMessage( "7304036480", "+91"+recepient, body ) );
        for (SmsSubmissionResponseMessage response : responses.getMessages())
        {
            System.out.println( response );
        }
    }

}
