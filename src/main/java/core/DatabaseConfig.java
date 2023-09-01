package core;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({ "file:src/app.properties" })
public interface DatabaseConfig extends Config {
    @Key("database.url")
    String databaseUrl();

    @Key("database.user")
    String databaseUser();

    @Key("database.pass")
    String databasePass();
}
