package app.api;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class registerUser extends API {
    public registerUser(String name, String contextUrl) {
        super(name, contextUrl);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, "Hello!".length());
        OutputStream oe = exchange.getResponseBody();
        oe.write("Hello!".getBytes());
        oe.close();
    }
}
