package app.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class API implements HttpHandler {
    public String name = "";
    private String contextUrl = "/api/";
    public API(String name, String contextUrl) {
        this.name = name;
        this.contextUrl += contextUrl;
    }

    public void setContextUrl(String contextUrl) {
        this.contextUrl = "/api/" + contextUrl;
    }

    public String getContextUrl() {
        return this.contextUrl;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
