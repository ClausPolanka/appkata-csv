package test.unit.goos;

import goos.Auction;
import goos.AuctionEventListener;
import goos.AuctionSniper;
import goos.SniperListener;
import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static goos.AuctionEventListener.PriceSource;

public class AuctionSniperTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final States sniperState = context.states("bidding");

    @Mock
    private SniperListener sniperListener;

    @Mock
    private Auction auction;

    @Test
    public void reportsLostIfAuctionClosesImmediately() {
        context.checking(new Expectations() {{
            atLeast(1).of(sniperListener).sniperLost();
        }});

        AuctionSniper sniper = new AuctionSniper(auction, sniperListener);
        sniper.auctionClosed();
    }

    @Test
    public void reportsLostIfAuctionClosesWhenBidding() {
        context.checking(new Expectations() {{
            ignoring(auction);
            allowing(sniperListener).sniperBidding(); then(sniperState.is("bidding"));

            atLeast(1).of(sniperListener).sniperLost(); when(sniperState.is("bidding"));
        }});

        AuctionSniper sniper = new AuctionSniper(auction, sniperListener);
        sniper.currentPrice(123, 45, PriceSource.FromOtherBidder);
        sniper.auctionClosed();
    }

    @Test
    public void reportsWonIfAuctionClosesWhenWinning() {
        context.checking(new Expectations() {{
            ignoring(auction);
            allowing(sniperListener).sniperWinning(); then(sniperState.is("winning"));

            atLeast(1).of(sniperListener).sniperWon(); when(sniperState.is("winning"));
        }});

        AuctionSniper sniper = new AuctionSniper(auction, sniperListener);
        sniper.currentPrice(123, 45, PriceSource.FromSniper);
        sniper.auctionClosed();
    }

    @Test
    public void bidsHigherAndReportsBiddingWhenNewPriceArrivesFromOtherBidder() {
        final int price = 1001;
        final int increment = 25;

        context.checking(new Expectations() {{
            oneOf(auction).bid(price + increment);
            atLeast(1).of(sniperListener).sniperBidding();
        }});

        AuctionSniper sniper = new AuctionSniper(auction, sniperListener);
        sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);
    }

    @Test
    public void reportsIsWinningWhenCurrentPriceComesFromSniper() {
        context.checking(new Expectations() {{
            atLeast(1).of(sniperListener).sniperWinning();
        }});

        AuctionSniper sniper = new AuctionSniper(auction, sniperListener);
        sniper.currentPrice(123, 45, PriceSource.FromSniper);
    }

}
