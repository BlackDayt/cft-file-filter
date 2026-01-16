package ru.cft.testtask.core;

import ru.cft.testtask.cli.Args;
import ru.cft.testtask.stats.StatsCollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Processor {

    public static StatsCollector process(Args args) throws IOException {
        ensureOutputDir(args.getOutputDir());

        LineClassifier classifier = new LineClassifier();
        StatsCollector stats = new StatsCollector();

        try (OutputWriters writers = new OutputWriters(args.getOutputDir(), args.getPrefix(), args.isAppend())) {
            for (Path input : args.getInputFiles()) {
                processOneFile(input, classifier, writers, stats);
            }
        }

        return stats;
    }

    private static void processOneFile(
            Path input,
            LineClassifier classifier,
            OutputWriters writers,
            StatsCollector stats
    ) {
        try (BufferedReader reader = Files.newBufferedReader(input, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                ClassifiedLine cl = classifier.classify(line);

                try {
                    writers.write(cl.type(), line);
                } catch (IOException e) {
                    throw new java.io.UncheckedIOException("Cannot write output file: " + e.getMessage(), e);
                }

                stats.accept(cl.type(), line, cl);
            }
        } catch (IOException e) {
            System.err.println("Cannot read file: " + input + " (" + e.getMessage() + ")");
        }
    }

    private static void ensureOutputDir(Path outputDir) throws IOException {
        Path dir = (outputDir == null) ? Path.of(".") : outputDir;

        if (Files.exists(dir) && !Files.isDirectory(dir)) {
            throw new IOException("Output path is not a directory: " + dir);
        }

        Files.createDirectories(dir);

        if (!Files.isDirectory(dir)) {
            throw new IOException("Output path is not a directory: " + dir);
        }

        if (!Files.isWritable(dir)) {
            throw new IOException("Output directory is not writable: " + dir);
        }
    }
}
