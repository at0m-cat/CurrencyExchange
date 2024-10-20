package example.currencyexchange.config;

import example.currencyexchange.exceptions.IncorrectParamsException;
import example.currencyexchange.exceptions.ObjectAlreadyExistException;
import example.currencyexchange.exceptions.DataBaseNotAvailableException;
import lombok.Getter;

import java.sql.*;

public final class DataBaseConnect {

    @Getter
    private static final DataBaseConnect instance = new DataBaseConnect();
    private final DataBaseConfig config;

    private DataBaseConnect() {
        this.config = DataBaseConfig.getInstance();
    }

    public ResultSet connect(String query, Object... params) {
        try {
            Class.forName(config.jdbcDriver);
            Connection connection = DriverManager
                    .getConnection(config.databaseUrl, config.databaseUser, config.DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            ResultSet resultSet = null;
            if (query.trim().toUpperCase().startsWith("SELECT")
                    || query.trim().toUpperCase().startsWith("WITH")) {
                resultSet = preparedStatement.executeQuery();
                connection.close();

            } else {
                preparedStatement.executeUpdate();
                connection.close();
            }

            return resultSet;

        } catch (ClassNotFoundException e) {
            throw new DataBaseNotAvailableException();

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ObjectAlreadyExistException("a record with this object already exists");

            } else if (e.getSQLState().equals("42804")) {
                throw new IncorrectParamsException();
            }
            else if (e.getSQLState().equals("08001")) {
                throw new DataBaseNotAvailableException("database connection lost");
            }
            throw new DataBaseNotAvailableException(e.getSQLState());
        }

    }
}
