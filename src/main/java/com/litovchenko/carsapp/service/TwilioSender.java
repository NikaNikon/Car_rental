package com.litovchenko.carsapp.service;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;

public class TwilioSender {

        public static final String ACCOUNT_SID = "ACd462f746c6cedcce33599460462b5356";
        public static final String AUTH_TOKEN = "27a41fdecd3a895ef3e2546de9bc66f6";

        public static void send(String phone, int orderId, double price) {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message message = Message
                    .creator(new PhoneNumber(phone),
                            new PhoneNumber("+19012311879"),
                            "Your order was registered (order id: " + orderId + ", total price: " +
                    price + "). Wait for confirmation.").create();

            System.out.println(message.getSid());
        }
}
