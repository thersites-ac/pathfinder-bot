package net.picklepark.discord.exception;

public class AmbiguousUserException extends UserIdentificationException {
    public AmbiguousUserException(String user) {
        super(user);
    }
}
