package example.currencyexchange.config;

import lombok.Getter;

public class DataBaseConfig {

    @Getter
    private static final DataBaseConfig CONFIG = new DataBaseConfig();

    private DataBaseConfig(){
    }

    public final String JDBC_DRIVER = "org.postgresql.Driver";
    public final String DATABASE_URL = "jdbc:postgresql://db:5432/currencyexchange";
    public final String DATABASE_USER = "postgres";
    public final String DATABASE_PASSWORD = "yourpassword";
}


