package com.lokeshghub.word_chain.graph;

import java.util.*;

import static java.util.Collections.unmodifiableMap;
import static java.util.Optional.ofNullable;

public final class Graph {

    private final Map<CharSequence, Node> nodes;

    Graph(Map<CharSequence, Node> nodes) {
        this.nodes = unmodifiableMap(nodes);
    }

    Optional<Node> getNode(CharSequence word) {
        return ofNullable(nodes.get(word));
    }

    public Deque<Node> findPathBetween(CharSequence startWord, CharSequence endWord) {
        if (containsNodesForBothWords(startWord, endWord)) {
            return findPath(startWord, endWord);
        }
        return emptyDeque();
    }

    private boolean containsNodesForBothWords(CharSequence startWord, CharSequence endWord) {
        return getNode(startWord).isPresent() && getNode(endWord).isPresent();
    }

    private Deque<Node> findPath(CharSequence startWord, CharSequence endWord) {
        Queue<Node> toVisit = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parents = new HashMap<>();

        toVisit.add(getNode(startWord).get());

        while (!toVisit.isEmpty()) {
            Node currentNode = toVisit.poll();
            if (currentNode.hasWord(endWord)) {
                return backtrack(currentNode, parents);
            }

            visited.add(currentNode);

            currentNode.getAdjacents()
                    .stream()
                    .filter(adjacent -> !visited.contains(adjacent))
                    .forEach(adjacent -> {
                        toVisit.add(adjacent);
                        parents.putIfAbsent(adjacent, currentNode);
                    });
        }

        return emptyDeque();
    }

    private Deque<Node> backtrack(Node lastNode, Map<Node, Node> parents) {
        Deque<Node> path = new LinkedList<>();

        Node currentNode = lastNode;
        do {
            path.push(currentNode);
            currentNode = parents.get(currentNode);
        } while (currentNode != null);

        return path;
    }

    private Deque<Node> emptyDeque() {
        return new LinkedList<>();
    }
}
