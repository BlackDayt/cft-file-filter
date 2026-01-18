package ru.cft.testtask.cli;

import lombok.Getter;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Getter
@CommandLine.Command(
        name = "file-filter",
        mixinStandardHelpOptions = true,
        description = "Filters lines from input files into integers/floats/strings output files."
)
public class Args {

    @CommandLine.Option(
            names = "-o",
            paramLabel = "<dir>",
            description = "Output directory. Default: current directory."
    )
    private Path outputDir = Path.of(".");

    @CommandLine.Option(
            names = "-p",
            paramLabel = "<prefix>",
            description = "Prefix for output file names. Default: empty."
    )
    private String prefix = "";

    @CommandLine.Option(
            names = "-a",
            description = "Append to existing output files instead of overwriting."
    )
    private boolean append;

    @CommandLine.Option(
            names = "-s",
            description = "Print short statistics (counts only)."
    )
    private boolean shortStats;

    @CommandLine.Option(
            names = "-f",
            description = "Print full statistics (min/max/sum/avg for numbers; min/max length for strings)."
    )
    private boolean fullStats;

    @CommandLine.Parameters(
            paramLabel = "<inputFiles>",
            arity = "1..*",
            description = "Input text files to process."
    )
    private List<Path> inputFiles = new ArrayList<>();

    public void validate() {
        if (shortStats && fullStats) {
            throw new CommandLine.ParameterException(
                    new CommandLine(this),
                    "Options -s and -f cannot be used together. Choose either short (-s) or full (-f) statistics."
            );
        }
    }
}