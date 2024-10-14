package example.currencyexchange.config;

import example.currencyexchange.model.exceptions.status_400.IncorrectParams;
import example.currencyexchange.model.exceptions.status_409.ObjectAlreadyExist;
import example.currencyexchange.model.exceptions.status_500.DataBaseNotAvailable;
import lombok.Getter;

import java.sql.*;

public final class DataBaseConnect {

    @Getter
    private static final DataBaseConnect CONNCECTION = new DataBaseConnect();
    private DataBaseConfig config = DataBaseConfig.getCONFIG();

    private DataBaseConnect() {
    }

    public ResultSet connect(String query, Object... params)
            throws DataBaseNotAvailable, IncorrectParams, ObjectAlreadyExist {
        try {
            Class.forName(config.JDBC_DRIVER);
            Connection connection = DriverManager
                    .getConnection(config.DATABASE_URL, config.DATABASE_USER, config.DATABASE_PASSWORD);
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
            throw new DataBaseNotAvailable();

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ObjectAlreadyExist("a record with this object already exists");

            } else if (e.getSQLState().equals("42804")) {
                throw new IncorrectParams();
            }
            else if (e.getSQLState().equals("08001")) {
                throw new DataBaseNotAvailable("database connection lost");
            }
            throw new DataBaseNotAvailable(e.getSQLState());
        }
    }
}
