package example.currencyexchange.service;

import example.currencyexchange.exceptions.status_400.IncorrectParams;
import example.currencyexchange.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.exceptions.status_409.ObjectAlreadyExist;
import example.currencyexchange.exceptions.status_500.DataBaseNotAvailable;

import java.util.List;

public interface ServiceIntefrace<T, K> {

    List<T> findAll() throws ObjectNotFound, DataBaseNotAvailable;
    T findByCode(K code) throws ObjectNotFound, DataBaseNotAvailable;
    void save(T entity) throws ObjectAlreadyExist, DataBaseNotAvailable, IncorrectParams;

}
