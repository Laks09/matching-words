package com.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class KeywordMatcher {
    private static final Logger logger = LoggerFactory.getLogger(KeywordMatcher.class.getName());

    public static void main(String[] args) {
        // construct the trie to store all the predefined keywords
        // word list can be downloaded from here https://www.mit.edu/~ecprice/wordlist.10000
        Trie trie = Trie.getTrie();
        long startTime = System.currentTimeMillis();
        KeywordLoaderUtility.readKeywordFromFile("sample-mit-wordlist.txt", trie);
        long endTime = System.currentTimeMillis();
        logger.info("Inserting all words to Trie data structure with 10k keywords took "+(endTime-startTime)+"ms");
        System.out.println("The program runs until the user decides to exit. Enter exit to terminate at any point.");

        Scanner inputScanner = new Scanner(System.in);
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
            if(!Files.exists(filePath)) {
                System.out.println("File does not exist. Please try again.");
                logger.warn("File does not exist: " + filePath.toAbsolutePath().toString());
                continue;
            }
            WordUtility wordUtility = new WordUtility();
            List<String> matches = wordUtility.matches(filePath, trie);

            // output can either be printed on the terminal or can be copied to a file.
            while (true) {
                System.out.println("Enter Output format: Terminal | File ");
                String outputFormat = inputScanner.next().toLowerCase();

                if(outputFormat.equals("terminal")) {
                    matches.forEach(System.out::println);
                    break;
                }
                else if(outputFormat.equals("file")) {
                    wordUtility.writeResultToFile(matches, "output.txt");
                    break;
                }
                else {
                    System.out.println("Enter valid output format. Output format can either be 'terminal' or 'file' ");
                    logger.warn("Invalid output format: "+ outputFormat);
                }
            }
            System.out.println("Test with another input file? Yes/No");
            String response = inputScanner.next();
            if(response.equalsIgnoreCase("no")) {
                break;
            }
        }

        inputScanner.close();
    }
}
