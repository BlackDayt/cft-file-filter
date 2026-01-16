package ru.cft.testtask.cli;

import lombok.Getter;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Args — "контракт" командной строки утилиты.
 */
@Getter
@CommandLine.Command(
        name = "file-filter",
        mixinStandardHelpOptions = true,
        description = "Filters lines from input files into integers/floats/strings output files."
)
public class Args {

    /**
     * -o <dir> — директория для выходных файлов.
     * По умолчанию текущая директория.
     */
    @CommandLine.Option(
            names = "-o",
            paramLabel = "<dir>",
            description = "Output directory. Default: current directory."
    )
    private Path outputDir = Path.of(".");

    /**
     * -p <prefix> — префикс имен выходных файлов.
     */
    @CommandLine.Option(
            names = "-p",
            paramLabel = "<prefix>",
            description = "Prefix for output file names. Default: empty."
    )
    private String prefix = "";

    /**
     * -a — режим добавления (append).
     * Если включено — не перезаписывает файлы, а дописывает в конец.
     */
    @CommandLine.Option(
            names = "-a",
            description = "Append to existing output files instead of overwriting."
    )
    private boolean append;

    /**
     * -s — краткая статистика.
     */
    @CommandLine.Option(
            names = "-s",
            description = "Print short statistics (counts only)."
    )
    private boolean shortStats;

    /**
     * -f — полная статистика.
     */
    @CommandLine.Option(
            names = "-f",
            description = "Print full statistics (min/max/sum/avg for numbers; min/max length for strings)."
    )
    private boolean fullStats;

    /**
     * Позиционные аргументы: список входных файлов.
     */
    @CommandLine.Parameters(
            paramLabel = "<inputFiles>",
            arity = "1..*", // минимум 1 файл обязателен
            description = "Input text files to process."
    )
    private List<Path> inputFiles = new ArrayList<>();

    /**
     * validate() проверка аргументов.
     */
    public void validate() {
        if (shortStats && fullStats) {
            throw new CommandLine.ParameterException(
                    new CommandLine(this),
                    "Options -s and -f cannot be used together. Choose either short (-s) or full (-f) statistics."
            );
        }
    }
}