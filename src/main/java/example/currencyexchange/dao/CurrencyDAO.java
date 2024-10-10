package example.currencyexchange.dao;//package example.currencyexchange.model.dao;
//
//import example.currencyexchange.config.DataBaseConfig;
//import example.currencyexchange.model.Currencies;
//import lombok.SneakyThrows;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.*;
//
//public class CurrencyDAO {
//
//    /**
//     * db name: "currencyexchange",
//     * table name: "currencies"
//     *
//     * @return List Currencies
//     */
//    public List<Currencies> getCurrencies() throws SQLException {
//        String query = "SELECT * from currencies";
//        ResultSet resultSet = DataBaseConfig.connect(query);
//        return parsing(Objects.requireNonNull(resultSet));
//    }
//
//    /**
//     * Parsing currency in table "currencies"
//     *
//     * @param rs ResultSet
//     * @return List currencies
//     */
//    @SneakyThrows
//    private List<Currencies> parsing(ResultSet rs) {
//        List<Currencies> list = new ArrayList<>();
//        while (rs.next()) {
//            String id = rs.getString(1);
//            String code = rs.getString(2);
//            String fullname = rs.getString(3);
//            String sign = rs.getString(4);
//
//            Currencies currencies = new Currencies(fullname, code, Integer.valueOf(id), Integer.valueOf(sign));
//            list.add(currencies);
//        }
//        return list;
//    }
//
//
////
////    /**
////     * Adds an object to the database
////     *
////     * @param currencyName String
////     * @param currencyCode String
////     * @param currencySign String
////     * @throws NoSuchMethodException Exception - may be thrown if the object exists in the database
////     */
////    @SneakyThrows
////    public static void setCurrency(String currencyName, String currencyCode, String currencySign) throws NoSuchMethodException {
////        if (findCodeCurrency(currencyCode) != null) {
////            throw new NoSuchMethodException();
////        }
////        String query = """
////                INSERT INTO currencies (fullname, code, sign)
////                VALUES (?, ?, ?)
////                ON CONFLICT (code) DO NOTHING""";
////
////        DataBaseConfig.connect(query, currencyName, currencyCode.toUpperCase(), currencySign);
////    }
////
//
////
////    /**
////     * Find code in table "Currencies"
////     *
////     * @param code String
////     * @return If there is no object, it will return "null", otherwise it will return an object "Currencies".
////     */
////    @SneakyThrows
////    public static Currencies findCodeCurrency(String code) {
////        String query = "SELECT * from currencies where code = ?";
////        ResultSet rs = DataBaseConfig.connect(query, code.toUpperCase());
////        try {
////            return parsing(rs).getFirst();
////        } catch (NoSuchElementException e) {
////            return null;
////        }
////    }
//
//}

import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.model.Currency;
import example.currencyexchange.model.exceptions.code_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.code_500.DataBaseNotAvailable;
import example.currencyexchange.model.exceptions.code_400.CurrencyPairNotExist;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CurrencyDAO implements DAOInterface<Currency, String> {

    @Getter
    private final static CurrencyDAO INSTANCE = new CurrencyDAO();
    private static final DataBaseConfig DB = DataBaseConfig.getCONNCECTION();

    private CurrencyDAO() {
    }


    @Override
    public Currency getModel(String baseCode, String targetCode) throws ObjectNotFound, DataBaseNotAvailable {
        return null;
    }

    private Currency building(ResultSet rs) throws DataBaseNotAvailable {
        try {
            Integer id = rs.getInt(1);
            String codeCurrency = rs.getString(2);
            String name = rs.getString(3);
            Integer sign = rs.getInt(4);
            return new Currency(name, codeCurrency, id, sign);

        } catch (SQLException e) {
            throw new DataBaseNotAvailable();
        }
    }


    @Override
    public Currency getModel(String code) throws ObjectNotFound, DataBaseNotAvailable {
        String query = "SELECT * FROM currencies WHERE code = ?";
        ResultSet rs = DB.connect(query, code.toUpperCase());
        try {
            if (rs.next()) {
                return building(rs);
            }

        } catch (SQLException | DataBaseNotAvailable e) {
            throw new DataBaseNotAvailable();
        }
        throw new ObjectNotFound("Currency not found");
    }

    @Override
    public List<Currency> getModelAll() {

        String query = "SELECT * FROM currencies";
        ResultSet rs = DB.connect(query);

        try {
            List<Currency> currencies = new ArrayList<>();
            while (rs.next()) {
                currencies.add(building(rs));
            }
            if (currencies.isEmpty()) {
                throw new ObjectNotFound("Currencies not found");
            }
            return currencies;

        } catch (SQLException | DataBaseNotAvailable e) {
            throw new DataBaseNotAvailable();
        }
    }

    @Override
    public void addModel(String name, String code, String sign) throws DataBaseNotAvailable {
        String query = """
                INSERT INTO currencies (fullname, code, sign)
                VALUES (?, ?, ?)
                """;

        DB.connect(query, name, code, sign);
    }
}
