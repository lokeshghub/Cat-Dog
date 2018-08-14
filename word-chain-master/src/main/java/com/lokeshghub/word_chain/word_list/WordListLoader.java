package com.lokeshghub.word_chain.word_list;

import java.util.Set;

public interface WordListLoader {

    Set<CharSequence> loadWordList() throws WordListException;
}
