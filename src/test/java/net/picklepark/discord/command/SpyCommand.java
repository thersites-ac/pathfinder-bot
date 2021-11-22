package net.picklepark.discord.command;

import net.picklepark.discord.adaptor.DiscordActions;
import net.picklepark.discord.constants.AuthLevel;
import net.picklepark.discord.exception.DiscordCommandException;
import net.picklepark.discord.exception.UnimplementedException;

public class SpyCommand implements DiscordCommand {
    private boolean executed;

    @Override
    public void execute(DiscordActions actions) throws DiscordCommandException {
        this.executed = true;
    }

    @Override
    public AuthLevel requiredAuthLevel() {
        return AuthLevel.ANY;
    }

    @Override
    public String example() {
        throw new UnimplementedException();
    }

    @Override
    public String helpMessage() {
        throw new UnimplementedException();
    }

    @Override
    public String userInput() {
        throw new UnimplementedException();
    }

    public boolean isExecuted() {
        return executed;
    }
}
