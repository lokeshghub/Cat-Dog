package com.lokeshghub.word_chain.graph;

import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

public final class Node {

    private final CharSequence word;
    private final Set<Node> adjacents;

    Node(CharSequence word) {
        this.word = word;
        this.adjacents = new HashSet<>();
    }

    public boolean hasWord(CharSequence word) {
        return getWord().toString().equals(word.toString());
    }

    public CharSequence getWord() {
        return word;
    }

    void addAdjacent(Node node) {
        adjacents.add(node);
    }

    Set<Node> getAdjacents() {
        return unmodifiableSet(adjacents);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return word != null ? word.equals(node.word) : node.word == null;
    }

    @Override
    public int hashCode() {
        return word != null ? word.hashCode() : 0;
    }
}
