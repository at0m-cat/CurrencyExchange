package example.currencyexchange.service;

import example.currencyexchange.dao.CurrencyExchangeDAO;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.dto.CurrencyExchangeDTO;
import example.currencyexchange.model.CurrencyExchange;
import example.currencyexchange.model.exceptions.status_400.IncorrectParams;
import example.currencyexchange.model.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.status_409.ObjectAlreadyExist;
import example.currencyexchange.model.exceptions.status_500.DataBaseNotAvailable;
import lombok.Getter;

import java.util.List;

public class CurrencyExchangeService implements ServiceIntefrace<CurrencyExchangeDTO, String> {
    @Getter
    private static final CurrencyExchangeService CURRENCY_EXCHANGE_SERVICE = new CurrencyExchangeService();
    private static final CurrencyService CURRENCY_SERVICE = CurrencyService.getCURRENCY_SERVICE();
    private static final CurrencyExchangeDAO DAO = CurrencyExchangeDAO.getDAO();

    private CurrencyExchangeService() {

    }

    public CurrencyExchangeDTO createDto(CurrencyDTO baseCurrency, CurrencyDTO targetCurrency,
                                         double rate, double amount) {
        CurrencyExchangeDTO dto = new CurrencyExchangeDTO();

        String baseCode = baseCurrency.getCode();
        String targetCode = targetCurrency.getCode();

        CurrencyDTO bc = CURRENCY_SERVICE.getByCode(baseCode);
        CurrencyDTO tc = CURRENCY_SERVICE.getByCode(targetCode);
        Double convertedAmount = amount / rate;

        dto.setBaseCurrency(bc);
        dto.setTargetCurrency(tc);
        dto.setRate(rate);
        dto.setAmount(amount);
        dto.setConvertedAmount(convertedAmount);
        return dto;
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
        targetCurrency.setId(exchange.getTargetCurrency().getID());
        dto.setBaseCurrency(baseCurrency);
        dto.setTargetCurrency(targetCurrency);
        dto.setRate(exchange.getRate());

        return dto;
    }

    public CurrencyExchangeDTO setExchangeParameters(CurrencyExchangeDTO currencyExchangeDTO, double amount) {
        CurrencyExchangeDTO dto = new CurrencyExchangeDTO();

        double rate = currencyExchangeDTO.getRate();
        double convertedAmount = amount * rate;

        dto.setBaseCurrency(currencyExchangeDTO.getBaseCurrency());
        dto.setTargetCurrency(currencyExchangeDTO.getTargetCurrency());
        dto.setRate(rate);
        dto.setConvertedAmount(convertedAmount);
        dto.setAmount(amount);
        return dto;
    }

    @Override
    public List<CurrencyExchangeDTO> getAll()
            throws ObjectNotFound, DataBaseNotAvailable {
        return List.of();
    }

    @Override
    public CurrencyExchangeDTO getByCode(String code)
            throws ObjectNotFound, DataBaseNotAvailable {
        String baseCode = code.substring(0, 3);
        String targetCode = code.substring(3);

        CurrencyExchange model = DAO.getModel(baseCode, targetCode);
        return transform(model);
    }

    @Override
    public void addToBase(CurrencyExchangeDTO entity)
            throws ObjectAlreadyExist, DataBaseNotAvailable, IncorrectParams {

    }
}
