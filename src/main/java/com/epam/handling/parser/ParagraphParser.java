package com.epam.handling.parser;

import com.epam.handling.entity.ComponentType;
import com.epam.handling.entity.Composite;
import com.epam.handling.entity.Separator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ParagraphParser extends AbstractParser {
    private static final Logger logger = LogManager.getLogger(ParagraphParser.class);

    private final String SENTENCE_REGEX = "(?<=[.!?:])\\s+";

    public ParagraphParser() {
        this.nextHandler = new SentenceParser();
    }

    private Composite parseParagraph(String paragraph) {
        Composite paragraphComposite = new Composite(Separator.FOR_SENTENCES, ComponentType.PARAGRAPH);
        String[] sentences = paragraph.split(SENTENCE_REGEX);
        for(int i = 0; i < sentences.length; i++) {
            logger.debug("----------------------------SENTENCE(" + (i + 1) + ")-------------------------------\n");
            Composite sentenceComposite = nextHandler.handle(sentences[i]);
            paragraphComposite.add(sentenceComposite);
        }
        return paragraphComposite;
    }

    @Override
    public Composite handle(String paragraph) {
        Composite paragraphComposite = parseParagraph(paragraph);
        return paragraphComposite;
    }
}
