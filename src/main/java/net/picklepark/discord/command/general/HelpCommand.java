package net.picklepark.discord.command.general;

import net.picklepark.discord.annotation.Help;
import net.picklepark.discord.annotation.UserInput;
import net.picklepark.discord.command.DiscordCommand;
import net.picklepark.discord.adaptor.DiscordActions;
import net.picklepark.discord.command.DiscordCommandRegistry;
import org.checkerframework.checker.nullness.Opt;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@UserInput("help")
@Help(name = "help", message = "See this message again.")
public class HelpCommand implements DiscordCommand {

//    private static final String instructions = "Commands: ~queue [url], ~skip, ~volume (to get current), ~volume [n] (to set)," +
//            " ~louder, ~softer, ~pause, ~unpause, ~gtfo, ~feat [feat name], ~spell [spell name], ~help";
//    private static final String hint = "When you find a feat or spell, click the citation at top (e.g. Core Rulebook, " +
//            "Advanced Player's Guide, etc.) to go to the site.";

    private DiscordCommandRegistry registry;

    @Inject
    public HelpCommand(DiscordCommandRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void execute(DiscordActions actions) {
        Collection<DiscordCommand> commands = registry.getCommands();
        commands.stream()
                .filter(command -> command.getClass().isAnnotationPresent(Help.class))
                .map(this::commandHelpLine)
                .reduce((s, t) -> s + ",\n" + t)
                .ifPresentOrElse(body -> actions.send("I know these commands:\n" + body),
                        () -> actions.send("I don't know anything :("));
    }

    private String commandHelpLine(DiscordCommand command) {
        Help help = command.getClass().getAnnotation(Help.class);
        return help.name() + ": " + help.message();
    }
}
