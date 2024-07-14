package com.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for processing words and interacting with the Trie data structure.
 */
public class WordUtility {
    private static final Logger logger = LoggerFactory.getLogger(WordUtility.class.getName());
    public static final String REGEX = "\\s+";

    /**
     * Finds all words in the given file that are present in the Trie.
     *
     * @param filePath the path of the input file containing the words to be matched
     * @param trie the Trie data structure to match the words against
     * @return a list of strings, where each string contains the matched words from a line in the input file
     */
    public List<String> matches(Path filePath, Trie trie) {
        List<String> matchedWordsInAllRecords = new ArrayList<>();
        try {
            Files.readAllLines(filePath).forEach(record -> {
                StringBuilder matchedWords = new StringBuilder();
                String[] words = record.split(REGEX);
                String prefix = "";
                for(String word : words) {
                    if(trie.search(word)) {
                        matchedWords.append(prefix);
                        matchedWords.append(word);
                        if(prefix.isBlank()) {
                            prefix = ", ";
                        }
                    }
                }
                // Only add non-empty matched words to the list
                if (matchedWords.length() > 0) {
                    matchedWordsInAllRecords.add(matchedWords.toString());
                }
            });
        } catch (IOException exception) {
            logger.error("Invalid input file provided." + exception.getMessage());
        }

        return matchedWordsInAllRecords;
    }

    /**
     * Writes the matched words to a specified file.
     *
     * @param matchedWords the list of matched words to be written to the file
     * @param fileName the name of the file to write the matched words to
     */
    public void writeResultToFile(List<String> matchedWords, String fileName) {
        Path filePath = Paths.get(fileName);
        try {
            Files.write(filePath, matchedWords);
            logger.info("Output written to file '"+filePath.toAbsolutePath().toString()+"' successfully.");
        } catch (IOException exception) {
            logger.error("Failure occurred while writing output to file.\n" + exception.getMessage());
            throw new RuntimeException(exception);
        }
    }
}
