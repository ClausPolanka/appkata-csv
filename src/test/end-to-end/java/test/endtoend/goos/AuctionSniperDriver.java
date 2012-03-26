package test.endtoend.goos;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;
import goos.Main;
import goos.MainWindow;

import static goos.MainWindow.MAIN_WINDOW_NAME;
import static goos.MainWindow.SNIPER_STATUS_NAME;
import static org.hamcrest.core.IsEqual.equalTo;

public class AuctionSniperDriver extends JFrameDriver{

    public AuctionSniperDriver(int timeoutMillis) {
        super(new GesturePerformer(),
              JFrameDriver.topLevelFrame(named(MAIN_WINDOW_NAME), showingOnScreen()),
              new AWTEventQueueProber(timeoutMillis, 100));
    }

    public void showsSniperStatus(String statusText) {
        new JLabelDriver(this,
            named(SNIPER_STATUS_NAME)).hasText(equalTo(statusText));
    }
}
