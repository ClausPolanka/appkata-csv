package test.unit.goos;

import de.javawi.jstun.attribute.MessageAttributeInterface;
import goos.AuctionEventListener;
import goos.AuctionMessageTranslator;
import goos.Main;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static goos.AuctionEventListener.PriceSource;
import static goos.Main.SNIPER_ID;

public class AuctionMessageTranslatorTest {
    private static final Chat UNUSED_CHAT = null;

    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    public AuctionEventListener listener;

    @Test
    public void notifiesAuctionClosedWhenCloseMessageReceived() {
        context.checking(new Expectations() {{
            oneOf(listener).auctionClosed();
        }});
        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: CLOSE");

        AuctionMessageTranslator translator = new AuctionMessageTranslator(SNIPER_ID, listener);
        translator.processMessage(UNUSED_CHAT, message);
    }

    @Test
    public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromOtherBidder() {
        context.checking(new Expectations() {{
            exactly(1).of(listener).currentPrice(192, 7, PriceSource.FromOtherBidder);
        }});
        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");

        AuctionMessageTranslator translator = new AuctionMessageTranslator(SNIPER_ID, listener);
        translator.processMessage(UNUSED_CHAT, message);
    }

    @Test
    public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromSniper() {
        context.checking(new Expectations() {{
            exactly(1).of(listener).currentPrice(234, 5, PriceSource.FromSniper);
        }});
        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 234; Increment: 5; Bidder: " + SNIPER_ID + ";");

        AuctionMessageTranslator translator = new AuctionMessageTranslator(SNIPER_ID, listener);
        translator.processMessage(UNUSED_CHAT, message);
    }

}
