package goos;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.HashMap;

import static goos.AuctionEventListener.PriceSource;

public class AuctionMessageTranslator implements MessageListener {
    private String sniperId;
    private AuctionEventListener listener;

    public AuctionMessageTranslator(String sniperId, AuctionEventListener listener) {
        this.listener = listener;
        this.sniperId = sniperId;
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        AuctionEvent event = AuctionEvent.from(message.getBody());

        String eventType = event.type();
        if ("CLOSE".equals(eventType)) {
            listener.auctionClosed();
        }
        if ("PRICE".equals(eventType)) {
            listener.currentPrice(event.currentPrice(), event.increment(), event.isFrom(sniperId));
        }
    }

    private static class AuctionEvent {
        private final HashMap<String, String> fields = new HashMap<>();

        static AuctionEvent from(String messageBody) {
            AuctionEvent event = new AuctionEvent();
            for (String field : fieldsIn(messageBody)) {
                event.addField(field);
            }
            return event;
        }

        private static String[] fieldsIn(String messageBody) {
            return messageBody.split(";");
        }

        private void addField(String field) {
            String[] pair = field.split(":");
            fields.put(pair[0].trim(), pair[1].trim());
        }

        public String type() {
            return get("Event");
        }

        private String get(String fieldName) {
            return fields.get(fieldName);
        }

        public int currentPrice() {
            return getInt("CurrentPrice");
        }

        private int getInt(String fieldName) {
            return Integer.parseInt(get(fieldName));
        }

        public int increment() {
            return getInt("Increment");
        }

        public PriceSource isFrom(String sniperId) {
            return sniperId.equals(bidder()) ? PriceSource.FromSniper : PriceSource.FromOtherBidder;
        }

        private String bidder() {
            return get("Bidder");
        }
    }
}
