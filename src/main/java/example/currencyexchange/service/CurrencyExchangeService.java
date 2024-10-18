package example.currencyexchange.service;

import example.currencyexchange.dao.CurrencyExchangeDAO;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.dto.CurrencyExchangeDTO;
import example.currencyexchange.model.CurrencyExchange;
import example.currencyexchange.exceptions.status_400.IncorrectParams;
import example.currencyexchange.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.exceptions.status_409.ObjectAlreadyExist;
import example.currencyexchange.exceptions.status_500.DataBaseNotAvailable;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

public class CurrencyExchangeService implements ServiceIntefrace<CurrencyExchangeDTO, String> {
    @Getter
    private static final CurrencyExchangeService instance = new CurrencyExchangeService();
    private static final CurrencyService currencyService = CurrencyService.getInstance();
    private static final CurrencyExchangeDAO dao = CurrencyExchangeDAO.getDAO();

    private CurrencyExchangeService() {

    }

    public CurrencyExchangeDTO createDto(CurrencyDTO baseCurrency, CurrencyDTO targetCurrency,
                                         BigDecimal rate, BigDecimal amount) {
        CurrencyExchangeDTO dto = new CurrencyExchangeDTO();

        String baseCode = baseCurrency.getCode();
        String targetCode = targetCurrency.getCode();

        CurrencyDTO bc = currencyService.findByCode(baseCode);
        CurrencyDTO tc = currencyService.findByCode(targetCode);
        BigDecimal convertedAmount = amount.divide(rate);
//        Double convertedAmount = amount / rate;

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

        baseCurrency.setName(exchange.getBaseCurrency().getFullName());
        baseCurrency.setCode(exchange.getBaseCurrency().getCode());
        baseCurrency.setSign(exchange.getBaseCurrency().getSign());
        baseCurrency.setId(exchange.getBaseCurrency().getId());

        targetCurrency.setName(exchange.getTargetCurrency().getFullName());
        targetCurrency.setCode(exchange.getTargetCurrency().getCode());
        targetCurrency.setSign(exchange.getTargetCurrency().getSign());
        targetCurrency.setId(exchange.getTargetCurrency().getId());
        dto.setBaseCurrency(baseCurrency);
        dto.setTargetCurrency(targetCurrency);
        dto.setRate(exchange.getRate());

        return dto;
    }

    public CurrencyExchangeDTO setExchangeParameters(CurrencyExchangeDTO currencyExchangeDTO, BigDecimal amount) {
        CurrencyExchangeDTO dto = new CurrencyExchangeDTO();

        BigDecimal rate = currencyExchangeDTO.getRate();
        BigDecimal convertedAmount = amount.multiply(rate);

        dto.setBaseCurrency(currencyExchangeDTO.getBaseCurrency());
        dto.setTargetCurrency(currencyExchangeDTO.getTargetCurrency());
        dto.setRate(rate);
        dto.setConvertedAmount(convertedAmount);
        dto.setAmount(amount);
        return dto;
    }

    @Override
    public List<CurrencyExchangeDTO> findAll()
            throws ObjectNotFound, DataBaseNotAvailable {
        return List.of();
    }

    @Override
    public CurrencyExchangeDTO findByCode(String code)
            throws ObjectNotFound, DataBaseNotAvailable {
        String baseCode = code.substring(0, 3);
        String targetCode = code.substring(3);

        CurrencyExchange model = dao.find(baseCode, targetCode);
        return transform(model);
    }

    @Override
    public void save(CurrencyExchangeDTO entity)
            throws ObjectAlreadyExist, DataBaseNotAvailable, IncorrectParams {

    }
}
