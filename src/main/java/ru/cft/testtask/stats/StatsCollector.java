package ru.cft.testtask.stats;

import ru.cft.testtask.core.ClassifiedLine;
import ru.cft.testtask.core.LineType;

public class StatsCollector {
    private final IntStats intStats = new IntStats();
    private final FloatStats floatStats = new FloatStats();
    private final StringStats stringStats = new StringStats();

    public void accept(LineType type, String originalLine, ClassifiedLine classified) {
        if (type == null) return;

        switch (type) {
            case INTEGER -> intStats.accept(classified.intValue());
            case FLOAT -> floatStats.accept(classified.floatValue());
            case STRING -> stringStats.accept(originalLine);
        }
    }

    public String renderShort() {
        return "integers: " + intStats.getCount() + System.lineSeparator() +
                "floats: " + floatStats.getCount() + System.lineSeparator() +
                "strings: " + stringStats.getCount();
    }

    public String renderFull() {
        return intStats.renderFull() + System.lineSeparator() +
                floatStats.renderFull() + System.lineSeparator() +
                stringStats.renderFull();
    }
}
