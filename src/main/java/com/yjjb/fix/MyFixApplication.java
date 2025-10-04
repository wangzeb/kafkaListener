package com.yjjb.fix;

import quickfix.*;
import quickfix.MessageCracker;
import quickfix.field.*;
import quickfix.fix44.*;
import quickfix.fix44.Message;

public abstract class MyFixApplication extends MessageCracker implements Application {

    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        // The `crack` method automatically dispatches the message to the right handler
        crack(message, sessionID);
    }

    // Handler for the `NewOrderSingle` message
    public void onMessage(NewOrderSingle message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        ClOrdID clOrdID = new ClOrdID();
        message.get(clOrdID);
        System.out.println("Received NewOrderSingle with Client Order ID: " + clOrdID.getValue());

        // Access other fields
        Symbol symbol = new Symbol();
        message.get(symbol);
        System.out.println("Symbol: " + symbol.getValue());
    }
    // Implement other Application methods (e.g., onCreate, onLogon) as needed
}
