package com.assignment;

import java.util.HashMap;
import java.util.Map;

/**
 * A node in the Trie. Each node represents a single character and its children.
 */
public class TrieNode {
    Map<Character, TrieNode> children;
    boolean isWord;

    public TrieNode() {
        this.children = new HashMap<>();
        this.isWord = false;
    }
}