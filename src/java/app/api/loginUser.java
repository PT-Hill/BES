package app.api;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class loginUser extends API {
    public loginUser(String name, String contextUrl) {
        super(name, contextUrl);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
