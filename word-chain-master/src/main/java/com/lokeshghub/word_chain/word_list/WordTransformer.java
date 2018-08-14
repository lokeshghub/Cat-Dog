package com.lokeshghub.word_chain.word_list;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

public final class WordTransformer {

    private static final Set<Character> ALPHABET =
            rangeClosed('a', 'z')
                    .mapToObj(ch -> (char) ch)
                    .collect(collectingAndThen(toSet(), Collections::unmodifiableSet));

    private final Set<CharSequence> wordList;

    public WordTransformer(Set<CharSequence> wordList) {
        this.wordList = wordList;
    }

    public Set<CharSequence> transform(CharSequence word) {
        Set<CharSequence> modifiedWords = new HashSet<>();
        range(0, word.length())
                .forEach(index -> modifiedWords.addAll(modifyWordAt(index, word)));
        return Collections.unmodifiableSet(modifiedWords);
    }

    private Set<CharSequence> modifyWordAt(Integer index, CharSequence word) {
        return ALPHABET
                .stream()
                .filter(ch -> !ch.equals(word.charAt(index)))
                .map(ch -> setCharAt(word, index, ch))
                .filter(modifiedWord -> isValidWord(modifiedWord))
                .collect(toSet());
    }

    private CharSequence setCharAt(CharSequence word, Integer at, Character character) {
        StringBuilder changedWord = new StringBuilder(word);
        changedWord.setCharAt(at, character);
        return changedWord.toString();
    }

    private boolean isValidWord(CharSequence word) {
        return wordList.contains(word);
    }
}
