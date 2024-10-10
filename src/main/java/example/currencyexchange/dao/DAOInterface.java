package example.currencyexchange.dao;

import example.currencyexchange.model.exceptions.code_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.code_500.DataBaseNotAvailable;
import example.currencyexchange.model.exceptions.code_400.CurrencyPairNotExist;

import java.util.List;


interface DAOInterface<T, K> {

    List<T> getModelAll();

    T getModel(K code) throws ObjectNotFound, DataBaseNotAvailable;

    T getModel(K baseCode, K targetCode) throws ObjectNotFound, DataBaseNotAvailable;

    void addModel(K name, K code, K sign) throws DataBaseNotAvailable;

}
