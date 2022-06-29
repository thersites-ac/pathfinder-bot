package net.picklepark.discord.persistence;

import net.picklepark.discord.exception.DataMappingException;
import net.picklepark.discord.model.AuthLevel;
import net.picklepark.discord.model.AuthRecord;

import javax.inject.Singleton;
import java.util.Map;

// TODO (important): tests, especially fromMap
@Singleton
public class AuthRecordMappingFactory implements MappingFactory<AuthRecord> {

    public static final String USER_ID = "user_id";
    public static final String GUILD_ID = "guild_id";

    private static final String LEVEL = "level";
    private static final String TABLE = "hippokleides_bans";

    @Override
    public Map<String, String> toMap(AuthRecord object) {
        return Map.of(
                USER_ID, object.getUserId() + "",
                GUILD_ID, object.getGuildId(),
                LEVEL, object.getLevel().toString()
        );
    }

    @Override
    public AuthRecord fromMap(Map<String, String> map) throws DataMappingException {
        String guild = map.get(GUILD_ID);
        String user = map.get(USER_ID);
        String level = map.get(LEVEL);
        try {
            return AuthRecord.builder()
                    .userId(Long.parseLong(user))
                    .guildId(guild)
                    .level(AuthLevel.valueOf(level))
                    .build();
        } catch (Exception ex) {
            throw new DataMappingException(map, ex);
        }
    }

    @Override
    public String getTable() {
        return TABLE;
    }
}
