package ru.cft.testtask.core;

import java.math.BigDecimal;
import java.math.BigInteger;

public record ClassifiedLine(
        LineType type,
        BigInteger intValue,
        BigDecimal floatValue
) {
    public static ClassifiedLine ofInt(BigInteger v) {
        return new ClassifiedLine(LineType.INTEGER, v, null);
    }

    public static ClassifiedLine ofFloat(BigDecimal v) {
        return new ClassifiedLine(LineType.FLOAT, null, v);
    }

    public static ClassifiedLine ofString() {
        return new ClassifiedLine(LineType.STRING, null, null);
    }
}
