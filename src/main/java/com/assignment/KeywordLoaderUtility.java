package com.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

/**
 * Utility class for loading keywords from a file into a Trie data structure.
 */
public class KeywordLoaderUtility {
    private static final Logger logger = LoggerFactory.getLogger(KeywordLoaderUtility.class.getName());
    public static final String FILE_PREFIX = "jar:";

    /**
     * Reads keywords from the specified file and inserts them into the provided {@link Trie}.
     *
     * @param file the name of the file containing the keywords
     * @param trie the Trie data structure to insert the keywords into
     */
    public static  void readKeywordFromFile(String file, Trie trie) {
        try {
            ClassLoader classLoader = KeywordLoaderUtility.class.getClassLoader();
            URL url = classLoader.getResource(file);
            // the below changes was added to test the code. FileSystems is required when we are trying to use a file present in the jar.
            // The else is used by the unit test.
            Path path;
            if ( url != null && url.toString().startsWith(FILE_PREFIX)) {
                path = FileSystems.newFileSystem(url.toURI(), Collections.emptyMap()).getPath(file);
            } else {
                path = Paths.get(url.toURI());
            }

            Files.readAllLines(path).forEach(trie::insert);
        } catch (IOException | URISyntaxException exception) {
            logger.error("Exception occurred while reading from input file " + file + " exception: "+exception.getMessage());
        }
    }
}
