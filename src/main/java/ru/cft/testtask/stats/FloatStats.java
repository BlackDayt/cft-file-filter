package ru.cft.testtask.stats;

import java.math.BigDecimal;
import java.math.MathContext;

public class FloatStats {
    private long count = 0;
    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal min = null;
    private BigDecimal max = null;

    public void accept(BigDecimal v) {
        if (v == null) return;

        count++;
        sum = sum.add(v);

        if (min == null || v.compareTo(min) < 0) min = v;
        if (max == null || v.compareTo(max) > 0) max = v;
    }

    public long getCount() {
        return count;
    }

    public String renderFull() {
        if (count == 0) return "floats: 0";
        BigDecimal avg = sum.divide(BigDecimal.valueOf(count), MathContext.DECIMAL128);

        return "floats: " + count +
                ", min=" + min.toPlainString() +
                ", max=" + max.toPlainString() +
                ", sum=" + sum.toPlainString() +
                ", avg=" + avg.toPlainString();
    }
}
