package example.currencyexchange.service;

import example.currencyexchange.model.exceptions.status_400.IncorrectParams;
import example.currencyexchange.model.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.status_409.ObjectAlreadyExist;
import example.currencyexchange.model.exceptions.status_500.DataBaseNotAvailable;

import java.util.List;

public interface ServiceIntefrace<T, K> {

    List<T> getAll() throws ObjectNotFound, DataBaseNotAvailable;
    T getByCode(K code) throws ObjectNotFound, DataBaseNotAvailable;
    void addToBase(T entity) throws ObjectAlreadyExist, DataBaseNotAvailable, IncorrectParams;

}
