package server;

import app.api.API;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import utils.ContextUtils;
import utils.RequestUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Server {

    private HttpServer httpServer;
    private String[] validRequestPaths;
    private String certificatePath;

    //Initializes a server object
    public Server() {
        try {
            httpServer = HttpServer.create();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Initializes a server object with an address and backlog
    //Examples: new InetSocketAddress(new InetAddress("127.0.0.1"), 80), 200
    //The above binds a server with the localhost ip on port 80, and with a backlog(how many pending connections) queue of 200
    public Server(InetSocketAddress socketAddress, int backlog) {
        try {
            httpServer = HttpServer.create(socketAddress, backlog);
            //httpServer.createContext("/", new RootHandler());

            System.out.println("Server initialized at " + socketAddress.toString());
            httpServer.start();
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadNormalContexts() {
        ContextUtils.recursiveContextInit(httpServer);
    }

    public void loadApiContexts(API[] apis) {
        for(API api : apis) {
            httpServer.createContext(api.getContextUrl(), api);
        }
    }

}

class RootHandler implements HttpHandler {

    @Override

    public void handle(HttpExchange he) {
        RequestUtils.ServeFile(he, "/");
    }
}