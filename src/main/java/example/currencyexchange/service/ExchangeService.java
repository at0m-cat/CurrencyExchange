package example.currencyexchange.service;

import example.currencyexchange.dao.ExchangeDAO;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.dto.ExchangeDTO;
import example.currencyexchange.model.Exchange;
import example.currencyexchange.model.exceptions.status_400.IncorrectParams;
import example.currencyexchange.model.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.status_409.ObjectAlreadyExist;
import example.currencyexchange.model.exceptions.status_500.DataBaseNotAvailable;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

public class ExchangeService implements ServiceIntefrace<ExchangeDTO, String> {

    @Getter
    private static final ExchangeService EXCHANGE_SERVICE = new ExchangeService();
    private static final ExchangeDAO DAO = ExchangeDAO.getDAO();

    private ExchangeService() {
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

        baseCurrency.setCode(exchange.getBASE_CURRENCY().getCODE());
        baseCurrency.setId(exchange.getBASE_CURRENCY().getID());
        baseCurrency.setName(exchange.getBASE_CURRENCY().getFULL_NAME());
        baseCurrency.setSign(exchange.getBASE_CURRENCY().getSIGN());

        targetCurrency.setCode(exchange.getTARGET_CURRENCY().getCODE());
        targetCurrency.setId(exchange.getTARGET_CURRENCY().getID());
        targetCurrency.setName(exchange.getTARGET_CURRENCY().getFULL_NAME());
        targetCurrency.setSign(exchange.getTARGET_CURRENCY().getSIGN());

        exchangeDTO.setBaseCurrency(baseCurrency);
        exchangeDTO.setTargetCurrency(targetCurrency);
        exchangeDTO.setRATE(exchange.getRATE());
        exchangeDTO.setId(exchange.getID());

        return exchangeDTO;
    }

    @Override
    public List<ExchangeDTO> getAll()
            throws ObjectNotFound, DataBaseNotAvailable {
        List<Exchange> models = DAO.getModelAll();
        return models.stream().map(this::transform).toList();
    }


    @Override
    public ExchangeDTO getByCode(String code)
            throws ObjectNotFound, DataBaseNotAvailable {
        String baseCode = code.substring(0, 3);
        String targetCode = code.substring(3);

        Exchange model = DAO.getModel(baseCode, targetCode);
        return transform(model);
    }

    @Override
    public void addToBase(ExchangeDTO entity)
            throws ObjectAlreadyExist, DataBaseNotAvailable, IncorrectParams {
        BigDecimal rate = entity.getRATE();
        DAO.addToBase(entity, rate);
    }

    public void updateRate(String baseCode, String targetCode, Double rate)
            throws DataBaseNotAvailable, IncorrectParams, ObjectNotFound {
        DAO.updateRate(baseCode, targetCode, rate);
    }

}
