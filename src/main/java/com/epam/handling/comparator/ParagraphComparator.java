package com.epam.handling.comparator;

import com.epam.handling.entity.Composite;

import java.util.Comparator;


public enum ParagraphComparator implements Comparator<Composite> {
    SENTENCES_COUNT {
        @Override
        public int compare(Composite paragraph1, Composite paragraph2) {
            return Integer.compare(paragraph1.size(), paragraph2.size());
        }
    };
}
