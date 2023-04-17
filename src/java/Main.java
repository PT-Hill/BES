import server.Server;
import utils.ContextUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        Server s = new Server(new InetSocketAddress(InetAddress.getLoopbackAddress(), 8000), 200);
        System.out.println("Hello world!");
        s.loadNormalContexts();
    }
}