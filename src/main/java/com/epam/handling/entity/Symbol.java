package com.epam.handling.entity;

public class Symbol implements Component {
    private String character;

    public Symbol(String c) {
        this.character = c;
    }

    @Override
    public StringBuilder buildText() {
        return new StringBuilder(character);
    }
}
