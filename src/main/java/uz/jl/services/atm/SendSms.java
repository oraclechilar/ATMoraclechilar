package uz.jl.services.atm;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import static uz.jl.utils.Print.println;


public class SendSms {

    public static final String ACCOUNT_SID = System.getenv("AC8c0cdbca2ab76089796d43e0f1335bb5");
    public static final String ACCOUNT_TOKEN = System.getenv("aa80ed1799027322aaa2e5b0def29229");

    public static void send(String phoneNumber, String text) {
        Twilio.init(ACCOUNT_SID, ACCOUNT_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(System.getenv("+18787896604")),
                new PhoneNumber(phoneNumber),
                text
        ).create();
        System.out.println(message.getSid());
    }

}
