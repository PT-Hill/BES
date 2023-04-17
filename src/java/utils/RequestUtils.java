package utils;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RequestUtils {
    //All tedious methods relating to serving, fetching, and requesting pages

    public static void ServeFile (HttpExchange he, String filepath) {
        filepath = "html/" + filepath;

        if(filepath.endsWith("/")) {
            filepath += "index.html";
        }
        String response = null;
        try {
            response = Files.readString(Paths.get(filepath), StandardCharsets.UTF_8);
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (IOException e) {
            /**Debug
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            System.out.println("Current absolute path is: " + s);**/
            if(e.toString().contains("An established connection was aborted by the software in your host machine")) System.out.println("An attempt to access a timed out connection was made");
            else System.err.println(e.toString());
        }
    }
}
