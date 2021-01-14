package com.epam.handling.service;

import com.epam.handling.exception.TextServiceException;

import java.util.Map;

public interface TextService {
    String sortParagraphBySentenceCount(String text);
    String findSentenceWithLongestWord(String text);
    String removeSentencesWithWordsFewerThan(String text, int count) throws TextServiceException;
    Map<String, Long> countSameWords(String text);
}
