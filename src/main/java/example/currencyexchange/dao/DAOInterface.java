package example.currencyexchange.dao;

import example.currencyexchange.exceptions.ObjectNotFoundException;
import example.currencyexchange.exceptions.DataBaseNotAvailableException;

import java.sql.ResultSet;
import java.util.List;


interface DAOInterface<T, K> {


    List<T> findAll();

    T find(K code);

    T find(K baseCode, K targetCode);

    void save(K name, K code, K sign);

}
