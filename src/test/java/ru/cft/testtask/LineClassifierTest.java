package ru.cft.testtask;

import org.junit.jupiter.api.Test;
import ru.cft.testtask.core.LineClassifier;
import ru.cft.testtask.core.LineType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineClassifierTest {

    private final LineClassifier classifier = new LineClassifier();

    @Test
    void classify_examples() {
        assertEquals(LineType.STRING, classifier.classify("Lorem ipsum dolor sit amet").type());
        assertEquals(LineType.INTEGER, classifier.classify("45").type());
        assertEquals(LineType.STRING, classifier.classify("Пример").type());
        assertEquals(LineType.FLOAT, classifier.classify("3.1415").type());
        assertEquals(LineType.STRING, classifier.classify("consectetur adipiscing").type());
        assertEquals(LineType.FLOAT, classifier.classify("-0.001").type());
        assertEquals(LineType.STRING, classifier.classify("тестовое задание").type());
        assertEquals(LineType.INTEGER, classifier.classify("100500").type());

        assertEquals(LineType.INTEGER, classifier.classify("1234567890123456789").type());
        assertEquals(LineType.FLOAT, classifier.classify("1.528535047E-25").type());
        assertEquals(LineType.STRING, classifier.classify("Long").type());
    }
}
