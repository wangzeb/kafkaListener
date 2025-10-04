package com.yjjb.fix.onetime;

import quickfix.*;
import quickfix.field.*;

public class StandaloneParser {

    public static void main(String[] args) throws InvalidMessage, FieldNotFound {
        String fixMessageString = "8=FIX.4.49=12235=D49=SENDER56=TARGET34=111=CLORD12321=155=MSFT54=160=20251004-14:40:00.00010=077";

        try {
            SessionSettings settings = new SessionSettings();
            DataDictionary dataDictionary = new DataDictionary("FIX44.xml"); // Path to your FIX dictionary
            DefaultMessageFactory messageFactory = new DefaultMessageFactory();

            Message message = MessageUtils.parse(messageFactory, dataDictionary, fixMessageString);

            // Accessing fields by tag number
            String clientOrderID = message.getString(11);
            String symbol = message.getString(55);

            System.out.println("Client Order ID: " + clientOrderID);
            System.out.println("Symbol: " + symbol);
        } catch (ConfigError e) {
            e.printStackTrace();
        }
    }
}

