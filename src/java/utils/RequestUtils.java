package utils;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestUtils {
    //All tedious methods relating to serving, fetching, and requesting pages

    public static void ServeFile (HttpExchange he, String filepath) {
        filepath = "html/" + filepath;

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


    //Starts at 1 as I like to think of it as a binary value, almost a boolean (0 or 1)
    public static Map<String, String> fetchPostParameters(String request) {
        Map<String, String> parameters = new HashMap<String, String>();
        for (String parameter : request.split("&"))
        {
            String[] pair  = parameter.split("=");
            parameters.put(pair[0], pair[1]);
        }

        return parameters;
    }
}
