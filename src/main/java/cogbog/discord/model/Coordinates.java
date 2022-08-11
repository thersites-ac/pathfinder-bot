package cogbog.discord.model;

import lombok.Builder;
import lombok.Getter;

import java.net.URL;

@Builder
@Getter
public class Coordinates {
    private final URL url;
    private final String key;
    private final String prefix;
    private final String recordingId;
}
