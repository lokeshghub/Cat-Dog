package com.lokeshghub.word_chain.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class GraphFactory {

    private final Map<CharSequence, Set<CharSequence>> processedWords;
    private final Map<CharSequence, Node> nodes;

    public GraphFactory(Map<CharSequence, Set<CharSequence>> processedWords) {
        this.processedWords = processedWords;
        this.nodes = new HashMap<>();
    }

    public Graph createGraph() {
        processedWords.keySet()
                .stream()
                .map(this::getNodeFor)
                .forEach(node -> addAdjacents(node, getWordsFor(node)));

        return new Graph(nodes);
    }

    private void addAdjacents(Node node, Set<CharSequence> words) {
        words.stream()
                .map(this::getNodeFor)
                .forEach(node::addAdjacent);
    }

    private Node getNodeFor(CharSequence word) {
        nodes.computeIfAbsent(word, key -> new Node(key));
        return nodes.get(word);
    }

    private Set<CharSequence> getWordsFor(Node node) {
        return processedWords.get(node.getWord());
    }
}
