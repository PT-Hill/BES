package app.api;

import app.api.sql.UserTable;
import com.sun.net.httpserver.HttpExchange;
import utils.RequestUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

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
                System.out.println("Param 1 Left: " + RequestUtils.fetchPostParameters(request, 1, 1));
                System.out.println(RequestUtils.fetchPostParameters(request, 1, 2));

                UserTable.Insert(Integer.parseInt(RequestUtils.fetchPostParameters(request, 1, 2)),
                                 RequestUtils.fetchPostParameters(request, 2, 2),
                                 RequestUtils.fetchPostParameters(request, 3, 2),
                                 RequestUtils.fetchPostParameters(request, 4, 2),
                                 RequestUtils.fetchPostParameters(request, 5, 2)
                );

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
