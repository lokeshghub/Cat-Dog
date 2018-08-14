package com.lokeshghub.word_chain.word_list;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

public final class FileWordListLoader implements WordListLoader {

    private final Path file;

    public FileWordListLoader(Path file) {
        this.file = file;
    }

    public Set<CharSequence> loadWordList() {
        try (Stream<String> fileStream = Files.lines(file)) {
            return fileStream
                    .filter(line -> !line.isEmpty())
                    .map(String::toLowerCase)
                    .map(String::trim)
                    .map(line -> (CharSequence) line)
                    .collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
        } catch (IOException e) {
            throw new WordListException(format("Couldn't load file [%s]", file), e);
        }
    }
}
