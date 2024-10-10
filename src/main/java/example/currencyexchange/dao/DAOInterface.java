package example.currencyexchange.dao;

import example.currencyexchange.model.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.status_500.DataBaseNotAvailable;

import java.sql.ResultSet;
import java.util.List;


interface DAOInterface<T, K> {

    T building(ResultSet rs) throws DataBaseNotAvailable;

    List<T> getModelAll();

    T getModel(K code) throws ObjectNotFound, DataBaseNotAvailable;

    T getModel(K baseCode, K targetCode) throws ObjectNotFound, DataBaseNotAvailable;

    void addModel(K name, K code, K sign) throws DataBaseNotAvailable;

}
