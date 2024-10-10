package example.currencyexchange.dao;

import example.currencyexchange.model.CurrencyExchange;
import example.currencyexchange.model.exceptions.code_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.code_500.DataBaseNotAvailable;
import lombok.Getter;

import java.util.List;

public class CurrencyExchangeDAO implements DAOInterface<CurrencyExchange, String> {

    @Getter
    private static final CurrencyExchangeDAO DAO = new CurrencyExchangeDAO();

    private CurrencyExchangeDAO() {
    }

    @Override
    public List<CurrencyExchange> getModelAll() {
        return List.of();
    }

    @Override
    public CurrencyExchange getModel(String code) throws ObjectNotFound, DataBaseNotAvailable {
        return null;
    }

    @Override
    public CurrencyExchange getModel(String baseCode, String targetCode) throws ObjectNotFound, DataBaseNotAvailable {
    return null;
    }

    @Override
    public void addModel(String name, String code, String sign) throws DataBaseNotAvailable {

    }
}
