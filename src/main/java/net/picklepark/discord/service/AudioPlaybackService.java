package net.picklepark.discord.service;

import net.picklepark.discord.adaptor.LavaPlayerInputStreamAdaptor;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;

public interface AudioPlaybackService {
    void setChannelOne(AudioInputStream channelOne);
    void setChannelTwo(AudioInputStream channelTwo);
    boolean hasNext() throws IOException;
    byte[] nextTwentyMs() throws IOException;
}
