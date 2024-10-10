package example.currencyexchange.service;

import example.currencyexchange.dao.CurrencyDAO;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.model.Currency;
import example.currencyexchange.model.exceptions.code_400.IncorrectParams;
import example.currencyexchange.model.exceptions.code_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.code_409.ObjectAlreadyExist;
import example.currencyexchange.model.exceptions.code_500.DataBaseNotAvailable;
import lombok.Getter;

import java.util.List;

public class CurrencyService implements ServiceIntefrace<CurrencyDTO, String> {

    @Getter
    private static final CurrencyService CURRENCY_SERVICE = new CurrencyService();
    private static final CurrencyDAO DAO = CurrencyDAO.getDAO();

    private CurrencyService() {
    }

    public CurrencyDTO createDto(String name, String code, String sign) {
        CurrencyDTO dto = new CurrencyDTO();
        dto.setName(name);
        dto.setCode(code);
        dto.setSign(sign);
        return dto;
    }

    private CurrencyDTO transform(Currency currency) {
        CurrencyDTO dto = new CurrencyDTO();
        dto.setId(currency.getID());
        dto.setName(currency.getFULL_NAME());
        dto.setSign(currency.getSIGN());
        dto.setCode(currency.getCODE());
        return dto;
    }

    @Override
    public List<CurrencyDTO> getAll()
            throws ObjectNotFound, DataBaseNotAvailable {
        return DAO.getModelAll()
                .stream()
                .map(this::transform).toList();
    }

    @Override
    public CurrencyDTO getByCode(String code)
            throws ObjectNotFound, DataBaseNotAvailable {
        Currency model = DAO.getModel(code);
        return transform(model);
    }

    @Override
    public void addToBase(CurrencyDTO entity)
            throws ObjectAlreadyExist, DataBaseNotAvailable, IncorrectParams {
        DAO.addModel(entity.getName(), entity.getCode(), String.valueOf(entity.getSign()));
    }
}
