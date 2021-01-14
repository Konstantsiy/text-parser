package com.epam.handling.entity;

public enum Separator {
    FOR_PARAGRAPHS ("\r\n"),
    FOR_SENTENCES (" "),
    FOR_WORDS(" "),
    FOR_SYMBOLS("");

    private final String str;

    Separator(String s) {
        this.str = s;
    }

    public String get() {
        return str;
    }

    @Override
    public String toString() {
        return str;
    }
}
