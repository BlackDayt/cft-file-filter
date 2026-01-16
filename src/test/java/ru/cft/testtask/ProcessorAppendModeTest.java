package ru.cft.testtask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;
import ru.cft.testtask.cli.Args;
import ru.cft.testtask.core.Processor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessorAppendModeTest {

    @TempDir
    Path tempDir;

    @Test
    void appendModeAddsLines() throws IOException {
        Path in = tempDir.resolve("in.txt");
        Path out = tempDir.resolve("out");

        Files.write(in, List.of("1", "2", "a"), StandardCharsets.UTF_8);

        Args first = parseArgs("-o", out.toString(), in.toString());
        Processor.process(first);

        Args second = parseArgs("-a", "-o", out.toString(), in.toString());
        Processor.process(second);

        List<String> ints = Files.readAllLines(out.resolve("integers.txt"), StandardCharsets.UTF_8);
        List<String> strs = Files.readAllLines(out.resolve("strings.txt"), StandardCharsets.UTF_8);

        assertEquals(List.of("1", "2", "1", "2"), ints);
        assertEquals(List.of("a", "a"), strs);
    }

    private static Args parseArgs(String... argv) {
        Args args = new Args();
        new CommandLine(args).parseArgs(argv);
        args.validate();
        return args;
    }
}
