package ru.cft.testtask.stats;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class IntStats {
    private long count = 0;
    private BigInteger sum = BigInteger.ZERO;
    private BigInteger min = null;
    private BigInteger max = null;

    public void accept(BigInteger v) {
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
        if (count == 0) return "integers: 0";
        BigDecimal avg = new BigDecimal(sum).divide(BigDecimal.valueOf(count), MathContext.DECIMAL128);

        return "integers: " + count +
                ", min=" + min +
                ", max=" + max +
                ", sum=" + sum +
                ", avg=" + avg.toPlainString();
    }
}
