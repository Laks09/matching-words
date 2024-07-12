package com.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KeywordLoaderUtilityTest {
    private Trie trie;

    @BeforeEach
    public void setUp() {
        trie = Trie.getTrie();
    }

    @Test
    public  void testReadKeywordFromFile() {
        KeywordLoaderUtility.readKeywordFromFile("test-file.txt", trie);
        assertTrue(trie.search("word"));
        assertFalse(trie.search("anotherword"));

    }
    @Test
    public void testReadKeywordFromFileWithInvalidFile() {
        assertThrows(RuntimeException.class, () -> KeywordLoaderUtility.readKeywordFromFile("invalid-file.txt", trie));
    }
}
