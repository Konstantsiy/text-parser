package com.epam.handling.service.impl;

import com.epam.handling.comparator.ParagraphComparator;
import com.epam.handling.entity.Component;
import com.epam.handling.entity.ComponentType;
import com.epam.handling.entity.Composite;
import com.epam.handling.entity.Separator;
import com.epam.handling.exception.TextServiceException;
import com.epam.handling.parser.TextParser;
import com.epam.handling.service.TextService;
import com.epam.handling.writer.DataWriter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TextServiceImpl implements TextService {
    private final TextParser textParser = new TextParser();
    private int longestWordSize;
    private final DataWriter writer = new DataWriter();

    public TextServiceImpl() {}

    @Override
    public String sortParagraphBySentenceCount(String text) {
        Composite textComposite = textParser.parseToComposite(text);
        Composite sortedTextComposite = sorting(textComposite);
        return writer.print(sortedTextComposite);
    }

    public Composite sorting(Composite textComposite) {
        List<Component> paragraphs = textComposite.getComponents();
        List<Composite> paragraphsComposites = new ArrayList<>();
        for(Component par : paragraphs) {
            paragraphsComposites.add((Composite) par);
        }

        List<Component> sortedParagraphs = paragraphsComposites.stream()
                .sorted(ParagraphComparator.SENTENCES_COUNT)
                .collect(Collectors.toList());

        Composite newTextComposite = new Composite(Separator.FOR_PARAGRAPHS, ComponentType.TEXT);
        newTextComposite.addAll(sortedParagraphs);
        return newTextComposite;
    }

    @Override
    public String findSentenceWithLongestWord(String text) {
        Composite textComposite = textParser.parseToComposite(text);
        Component sentenceComponent = searching(textComposite);
        Composite sentenceComposite = (Composite) sentenceComponent;
        return writer.print(sentenceComposite);
    }

    public Component searching(Composite textComposite) {
        longestWordSize = 0;
        List<Component> paragraphs = textComposite.getComponents();
        Component neededSentence = null;
        for(Component paragraph : paragraphs) {
            List<Component> sentences = ((Composite) paragraph).getComponents();
            for(Component sentence : sentences) {
                if (checkLongestWordInComposite((Composite) sentence)) {
                    neededSentence = sentence;
                }
            }
        }
        return neededSentence;
    }

    private boolean checkLongestWordInComposite(Composite sentence) {
        List<Component> wordsComponents = sentence.getComponents().stream()
                .filter(component -> ((Composite)component).getType() == ComponentType.WORD)
                .collect(Collectors.toList());
        List<String> words = new ArrayList<>();
        for(Component wordComponent : wordsComponents) {
            words.add(new String(wordComponent.buildText()));
        }
        String longestWord = Collections.max(words, Comparator.comparing(String::length));
        if(longestWord.length() > longestWordSize) {
            longestWordSize = longestWord.length();
            return true;
        }
        return false;
    }

    @Override
    public String removeSentencesWithWordsFewerThan(String text, int count) throws TextServiceException {
        if(count <= 0) {
            throw new TextServiceException("Incorrect integer parameter");
        }
        Composite textComposite = textParser.parseToComposite(text);
        Composite changedTextComposite = removing(textComposite, count);
        return writer.print(changedTextComposite);
    }

    private Composite removing(Composite composite, int count) {
        List<Component> paragraphs = composite.getComponents();
        for(Component paragraph : paragraphs) {
            List<Component> sentences = ((Composite) paragraph).getComponents();
            List<Component> newSentences = new ArrayList<>();
            for(Component sentence : sentences) {
                int wordsCount = (int) ((Composite) sentence).getComponents().stream()
                        .filter(component -> ((Composite) component).getType() == ComponentType.WORD)
                        .count();
                if(wordsCount >= count) {
                    newSentences.add(sentence);
                }
            }
            ((Composite) paragraph).setComponents(newSentences);
        }
        Composite textComposite = new Composite(Separator.FOR_PARAGRAPHS, ComponentType.TEXT);
        textComposite.addAll(paragraphs);
        return textComposite;
    }

    @Override
    public Map<String, Long> countSameWords(String text) {
        Composite textComposite = textParser.parseToComposite(text);
        List<String> words = getWords(textComposite);
        return countingSameWords(words);
    }

    private List<String> getWords(Composite textComposite) {
        List<String> words = new ArrayList<>();
        for(Component paragraph : textComposite.getComponents()) {
            Composite paragraphComposite = (Composite) paragraph;
            for(Component sentence : paragraphComposite.getComponents()) {
                Composite sentenceComposite = (Composite) sentence;
                for(Component word : sentenceComposite.getComponents()) {
                    Composite wordComposite = (Composite) word;
                    if(wordComposite.getType() == ComponentType.WORD) {
                        String wordStr = new String(wordComposite.buildText());
                        words.add(wordStr);
                    }
                }
            }
        }
        return words;
    }

    private Map<String, Long> countingSameWords(List<String> words) {
        Map<String, Long> result = words.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toMap(Function.identity(), v -> 1L, Long::sum));
        return result;
    }


}
