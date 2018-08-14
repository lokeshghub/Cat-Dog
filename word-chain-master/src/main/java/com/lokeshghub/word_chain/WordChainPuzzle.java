package com.lokeshghub.word_chain;

import com.lokeshghub.word_chain.graph.Graph;
import com.lokeshghub.word_chain.graph.Node;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public final class WordChainPuzzle {

    private final Graph graph;

    public WordChainPuzzle(Graph graph) {
        this.graph = graph;
    }

    public List<CharSequence> solveFor(CharSequence startWord, CharSequence endWord) {
        ensureBothWordsAreValid(startWord, endWord);

        return graph.findPathBetween(startWord, endWord)
                .stream()
                .map(Node::getWord)
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }

    private void ensureBothWordsAreValid(CharSequence startWord, CharSequence endWord) {
        ensureWordIsNotEmpty(startWord);
        ensureWordIsNotEmpty(endWord);
        ensureBothWordsHaveSameLength(startWord, endWord);
    }

    private void ensureWordIsNotEmpty(CharSequence word) {
        if (isNull(word) || word.length() == 0) {
            throw new IllegalArgumentException("Input word(s) cannot be null or empty");
        }
    }

    private void ensureBothWordsHaveSameLength(CharSequence startWord, CharSequence endWord) {
        if (startWord.length() != endWord.length()) {
            throw new IllegalArgumentException("Both words must have the same length");
        }
    }
}
