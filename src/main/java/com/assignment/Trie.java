package com.assignment;

/**
 * A Trie (prefix tree) implementation for storing and searching words.
 * This implementation uses a singleton pattern to ensure a single instance.
 */
public class Trie {
    private static Trie instance = null;
    private final TrieNode root;

    /**
     * Private constructor to initialize the root node.
     */
    private Trie() {
        root = new TrieNode();
    }

    /**
     * Returns the singleton instance of {@link Trie}.
     *
     * @return Singleton instance of Trie.
     */
    public static Trie getTrie() {
        if(instance == null) {
            return new Trie();
        }
        return instance;
    }

    /**
     * Inserts a word into the trie.
     *
     * @param word The word to be inserted.
     */
    public void insert(String word) {
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.children.computeIfAbsent(ch, val -> new TrieNode());
        }
        current.isWord = true;

    }

    /**
     * Searches for a word in the trie.
     *
     * @param word The word to be searched.
     * @return {@code true} if the word is found in the trie, {@code false} otherwise.
     */
    public boolean search(String word) {
        if(word.isBlank()) {
            return false;
        }
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.children.get(ch);
            if(current == null) {
                return false;
            }
        }

        return current.isWord;
    }
}