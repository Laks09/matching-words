# Matching Words

A Java application to find matching words from an input file using a Trie data structure.

## Table of Contents

- [Requirements](#requirements)
- [Installation](#installation)
- [Building the Project](#building-the-project)
- [Running the Project](#running-the-project)
- [Usage](#usage)
- [Running Unit Test](#running-unit-test)

## Requirements
- Java 11 or higher

## Installation
Clone the repository to your local machine:

```shell script
git clone https://github.com/Laks09/matching-words.git
cd matching-words
```
## Building the Project
To build the project, use the Gradle wrapper included in the repository. You do not need to have Gradle installed on your machine.

On Unix-based systems (Linux, macOS), use:
```shell script
./gradlew build
```

On Windows, use:
```shell script
gradlew.bat build
```

## Running the Project
The build command creates the jar in the build directury. To run, execute the below command

```shell script
java -jar build/libs/matching-words-1.0-SNAPSHOT.jar
```

## Usage
The application reads keywords from a file (sample-mit-wordlist.txt) which is packaged as part of the jar and inserts them into a Trie data structure. 
It then takes the user provided input file and finds matching words from the Trie. 
The results can be printed to the terminal or written to an output file (output.txt).
1. keywords file named `sample-mit-wordlist.txt` in the `src/main/resources` directory.
2. Input file should be a text file with one record per line. 

### Interactive Usage
The application will prompt you for an input file and output format (terminal or file). It will continue to process files until the user wishes to terminate/exit.

```plain Text
The program runs until the user decides to exit. Enter exit to terminate at any point.
Enter the input file: path/to/your/input/file.txt
Enter Output format: Terminal | File
(If "Terminal", matched words will be printed. If "File", results will be written to `output.txt`.)
Test with another input file? Yes/No
(If "No", the program will exit.)
```

## Example Run
sample input file 
```plain Text
apples and cherry  are red
violets are green as grapes
```
On the running the project, the below are the matched keywords from the predefined 10k words.
```plain Text
Inserting all words to Trie data structure with 10k keywords took 70ms
The program runs until the user decides to exit. Enter exit to terminate at any point.
Enter the input file: 
/Users/lakshmi/workspace/input.txt
Enter Output format: Terminal | File 
terminal
and, cherry, are, red
are, green, as
Test with another input file? Yes/No
no
```

## Running Unit Test
Unit tests are an important part of ensuring the quality and correctness of the application. The project includes unit tests that can be run using the Gradle wrapper.

To run the unit tests, use the following command:
On Unix-based systems (Linux, macOS), use:
```shell script
./gradlew test
```

On Windows, use:
```shell script
gradlew.bat test
```
Gradle will execute the unit tests and provide a summary of the test results. The detailed test reports can be found in the build/reports/tests/test directory.