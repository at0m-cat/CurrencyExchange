package example.currencyexchange.service;

import example.currencyexchange.dao.CurrencyExchangeDAO;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.dto.CurrencyExchangeDTO;
import example.currencyexchange.dto.ExchangeDTO;
import example.currencyexchange.model.Currency;
import example.currencyexchange.model.CurrencyExchange;
import example.currencyexchange.model.Exchange;
import lombok.Getter;

import java.util.List;

public class CurrencyExchangeService implements ServiceIntefrace<CurrencyExchangeDTO, String> {
    @Getter
    private static final CurrencyExchangeService SERVICE = new CurrencyExchangeService();
    private static final CurrencyService CURRENCY_SERVICE = CurrencyService.getSERVICE();
    private static final ExchangeService EXCHANGE_SERVICE = ExchangeService.getSERVICE();
    private static final CurrencyExchangeDAO DAO = CurrencyExchangeDAO.getDAO();

    public CurrencyExchangeDTO createDto(CurrencyDTO baseCurrency, CurrencyDTO targetCurrency, Double rate, Double amount) {
        CurrencyExchangeDTO dto = new CurrencyExchangeDTO();

        String baseCode = baseCurrency.getCode();
        String targetCode = targetCurrency.getCode();

        CurrencyDTO bc = CURRENCY_SERVICE.getByCode(baseCode);
        CurrencyDTO tc = CURRENCY_SERVICE.getByCode(targetCode);

        dto.setBaseCurrency(bc);
        dto.setTargetCurrency(tc);
        dto.setRate(rate);
        dto.setAmount(amount);
        dto.setConvertedAmount(amount / rate);
        return dto;
    }

    public double getRate(CurrencyDTO baseCurrency, CurrencyDTO targetCurrency) {
        return EXCHANGE_SERVICE.getByCode(baseCurrency.getCode() + targetCurrency.getCode()).getRATE();
    }

    private CurrencyExchangeDTO transform(CurrencyExchange exchange) {
        CurrencyExchangeDTO dto = new CurrencyExchangeDTO();
        CurrencyDTO baseCurrency = new CurrencyDTO();
        CurrencyDTO targetCurrency = new CurrencyDTO();

        baseCurrency.setName(exchange.getBaseCurrency().getFULL_NAME());
        baseCurrency.setCode(exchange.getBaseCurrency().getCODE());
        baseCurrency.setSign(exchange.getBaseCurrency().getSIGN());
        baseCurrency.setId(exchange.getBaseCurrency().getID());

        targetCurrency.setName(exchange.getTargetCurrency().getFULL_NAME());
        targetCurrency.setCode(exchange.getTargetCurrency().getCODE());
        targetCurrency.setSign(exchange.getTargetCurrency().getSIGN());
        dto.setBaseCurrency(baseCurrency);
        dto.setTargetCurrency(targetCurrency);
        dto.setRate(exchange.getRate());

        return dto;
    }

    public CurrencyExchangeDTO setExchangeParameters(CurrencyExchangeDTO currencyExchangeDTO, Double amount) {
        CurrencyExchangeDTO pairs;

        Double rate = currencyExchangeDTO.getRate();
        Double convertedAmount = amount / rate;

        pairs = currencyExchangeDTO;
        pairs.setAmount(amount);
        pairs.setConvertedAmount(convertedAmount);
        return pairs;
    }

    @Override
    public List<CurrencyExchangeDTO> getAll() {
        return List.of();
    }

    @Override
    public CurrencyExchangeDTO getByCode(String code) {
        String baseCode = code.substring(0, 3);
        String targetCode = code.substring(3);
        CurrencyExchange model = DAO.getModel(baseCode, targetCode);
        return transform(model);
    }

    @Override
    public void addToBase(CurrencyExchangeDTO entity) {

    }
}
