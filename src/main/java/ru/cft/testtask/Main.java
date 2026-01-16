package ru.cft.testtask;

import picocli.CommandLine;
import ru.cft.testtask.cli.Args;
import ru.cft.testtask.core.Processor;
import ru.cft.testtask.stats.StatsCollector;

public class Main {

    private static final int EXIT_OK = 0;
    private static final int EXIT_RUNTIME_ERROR = 1;
    private static final int EXIT_USAGE_ERROR = 2;

    public static void main(String[] args) {
        System.exit(run(args));
    }

    static int run(String[] rawArgs) {
        Args args = new Args();
        CommandLine cmd = new CommandLine(args);

        try {
            CommandLine.ParseResult parseResult = cmd.parseArgs(rawArgs);

            if (parseResult.isUsageHelpRequested()) {
                cmd.usage(System.out);
                return EXIT_OK;
            }
            if (parseResult.isVersionHelpRequested()) {
                cmd.printVersionHelp(System.out);
                return EXIT_OK;
            }

            args.validate();

            StatsCollector stats = Processor.process(args);

            if (args.isShortStats()) {
                System.out.println(stats.renderShort());
            } else if (args.isFullStats()) {
                System.out.println(stats.renderFull());
            }

            return EXIT_OK;

        } catch (CommandLine.ParameterException e) {
            System.err.println(e.getMessage());
            cmd.usage(System.err);
            return EXIT_USAGE_ERROR;

        } catch (java.io.UncheckedIOException e) {
            System.err.println("Runtime error: " + e.getMessage());
            return EXIT_RUNTIME_ERROR;

        } catch (Exception e) {
            System.err.println("Runtime error: " + e.getMessage());
            return EXIT_RUNTIME_ERROR;
        }
    }
}
