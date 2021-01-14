package com.epam.handling.parser;

import com.epam.handling.entity.*;
import com.epam.handling.interpreter.ExpressionUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class WordParser extends AbstractParser {
    private static final Logger logger = LogManager.getLogger(WordParser.class);

    private final String SYMBOL_REGEX = "";
    private final String EXCLUDE_COLON = ":";
    private final String EXCLUDE_DASH = "-";
    private final String EXPRESSION_REGEX = "^[0-9|\\(|\\)|\\^|~|<|>|&|\\|| ]+$";
    private final String SPACE_DELETION_REGEX = "(?<=\\d) (?=\\d)";

    private final String FAULT1 = "> >";
    private final String FAULT2 = "< <";

    public WordParser() {}

    private boolean isSign(String word) {
        return word.equals(EXCLUDE_COLON) || word.equals(EXCLUDE_DASH);
    }

    private boolean isExpression(String word) {
        return word.matches(EXPRESSION_REGEX);
    }

    private String parseExpression(String expression) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < expression.length(); i++) {
            stringBuilder.append(expression.charAt(i));
            if(i != expression.length() - 1) {
                stringBuilder.append(" ");
            }
        }
        String parsedExpression = stringBuilder.toString();
        if(parsedExpression.contains(FAULT2)) {
            parsedExpression = parsedExpression.replaceAll(FAULT2, Operation.BIT_SHIFT_LEFT);
        }
        if(parsedExpression.contains(FAULT1)) {
            parsedExpression = parsedExpression.replaceAll(FAULT1, Operation.BIT_SHIFT_RIGHT);
        }
        return parsedExpression.replaceAll(SPACE_DELETION_REGEX, "");
    }

    public Composite parseLexeme(String word) {
        Composite wordComposite;
        boolean isWordIsExpression = false;
        if(isSign(word)) {
            Symbol sign = new Symbol(word);
            wordComposite = new Composite(Separator.FOR_SYMBOLS, ComponentType.SIGN);
            wordComposite.add(sign);
            logger.debug("[" + wordComposite.buildText() + " --- " + wordComposite.getType() + "]\n");
        } else {
            if(isExpression(word)) {
                isWordIsExpression = true;
                wordComposite = new Composite(Separator.FOR_SYMBOLS, ComponentType.EXPRESSION);
                logger.debug("[(" + word + ") => ");
                word = parseExpression(word);
                String polishRecord = ExpressionUtil.buildReversePolishRecord(word);
                logger.debug("(" + polishRecord + ") => ");
                int number = ExpressionUtil.calculateReversePolishRecord(polishRecord);
                word = String.valueOf(number);
                logger.debug(word + " --- " + wordComposite.getType() + "]\n");
            } else {
                wordComposite = new Composite(Separator.FOR_SYMBOLS, ComponentType.WORD);
            }
            String[] symbols = word.split(SYMBOL_REGEX);
            for(String c : symbols) {
                Symbol symbol = new Symbol(c);
                wordComposite.add(symbol);
            }
            if(!isWordIsExpression) {
                logger.debug("[" + wordComposite.buildText() + " --- " + wordComposite.getType() + "]\n");
            }
        }
        return wordComposite;
    }

    @Override
    public Composite handle(String word) {
        Composite wordComposite = parseLexeme(word);
        return wordComposite;
    }
}
