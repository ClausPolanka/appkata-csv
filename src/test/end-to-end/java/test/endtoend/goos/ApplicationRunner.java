package test.endtoend.goos;

import goos.Main;
import goos.MainWindow;

import static goos.MainWindow.STATUS_BIDDING;
import static goos.MainWindow.STATUS_JOINING;
import static goos.MainWindow.STATUS_LOST;
import static goos.MainWindow.STATUS_WON;
import static goos.MainWindow.STATUS_WINNING;

public class ApplicationRunner {

    private static final String XMPP_HOSTNAME = "localhost";
    private static final String SNIPER_ID = "sniper";
    private static final String SNIPER_PASSWORD = "sniper";
    public static final String SNIPER_XMPP_ID = SNIPER_ID + "@" + XMPP_HOSTNAME + "/Auction";

    private AuctionSniperDriver driver;

    public void startBiddingIn(final FakeAuctionServer auction) {
        Thread thread = new Thread("Application Runner") {
            @Override
            public void run() {
                try {
                    Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.showsSniperStatus(STATUS_JOINING);
    }

    public void hasShownSniperIsBidding() {
        driver.showsSniperStatus(STATUS_BIDDING);
    }

    public void showsSniperHasLostAuction() {
        driver.showsSniperStatus(STATUS_LOST);
    }

    public void hasShownSniperIsWinning() {
        driver.showsSniperStatus(STATUS_WINNING);
    }

    public void showsSniperHasWonAuction() {
        driver.showsSniperStatus(STATUS_WON);
    }

    public void stop() {
        driver.dispose();
    }
}
