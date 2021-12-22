package net.picklepark.discord.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.picklepark.discord.handler.send.MultichannelPlayerSendHandler;
import net.picklepark.discord.service.AudioPlaybackService;

public class AudioContext {

    public final TextChannel channel;
    public final GuildPlayer guildPlayer;
    public final AudioPlayerManager playerManager;

    public AudioContext(TextChannel channel, GuildPlayer guildPlayer, AudioPlayerManager playerManager) {
        this.channel = channel;
        this.guildPlayer = guildPlayer;
        this.playerManager = playerManager;
    }
}