package example.currencyexchange.service;

import java.util.List;

public interface ServiceIntefrace<T, K> {

    List<T> getAll();
    T getByCode(K code);
    void addToBase(T entity);

}
