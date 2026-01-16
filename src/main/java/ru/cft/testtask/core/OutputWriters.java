package ru.cft.testtask.core;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class OutputWriters implements Closeable {

    private final Path outputDir;
    private final String prefix;
    private final boolean append;

    private BufferedWriter intWriter;
    private BufferedWriter floatWriter;
    private BufferedWriter stringWriter;

    public OutputWriters(Path outputDir, String prefix, boolean append) {
        this.outputDir = outputDir == null ? Path.of(".") : outputDir;
        this.prefix = prefix == null ? "" : prefix;
        this.append = append;
    }

    public void write(LineType type, String line) throws IOException {
        if (type == null) return;
        BufferedWriter w = writerFor(type);
        w.write(line);
        w.newLine();
    }

    private BufferedWriter writerFor(LineType type) throws IOException {
        return switch (type) {
            case INTEGER -> writerIntegers();
            case FLOAT -> writerFloats();
            case STRING -> writerStrings();
        };
    }

    private BufferedWriter writerIntegers() throws IOException {
        if (intWriter == null) intWriter = open(prefix + "integers.txt");
        return intWriter;
    }

    private BufferedWriter writerFloats() throws IOException {
        if (floatWriter == null) floatWriter = open(prefix + "floats.txt");
        return floatWriter;
    }

    private BufferedWriter writerStrings() throws IOException {
        if (stringWriter == null) stringWriter = open(prefix + "strings.txt");
        return stringWriter;
    }

    private BufferedWriter open(String fileName) throws IOException {
        Files.createDirectories(outputDir);
        Path file = outputDir.resolve(fileName);

        if (append) {
            return Files.newBufferedWriter(
                    file,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.APPEND
            );
        }

        return Files.newBufferedWriter(
                file,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    @Override
    public void close() throws IOException {
        IOException first = null;

        first = closeOne(intWriter, first);
        first = closeOne(floatWriter, first);
        first = closeOne(stringWriter, first);

        if (first != null) throw first;
    }

    private IOException closeOne(BufferedWriter w, IOException first) {
        if (w == null) return first;
        try {
            w.close();
            return first;
        } catch (IOException e) {
            return first == null ? e : first;
        }
    }
}
