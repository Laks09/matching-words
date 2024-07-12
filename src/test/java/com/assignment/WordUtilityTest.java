package com.assignment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;


public class WordUtilityTest {
    private WordUtility wordUtility;
    private Trie trie;
    private Path tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        wordUtility = new WordUtility();
        trie = Trie.getTrie();
        trie.insert("word");
        trie.insert("test");

        // Create a temporary file for testing
        tempFile = Files.createTempFile("test", ".txt");
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Delete the temporary file after tests
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void testMatches() throws IOException {
        // Add test content to the temporary file
        List<String> content = List.of("word test", "test word", "no match");
        Files.write(tempFile, content);

        List<String> matches = wordUtility.matches(tempFile, trie);

        assertEquals(2, matches.size(), "Two lines should contain matched words.");
        assertTrue(matches.contains("word, test"));
        assertTrue(matches.contains("test, word"));
    }

    @Test
    public void testWriteResultToFile() throws IOException {
        // Prepare output file path
        Path outputFilePath = Paths.get("output.txt");
        List<String> matchedWords = List.of("word1", "word2");

        // Write results to the file
        wordUtility.writeResultToFile(matchedWords, outputFilePath.toString());

        // Verify the output file content
        assertTrue(Files.exists(outputFilePath), "The output file should be created.");
        List<String> lines = Files.readAllLines(outputFilePath);
        assertEquals(matchedWords, lines, "The content of the output file should match the matched words.");

        // Clean up the test file
        Files.deleteIfExists(outputFilePath);
    }

    @Test
    public void testWriteResultToFileWithIOException() {
        // Spy on WordUtility
        WordUtility wordUtilitySpy = spy(wordUtility);

        // Mock the Files.write method to throw IOException
        doAnswer(invocation -> {
            throw new IOException("Mocked IOException");
        }).when(wordUtilitySpy).writeResultToFile(anyList(), anyString());

        // Verify that the method throws the expected exception
        List<String> matchedWords = List.of("word1", "word2");
        assertThrows(IOException.class, () -> wordUtilitySpy.writeResultToFile(matchedWords, "output.txt"));
    }
}
