package com.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrieTest {
    private Trie trie;

    @BeforeEach
    public void setUp() {
        trie = Trie.getTrie();
    }

    @Test
    public void testInsertAndSearch() {
        trie.insert("word");
        assertTrue(trie.search("word"));
        assertFalse(trie.search("wor"));
        assertFalse(trie.search("words"));
    }

    @Test
    public void testSearchEmptyString() {
        assertFalse(trie.search(""));
    }

    @Test
    public void testSearchBlankString() {
        assertFalse(trie.search(" "));
    }
}
