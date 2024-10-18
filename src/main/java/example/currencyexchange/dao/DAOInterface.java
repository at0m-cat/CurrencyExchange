package example.currencyexchange.dao;

import example.currencyexchange.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.exceptions.status_500.DataBaseNotAvailable;

import java.sql.ResultSet;
import java.util.List;


interface DAOInterface<T, K> {

    T building(ResultSet rs) throws DataBaseNotAvailable;

    List<T> findAll();

    T find(K code) throws ObjectNotFound, DataBaseNotAvailable;

    T find(K baseCode, K targetCode) throws ObjectNotFound, DataBaseNotAvailable;

    void save(K name, K code, K sign) throws DataBaseNotAvailable;

}
