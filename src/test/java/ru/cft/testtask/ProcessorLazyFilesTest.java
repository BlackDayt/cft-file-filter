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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProcessorLazyFilesTest {

    @TempDir
    Path tempDir;

    @Test
    void doesNotCreateFilesForMissingTypes() throws IOException {
        Path in = tempDir.resolve("in.txt");
        Path out = tempDir.resolve("out");

        Files.write(in, List.of(
                "1",
                "2",
                "hello",
                "world"
        ), StandardCharsets.UTF_8);

        Args args = parseArgs("-o", out.toString(), in.toString());
        Processor.process(args);

        assertTrue(Files.exists(out.resolve("integers.txt")));
        assertTrue(Files.exists(out.resolve("strings.txt")));
        assertFalse(Files.exists(out.resolve("floats.txt")));
    }

    private static Args parseArgs(String... argv) {
        Args args = new Args();
        new CommandLine(args).parseArgs(argv);
        args.validate();
        return args;
    }
}
