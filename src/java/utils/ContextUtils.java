package utils;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ContextUtils {
    public static void recursiveContextInit(HttpServer s) {
        try {
            Files.find(Paths.get("html/"), 999, (p, bfa) -> bfa.isRegularFile()).forEach(file -> {
                System.out.println(file.toString().substring(4));
                //Creates a context url for all found files
                String filePath = file.toString().substring(4).replace("\\", "/");
                s.createContext(filePath, exchange -> {
                    RequestUtils.ServeFile(exchange, filePath.substring(1));
                });
                //Creates root directory listings if necessary
                if(filePath.contains("index.html")) {
                    String indexFilePath = filePath.replace("index.html", "");
                    s.createContext(indexFilePath, exchange -> {
                        RequestUtils.ServeFile(exchange, indexFilePath);
                    });
                }
            });
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }
}
