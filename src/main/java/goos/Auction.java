package goos;

import org.jivesoftware.smack.XMPPException;

public interface Auction {
    void bid(int bid);
    void join() throws XMPPException;
}
