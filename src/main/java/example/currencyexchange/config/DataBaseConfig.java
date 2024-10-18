package example.currencyexchange.config;

import lombok.Getter;

public class DataBaseConfig {

    @Getter
    private static final DataBaseConfig instance = new DataBaseConfig();

    private DataBaseConfig(){
    }

    public final String jdbcDriver = "org.postgresql.Driver";
    public final String databaseUrl = "jdbc:postgresql://localhost:5432/currencyexchange";
//    public final String databaseUrl = "jdbc:postgresql://db:5432/currencyexchange";
    public final String databaseUser = "postgres";
    public final String DATABASE_PASSWORD = "yourpassword";
}


