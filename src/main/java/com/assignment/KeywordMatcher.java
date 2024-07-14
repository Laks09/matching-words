package com.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * This class is responsible for matching keywords in an input file against a predefined list of keywords stored in a Trie.
 * It reads the input file and outputs the matching keywords either to the terminal or to a file.
 */
public class KeywordMatcher {
    private static final Logger logger = LoggerFactory.getLogger(KeywordMatcher.class.getName());
    private static final long MAX_INPUT_FILE_SIZE = 20 * 1024 * 1024; // 20 MB in bytes
    public static final String SAMPLE_KEYWORDS_FILENAME = "sample-mit-wordlist.txt";
    public static final String OUTPUT_FILE_NAME = "output.txt";
    private static long startTime;
    private static long endTime;

    public static void main(String[] args) throws IOException {
        // construct the trie to store all the predefined keywords
        // word list can be downloaded from here https://www.mit.edu/~ecprice/wordlist.10000
        Trie trie = constructTrie();

        Scanner inputScanner = new Scanner(System.in);
        matchWords(trie, inputScanner);
        inputScanner.close();
    }

    /**
     * Matches words from the input file against the keywords in the Trie.
     *
     * @param trie         The Trie containing predefined keywords.
     * @param inputScanner The Scanner to read user input.
     * @throws IOException If an I/O error occurs.
     */
    private static void matchWords(Trie trie, Scanner inputScanner) throws IOException {
        while (true) {
            // once the trie is constructed pass the input file to find all the matching words.
            // each line in the output corresponds to matched words per record/line that are comma separated.
            System.out.println("Enter the input file: ");
            String inputFile = inputScanner.next();

            if("exit".equalsIgnoreCase(inputFile)) {
                logger.info("Exiting. ");
                break;
            }
            Path filePath = Paths.get(inputFile);
            if (!Files.exists(filePath)) {
                System.out.println("File does not exist. Please try again.");
                logger.warn("File does not exist: " + filePath.toAbsolutePath().toString());
                continue;
            }
            long fileSize = Files.size(filePath);
            if (fileSize > MAX_INPUT_FILE_SIZE) {
                logger.warn("Input file exceeds max allowed file size. Input file can be upto 20MB");
                continue;
            }
            WordUtility wordUtility = new WordUtility();
            startTime = System.currentTimeMillis();
            List<String> matches = wordUtility.matches(filePath, trie);
            endTime = System.currentTimeMillis();
            logger.info("Processing input file of size {} bytes took {} ms to complete", fileSize, (endTime-startTime));

            printResult(inputScanner, wordUtility, matches);

            System.out.println("Test with another input file? Yes/No");
            String response = inputScanner.next();
            if(response.equalsIgnoreCase("no")) {
                break;
            }
        }
    }

    /**
     * Prints the result of matched words either to the terminal or to a file.
     *
     * @param inputScanner The Scanner to read user input.
     * @param wordUtility  The utility to handle word operations.
     * @param matches      The list of matched words.
     */
    private static void printResult(Scanner inputScanner, WordUtility wordUtility, List<String> matches) {
        // output can either be printed on the terminal or can be copied to a file.
        while (true) {
            System.out.println("Enter Output format: Terminal | File ");
            String outputFormat = inputScanner.next().toLowerCase();

            if(outputFormat.equals("terminal")) {
                matches.forEach(System.out::println);
                break;
            }
            else if(outputFormat.equals("file")) {
                wordUtility.writeResultToFile(matches, OUTPUT_FILE_NAME);
                break;
            }
            else {
                System.out.println("Enter valid output format. Output format can either be 'terminal' or 'file' ");
                logger.warn("Invalid output format: "+ outputFormat);
            }
        }
    }

    /**
     * Constructs the Trie with predefined keywords.
     *
     * @return The constructed Trie.
     */
    private static Trie constructTrie() {
        Trie trie = Trie.getTrie();
        startTime = System.currentTimeMillis();
        KeywordLoaderUtility.readKeywordFromFile(SAMPLE_KEYWORDS_FILENAME, trie);
        endTime = System.currentTimeMillis();
        logger.info("Inserting all words to Trie data structure with 10k keywords took "+(endTime-startTime)+"ms");
        System.out.println("The program runs until the user decides to exit. Enter exit to terminate at any point.");
        return trie;
    }
}
