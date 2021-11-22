package net.picklepark.discord.service.impl;

import net.picklepark.discord.adaptor.DiscordActions;
import net.picklepark.discord.constants.AuthLevel;
import net.picklepark.discord.service.AuthService;

public class TestAuthService implements AuthService {

    private boolean answer;

    public void setAuthDecision(boolean answer) {
        this.answer = answer;
    }

    @Override
    public boolean isActionAuthorized(DiscordActions actions, AuthLevel level) {
        return answer;
    }

    @Override
    public void addAdmin(String channelName, long user) {
    }
}
