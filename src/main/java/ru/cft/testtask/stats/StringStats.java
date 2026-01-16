package ru.cft.testtask.stats;

public class StringStats {
    private long count = 0;
    private int minLen = Integer.MAX_VALUE;
    private int maxLen = Integer.MIN_VALUE;

    public void accept(String s) {
        if (s == null) return;

        count++;
        int len = s.length();
        if (len < minLen) minLen = len;
        if (len > maxLen) maxLen = len;
    }

    public long getCount() {
        return count;
    }

    public String renderFull() {
        if (count == 0) return "strings: 0";
        return "strings: " + count + ", minLen=" + minLen + ", maxLen=" + maxLen;
    }
}
