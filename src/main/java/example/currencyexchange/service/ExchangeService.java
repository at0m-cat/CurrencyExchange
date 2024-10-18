package example.currencyexchange.service;

import example.currencyexchange.dao.ExchangeDAO;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.dto.ExchangeDTO;
import example.currencyexchange.model.Exchange;
import example.currencyexchange.exceptions.IncorrectParamsException;
import example.currencyexchange.exceptions.ObjectNotFoundException;
import example.currencyexchange.exceptions.ObjectAlreadyExistException;
import example.currencyexchange.exceptions.DataBaseNotAvailableException;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

public class ExchangeService implements ServiceIntefrace<ExchangeDTO, String> {

    @Getter
    private static final ExchangeService instance = new ExchangeService();
    private final ExchangeDAO dao;

    private ExchangeService() {
        this.dao = ExchangeDAO.getInstance();
    }

    public ExchangeDTO createDto(CurrencyDTO baseCurrency, CurrencyDTO targetCurrency, BigDecimal rate) {
        ExchangeDTO dto = new ExchangeDTO();
        dto.setBaseCurrency(baseCurrency);
        dto.setTargetCurrency(targetCurrency);
        dto.setRATE(rate);
        return dto;
    }

    private ExchangeDTO transform(Exchange exchange) {
        CurrencyDTO baseCurrency = new CurrencyDTO();
        CurrencyDTO targetCurrency = new CurrencyDTO();
        ExchangeDTO exchangeDTO = new ExchangeDTO();

        baseCurrency.setCode(exchange.getBaseCurrency().getCode());
        baseCurrency.setId(exchange.getBaseCurrency().getId());
        baseCurrency.setName(exchange.getBaseCurrency().getFullName());
        baseCurrency.setSign(exchange.getBaseCurrency().getSign());

        targetCurrency.setCode(exchange.getTargetCurrency().getCode());
        targetCurrency.setId(exchange.getTargetCurrency().getId());
        targetCurrency.setName(exchange.getTargetCurrency().getFullName());
        targetCurrency.setSign(exchange.getTargetCurrency().getSign());

        exchangeDTO.setBaseCurrency(baseCurrency);
        exchangeDTO.setTargetCurrency(targetCurrency);
        exchangeDTO.setRATE(exchange.getRate());
        exchangeDTO.setId(exchange.getId());

        return exchangeDTO;
    }

    @Override
    public List<ExchangeDTO> findAll() {
        List<Exchange> models = dao.findAll();
        return models.stream().map(this::transform).toList();
    }


    @Override
    public ExchangeDTO findByCode(String code) {
        String baseCode = code.substring(0, 3);
        String targetCode = code.substring(3);

        Exchange model = dao.find(baseCode, targetCode);
        return transform(model);
    }

    @Override
    public void save(ExchangeDTO entity) {
        BigDecimal rate = entity.getRATE();
        dao.addToBase(entity, rate);
    }

    public void updateRate(String baseCode, String targetCode, Double rate) {
        dao.updateRate(baseCode, targetCode, rate);
    }

}
