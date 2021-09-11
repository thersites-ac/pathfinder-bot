package net.picklepark.discord;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.picklepark.discord.command.DiscordCommandRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.dv8tion.jda.api.requests.GatewayIntent.GUILD_MESSAGES;
import static net.dv8tion.jda.api.requests.GatewayIntent.GUILD_VOICE_STATES;

public class Bot extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    public static void main(String[] args) throws Exception {
        JDABuilder.create(System.getProperty("token"), GUILD_MESSAGES, GUILD_VOICE_STATES)
                .addEventListeners(new Bot())
                .build();
    }

    private final DiscordCommandRegistry registry;

    private Bot() {
        registry = new DiscordCommandRegistry();
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        executeAsCommand(event);
        super.onGuildMessageReceived(event);
    }

    private void executeAsCommand(GuildMessageReceivedEvent event) {
        try {
            registry.execute(event);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            event.getChannel().sendMessage("Oh no, I'm broken!").queue();
        }
    }

}
