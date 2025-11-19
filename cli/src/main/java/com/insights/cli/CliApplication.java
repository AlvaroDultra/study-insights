package com.insights.cli;

import com.insights.cli.commands.AddCommand;
import com.insights.cli.commands.InsightsCommand;
import com.insights.cli.commands.StatsCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        name = "study",
        description = "Study Insights CLI - Gerencie suas sessões de estudo",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        subcommands = {
                CommandLine.HelpCommand.class,
                AddCommand.class,
                StatsCommand.class,
                InsightsCommand.class
        }
)
public class CliApplication implements Runnable {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CliApplication()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("🎓 Study Insights CLI");
        System.out.println("Use 'study --help' para ver os comandos disponíveis");
    }
}