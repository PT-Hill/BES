package utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;

public class ContextUtils {
    public static void recursiveContextInit(HttpServer s) {
        try {
            AtomicBoolean rootIndexAdded = new AtomicBoolean(false);
            Files.find(Paths.get("html/"), 999, (p, bfa) -> bfa.isRegularFile()).forEach(file -> {
                System.out.println(file.toString().substring(4));
                //Creates a context url for all found files
                String filePath = file.toString().substring(4).replace("\\", "/");
                s.createContext(filePath, exchange -> {
                    System.out.println(exchange.getRequestURI().getPath());


                    System.out.println(exchange.getRequestURI().getPath());
                    if(pageNotFound(exchange, filePath)) return;
                    RequestUtils.ServeFile(exchange, filePath.substring(1));
                });
                //Creates root directory listings if necessary
                if(filePath.contains("index.html")) {
                    String indexFilePath = filePath.replace("index.html", "");
                    s.createContext(indexFilePath, exchange ->
                    {
                        System.out.println(exchange.getRequestURI().getPath());
                        if(pageNotFound(exchange, indexFilePath)) return;
                        RequestUtils.ServeFile(exchange, filePath);
                    });

                    if(!rootIndexAdded.get()) {
                        rootIndexAdded.set(true);
                        return;
                    }
                    String indexFilePath2 = filePath.replace("/index.html", "");
                    try {

                        s.createContext(indexFilePath2, exchange ->
                        {
                            System.out.println(exchange.getRequestURI().getPath());
                            if (pageNotFound(exchange, indexFilePath2)) return;
                            RequestUtils.ServeFile(exchange, filePath);
                        });
                    }

                    catch(Exception e) {
                        System.err.println(e + " Path: " + indexFilePath2);
                    }
                }
            });
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    /*
     Since Java's context uri system is just awful and uses .startsWith instead of explicit .equals logic,
     we have to manually implement our own explicit pathing by intercepting any invalid request uris with a 404 page
    */
    public static boolean pageNotFound(HttpExchange exchange, String referencePath) throws IOException {
        if(!exchange.getRequestURI().getPath().equals(referencePath)) {
            String pageNotFoundResponse = Files.readString(Paths.get("html/404.html"), StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(404, pageNotFoundResponse.length());
            OutputStream rb = exchange.getResponseBody();
            rb.write(pageNotFoundResponse.getBytes(StandardCharsets.UTF_8));
            rb.close();
            return true;
        }
        else {
            return false;
        }
    }
}
