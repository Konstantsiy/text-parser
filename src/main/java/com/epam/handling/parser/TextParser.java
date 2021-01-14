package com.epam.handling.parser;


import com.epam.handling.entity.ComponentType;
import com.epam.handling.entity.Composite;
import com.epam.handling.entity.Separator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class TextParser extends AbstractParser {
    private static final Logger logger = LogManager.getLogger(TextParser.class);

    private final String PARAGRAPH_REGEX = "\\r\\n\\s";

    public TextParser() {
        this.nextHandler = new ParagraphParser();
    }

    private Composite parseText(String text) {
        Composite paragraphsComposite = new Composite(Separator.FOR_PARAGRAPHS, ComponentType.TEXT);
        String[] paragraphs = text.split(PARAGRAPH_REGEX);
        for(int i = 0; i < paragraphs.length; i++) {
            logger.debug("----------------------------PARAGRAPH(" + (i + 1) + ")------------------------------\n");
            Composite parsedParagraph = nextHandler.handle(paragraphs[i]);
            paragraphsComposite.add(parsedParagraph);
        }
        logger.debug("\n------------------------------------------------------------------\n");
        return paragraphsComposite;
    }

    @Override
    public Composite handle(String text) {
        Composite textComposite = parseText(text);
        return textComposite;
    }
}
