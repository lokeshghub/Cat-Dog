package com.lokeshghub.word_chain.word_list;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public final class WordListProcessor {

    private final WordTransformer wordTransformer;

    public WordListProcessor(WordTransformer wordTransformer) {
        this.wordTransformer = wordTransformer;
    }

    public Map<CharSequence, Set<CharSequence>> process(Set<CharSequence> wordList) {
        return wordList.stream()
                .collect(collectingAndThen(
                        toMap(identity(), wordTransformer::transform),
                        Collections::unmodifiableMap));
    }
}
