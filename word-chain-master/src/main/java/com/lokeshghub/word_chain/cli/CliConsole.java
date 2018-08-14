package com.lokeshghub.word_chain.cli;

import com.lokeshghub.word_chain.WordChainPuzzle;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class CliConsole {

    private final String EXIT_COMMAND = "\\exit";

    private final WordChainPuzzle wordChainPuzzle;
    private final Scanner scanner;
    private final PrintStream output;

    public CliConsole(WordChainPuzzle wordChainPuzzle, Scanner scanner, PrintStream output) {
        this.wordChainPuzzle = wordChainPuzzle;
        this.scanner = scanner;
        this.output = output;
    }

    public void startConsole() {
        output.println(format("Enter %s to exit", EXIT_COMMAND));
        while (true) {
            String firstWord = readWord("Please enter start word: ");
            String endWord = readWord("Please enter end word: ");
            solveFor(firstWord, endWord);
        }
    }

    private String readWord(String message) {
        output.println(message);
        String word = scanner.nextLine();
        exitIfRequested(word);
        return word;
    }

    private void exitIfRequested(String word) {
        if (EXIT_COMMAND.equalsIgnoreCase(word)) {
            System.exit(0);
        }
    }

    private void solveFor(CharSequence startWord, CharSequence endWord) {
        output.println(format("Solving for [%s] and [%s]", startWord, endWord));
        try {
            String chain = wordChainPuzzle.solveFor(startWord, endWord)
                    .stream()
                    .collect(Collectors.joining(" -> "));
            output.println(chain.isEmpty() ? "No word chain found" : chain);
        } catch (IllegalArgumentException e) {
            output.println("[ERROR] " + e.getMessage());
        }
        output.println("--------------------------------");
    }
}
