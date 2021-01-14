package com.epam.handling.service.impl;

import com.epam.handling.entity.Composite;
import com.epam.handling.exception.TextServiceException;
import com.epam.handling.parser.TextParser;
import com.epam.handling.service.TextService;
import com.epam.handling.writer.DataWriter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class TextServiceImplTest {
    private static final Logger logger = LogManager.getLogger(TextServiceImplTest.class);

    private final String FILE_NAME1 = "src/main/resources/data/text1.txt";
    private final String FILE_NAME2 = "src/main/resources/data/sort-test.txt";
    private final String FILE_NAME3 = "src/main/resources/data/search-test.txt";
    private final String FILE_NAME4 = "src/main/resources/data/remove-test.txt";
    private final String FILE_NAME5 = "src/main/resources/data/text2.txt";
    private final TextService textService = new TextServiceImpl();
    private final TextParser textParser = new TextParser();

    public String readFormFile(final String filename) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filename));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    @Test
    public void testSortParagraphBySentenceCount() throws IOException {
        String text = readFormFile(FILE_NAME1);
        String sortedText = textService.sortParagraphBySentenceCount(text);
        String result = readFormFile(FILE_NAME2);
        Assert.assertEquals(result, sortedText);
    }

    @Test
    public void testFindSentenceWithLongestWord() throws IOException {
        String text = readFormFile(FILE_NAME1);
        String neededSentence = textService.findSentenceWithLongestWord(text);
        String result = readFormFile(FILE_NAME3);
        Assert.assertEquals(result, neededSentence);
    }

    @Test
    public void testRemoveSentencesWithWordsFewerThan() throws IOException, TextServiceException {
        String text = readFormFile(FILE_NAME1);
        String changedText = textService.removeSentencesWithWordsFewerThan(text, 20);
        String result = readFormFile(FILE_NAME4);
        Assert.assertEquals(result, changedText);
    }

    @Test
    public void testCheckParserWork() throws IOException { // check log file
        String text = readFormFile(FILE_NAME5);
        Composite textComposite = textParser.parseToComposite(text);
        DataWriter writer = new DataWriter();
        String result = writer.print(textComposite);
    }

}