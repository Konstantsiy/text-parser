package com.epam.handling.writer;

import com.epam.handling.entity.Composite;

public class DataWriter {
    public DataWriter() {}

    public String print(Composite composite) {
        StringBuilder result = composite.buildText();
        return result.toString();
    }
}
