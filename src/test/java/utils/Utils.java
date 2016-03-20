package utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by kapiton on 19.03.16.
 */
public class Utils {
    public static String read(String resourceName) throws IOException, URISyntaxException {
        final URI resourceURI = ClassLoader.getSystemClassLoader().getResource(resourceName).toURI();

        byte[] encoded = Files.readAllBytes(Paths.get(resourceURI));
        return new String(encoded);
    }
}
