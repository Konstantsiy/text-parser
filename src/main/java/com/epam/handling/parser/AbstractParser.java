package com.epam.handling.parser;


import com.epam.handling.entity.Composite;
import com.epam.handling.exception.ParserException;

public abstract class AbstractParser {
    protected AbstractParser nextHandler;

    public abstract Composite handle(String text);

    public Composite parseToComposite(String text) {
        Composite composite = handle(text);
        return composite;
    }
}
