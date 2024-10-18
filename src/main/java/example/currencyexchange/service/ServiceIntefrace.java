package example.currencyexchange.service;

import example.currencyexchange.exceptions.IncorrectParamsException;
import example.currencyexchange.exceptions.ObjectNotFoundException;
import example.currencyexchange.exceptions.ObjectAlreadyExistException;
import example.currencyexchange.exceptions.DataBaseNotAvailableException;

import java.util.List;

public interface ServiceIntefrace<T, K> {

    List<T> findAll();
    T findByCode(K code);
    void save(T entity);

}
