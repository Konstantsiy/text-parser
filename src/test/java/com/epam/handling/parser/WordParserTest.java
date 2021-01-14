package com.epam.handling.parser;

import com.epam.handling.entity.Composite;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WordParserTest {
    private final WordParser wordParser = new WordParser();

    @Test
    public void testParseLexeme() {
        String[] expressions = {
                "3>>5",
                "13<<2",
                "~6&9|(3&4)",
                "(~71&(2&3|(3|(2&1>>2|2)&2)|10&2))|78",
                "(7^5|1&2<<(2|5>>2&71))|1200"
        };
        Integer[] results = {
                3>>5,
                13<<2,
                ~6&9|(3&4),
                (~71&(2&3|(3|(2&1>>2|2)&2)|10&2))|78,
                (7^5|1&2<<(2|5>>2&71))|1200
        };
        for(int i = 0; i < expressions.length; i++) {
            Composite lexemeComposite = wordParser.parseLexeme(expressions[i]);
            String parsedLexeme = lexemeComposite.buildText().toString();
            Assert.assertEquals(results[i], Integer.valueOf(parsedLexeme));
        }
    }
}