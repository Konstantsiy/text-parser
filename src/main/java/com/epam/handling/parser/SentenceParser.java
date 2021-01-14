package com.epam.handling.parser;

import com.epam.handling.entity.ComponentType;
import com.epam.handling.entity.Composite;
import com.epam.handling.entity.Symbol;
import com.epam.handling.entity.Separator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class SentenceParser extends AbstractParser {
    private static final Logger logger = LogManager.getLogger(SentenceParser.class);

    private final String WORD_REGEX1 = "\\p{Blank}+";
    private final String WORD_REGEX = " |\\r\\n|\t";

    public SentenceParser() {
        this.nextHandler = new WordParser();
    }

    private boolean checkSign(String lexeme) {
        char endSymbol = lexeme.charAt(lexeme.length() - 1);
        return endSymbol == '.' || endSymbol == ',' || endSymbol == '?' || endSymbol == '!' || lexeme.endsWith("...");
    }

    private Composite separateWordAndSign(String lexeme) {
        int lexemeSize = lexeme.length();

        Composite lexemeComposite = new Composite(Separator.FOR_SYMBOLS, ComponentType.SIGN);
        Composite wordComposite;
        Symbol signComposite;

        String word;
        String sign;

        if(lexeme.endsWith("...")) {
            word = lexeme.substring(0, lexemeSize - 3);
            wordComposite = nextHandler.handle(word);
            sign = lexeme.substring(lexemeSize - 3);
        } else {
            char mark = lexeme.charAt(lexemeSize - 1);
            sign = String.valueOf(mark);
            word = lexeme.substring(0, lexemeSize - 1);
            wordComposite = nextHandler.handle(word);
        }
        signComposite = new Symbol(sign);

        logger.debug("[" + signComposite.buildText() + " --- " + lexemeComposite.getType() + "]\n");

        lexemeComposite.add(wordComposite);
        lexemeComposite.add(signComposite);
        return lexemeComposite;
    }

    private Composite parseSentence(String sentence) {
        Composite sentenceComposite = new Composite(Separator.FOR_WORDS, ComponentType.SENTENCE);
        String[] lexemes = sentence.split(WORD_REGEX);
        logger.debug("WORDS:\n");
        for(String lexeme : lexemes) {
            if(!lexeme.isEmpty()) {
                Composite lexemeComposite;
                if(checkSign(lexeme)) {
                    lexemeComposite = separateWordAndSign(lexeme);
                } else {
                    lexemeComposite = nextHandler.handle(lexeme);
                }
                sentenceComposite.add(lexemeComposite);
            }
        }
        System.out.println();
        return sentenceComposite;
    }

    @Override
    public Composite handle(String sentence) {
        Composite sentenceComposite = parseSentence(sentence);
        return sentenceComposite;
    }
}
