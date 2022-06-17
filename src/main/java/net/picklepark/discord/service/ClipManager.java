package net.picklepark.discord.service;

import net.picklepark.discord.command.audio.PlayClipCommand;
import net.picklepark.discord.model.LocalClip;

import java.util.Collection;

// todo: this is really a local cache. rename? generify? replace with some library cache?
public interface ClipManager {
    PlayClipCommand lookup(String guild, String command);
    void put(LocalClip clip);
    Collection<String> getAllCommandNames(String guild);
    void delete(String guild, String clip);
    void clear(String guild);
}
