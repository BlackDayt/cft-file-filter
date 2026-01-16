package ru.cft.testtask.core;

import java.math.BigDecimal;
import java.math.BigInteger;

public class LineClassifier {

    public ClassifiedLine classify(String s) {
        if (s == null || s.isEmpty()) return ClassifiedLine.ofString();

        boolean hasDot = false;
        boolean hasExp = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c >= '0' && c <= '9') continue;

            if (c == '.') { hasDot = true; continue; }
            if (c == 'e' || c == 'E') { hasExp = true; continue; }

            if (c == '+' || c == '-') continue;

            return ClassifiedLine.ofString();
        }

        if (!hasDot && !hasExp) {
            if (!looksLikeInteger(s)) return ClassifiedLine.ofString();
            try {
                return ClassifiedLine.ofInt(new BigInteger(s));
            } catch (NumberFormatException ex) {
                return ClassifiedLine.ofString();
            }
        }

        if (!looksLikeFloat(s)) return ClassifiedLine.ofString();
        try {
            return ClassifiedLine.ofFloat(new BigDecimal(s));
        } catch (NumberFormatException ex) {
            return ClassifiedLine.ofString();
        }
    }

    private boolean looksLikeInteger(String s) {
        int i = 0;
        int n = s.length();

        char first = s.charAt(0);
        if (first == '+' || first == '-') {
            if (n == 1) return false;
            i = 1;
        }

        for (; i < n; i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    private boolean looksLikeFloat(String s) {
        int i = 0;
        int n = s.length();

        char first = s.charAt(0);
        if (first == '+' || first == '-') {
            if (n == 1) return false;
            i = 1;
        }

        boolean dotSeen = false;
        boolean expSeen = false;

        boolean mantissaHasDigit = false;
        boolean exponentHasDigit = false;

        for (; i < n; i++) {
            char c = s.charAt(i);

            if (c >= '0' && c <= '9') {
                if (!expSeen) mantissaHasDigit = true;
                else exponentHasDigit = true;
                continue;
            }

            if (c == '.') {
                if (dotSeen || expSeen) return false;
                dotSeen = true;
                continue;
            }

            if (c == 'e' || c == 'E') {
                if (expSeen) return false;
                if (!mantissaHasDigit) return false;
                expSeen = true;

                if (i + 1 >= n) return false;
                char next = s.charAt(i + 1);
                if (next == '+' || next == '-') {
                    i++;
                    if (i + 1 >= n) return false;
                }
                continue;
            }

            if (c == '+' || c == '-') return false;

            return false;
        }

        if (!mantissaHasDigit) return false;
        if (expSeen && !exponentHasDigit) return false;

        return dotSeen || expSeen;
    }
}
