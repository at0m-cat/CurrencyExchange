package example.currencyexchange.config;

import example.currencyexchange.model.exceptions.code_400.IncorrectParams;
import example.currencyexchange.model.exceptions.code_409.ObjectAlreadyExist;
import example.currencyexchange.model.exceptions.code_500.DataBaseNotAvailable;
import lombok.Getter;

import java.sql.*;

public final class DataBaseConfig {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/currencyexchange";
    static final String DATABASE_USER = "postgres";
    static final String DATABASE_PASSWORD = "postgres";

    @Getter
    private static final DataBaseConfig CONNCECTION = new DataBaseConfig();

    public ResultSet connect(String query, Object... params){
        try {
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            ResultSet resultSet = null;
            if (query.trim().toUpperCase().startsWith("SELECT")
                    || query.trim().toUpperCase().startsWith("WITH RECURSIVE")) {
                resultSet = preparedStatement.executeQuery();
                connection.close();

            } else {
                preparedStatement.executeUpdate();
                connection.close();
            }

            return resultSet;

        } catch (ClassNotFoundException e) {
            throw new DataBaseNotAvailable();

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ObjectAlreadyExist("a record with this object already exists");
            }

            else if (e.getSQLState().equals("42804")){
                throw new IncorrectParams();
            }

            throw new DataBaseNotAvailable(e.getSQLState());
        }
    }
}
