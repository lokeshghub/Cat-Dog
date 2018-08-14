package com.lokeshghub.word_chain;

import com.lokeshghub.word_chain.cli.CliConsole;
import com.lokeshghub.word_chain.graph.Graph;
import com.lokeshghub.word_chain.graph.GraphFactory;
import com.lokeshghub.word_chain.word_list.FileWordListLoader;
import com.lokeshghub.word_chain.word_list.WordListProcessor;
import com.lokeshghub.word_chain.word_list.WordTransformer;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

import static java.lang.String.format;

public final class WordChainPuzzleApp {

    private static final Logger LOGGER = Logger.getLogger("WordChainPuzzleApp");
    private static final String ENGLISH_WORD_LIST = "/word_list/en";

    public static void main(String[] args) throws Exception {
        WordChainPuzzle wordChainPuzzle = initializeWordChainPuzzle();
        initializeConsole(wordChainPuzzle);
    }

    private static WordChainPuzzle initializeWordChainPuzzle() throws Exception {
        Map<CharSequence, Set<CharSequence>> processedWordList = getProcessedWordList();
        Graph graph = createGraph(processedWordList);

        LOGGER.info("Initializing Word Chain Puzzle...");
        WordChainPuzzle wordChainPuzzle = new WordChainPuzzle(graph);
        LOGGER.info("Word Chain Puzzle initialized");

        return wordChainPuzzle;
    }

    private static void initializeConsole(WordChainPuzzle wordChainPuzzle) {
        Scanner scanner = new Scanner(System.in);
        new CliConsole(wordChainPuzzle, scanner, System.out).startConsole();
    }

    private static Graph createGraph(Map<CharSequence, Set<CharSequence>> processedWordList) {
        LOGGER.info("Creating Graph...");
        Graph graph = new GraphFactory(processedWordList).createGraph();
        LOGGER.info("Graph created...");

        return graph;
    }

    private static Map<CharSequence, Set<CharSequence>> getProcessedWordList() throws Exception {
        Set<CharSequence> wordList = loadWordList();

        LOGGER.info("Processing Word List");
        WordTransformer wordTransformer = new WordTransformer(wordList);
        Map<CharSequence, Set<CharSequence>> processed = new WordListProcessor(wordTransformer).process(wordList);
        LOGGER.info("Word List processed");

        return processed;
    }

    private static Set<CharSequence> loadWordList() throws Exception {
        LOGGER.info(format("Loading Word List [%s]", ENGLISH_WORD_LIST));
        Set<CharSequence> wordList = new FileWordListLoader(getWordListFilePath()).loadWordList();
        LOGGER.info(format("Word List [%s] loaded", ENGLISH_WORD_LIST));
        return wordList;
    }

    private static Path getWordListFilePath() throws Exception {
        URI uri = WordChainPuzzleApp.class.getResource(ENGLISH_WORD_LIST).toURI();
        FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
        return fileSystem.getPath(ENGLISH_WORD_LIST);
    }
}
