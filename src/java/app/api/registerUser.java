package app.api;

import app.api.sql.UserTable;
import com.sun.net.httpserver.HttpExchange;
import utils.RequestUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public class registerUser extends API {
    public registerUser(String name, String contextUrl) {
        super(name, contextUrl);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            String request = new String(exchange.getRequestBody().readAllBytes());
            System.out.println(request);
            try {
                Map<String,String> post = RequestUtils.fetchPostParameters(request);
                UserTable.Insert(Integer.parseInt(post.get("user_id")), post.get("first_name"), post.get("last_name"), post.get("email"), post.get("password"));

                String response = "User registered!";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream oe = exchange.getResponseBody();
                oe.write(response.getBytes());
                oe.close();
            } catch (SQLException e) {
                String response = "Error during registration!";
                exchange.sendResponseHeaders(400, response.length());
                OutputStream oe = exchange.getResponseBody();
                oe.write(response.getBytes());
                oe.close();
                System.err.println(e);
                throw new RuntimeException(e);
            }
        }
        else {
            String response = "Unsupported request method";
            exchange.sendResponseHeaders(405, response.length());
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.flush();
            outputStream.close();
        }


    }
}
