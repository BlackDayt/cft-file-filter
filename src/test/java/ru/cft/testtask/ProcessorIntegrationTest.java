package ru.cft.testtask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;
import ru.cft.testtask.cli.Args;
import ru.cft.testtask.core.Processor;
import ru.cft.testtask.stats.StatsCollector;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessorIntegrationTest {

    @TempDir
    Path tempDir;

    @Test
    void splits_files_like_in_task_example() throws IOException {
        Path in1 = tempDir.resolve("in1.txt");
        Path in2 = tempDir.resolve("in2.txt");
        Path outDir = tempDir.resolve("out");

        writeLines(in1, List.of(
                "Lorem ipsum dolor sit amet",
                "45",
                "Пример",
                "3.1415",
                "consectetur adipiscing",
                "-0.001",
                "тестовое задание",
                "100500"
        ));

        writeLines(in2, List.of(
                "Нормальная форма числа с плавающей запятой",
                "1234567890123456789",
                "1.528535047E-25",
                "Long"
        ));

        Args args = parseArgs("-o", outDir.toString(), in1.toString(), in2.toString());
        StatsCollector stats = Processor.process(args);

        assertEquals(List.of("45", "100500", "1234567890123456789"),
                readLines(outDir.resolve("integers.txt")));

        assertEquals(List.of("3.1415", "-0.001", "1.528535047E-25"),
                readLines(outDir.resolve("floats.txt")));

        assertEquals(List.of(
                        "Lorem ipsum dolor sit amet",
                        "Пример",
                        "consectetur adipiscing",
                        "тестовое задание",
                        "Нормальная форма числа с плавающей запятой",
                        "Long"
                ),
                readLines(outDir.resolve("strings.txt")));

        assertEquals(
                "integers: 3\nfloats: 3\nstrings: 6".replace("\n", System.lineSeparator()),
                stats.renderShort()
        );
    }

    private static Args parseArgs(String... argv) {
        Args args = new Args();
        new CommandLine(args).parseArgs(argv);
        args.validate();
        return args;
    }

    private static void writeLines(Path file, List<String> lines) throws IOException {
        Files.write(file, lines, StandardCharsets.UTF_8);
    }

    private static List<String> readLines(Path file) throws IOException {
        return Files.readAllLines(file, StandardCharsets.UTF_8);
    }
}
