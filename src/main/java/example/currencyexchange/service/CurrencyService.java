package example.currencyexchange.service;

import example.currencyexchange.dao.CurrencyDAO;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.model.Currency;
import lombok.Getter;

import java.util.List;

public class CurrencyService implements ServiceIntefrace<CurrencyDTO, String> {

    @Getter
    private static final CurrencyService SERVICE = new CurrencyService();
    private static final CurrencyDAO DAO = CurrencyDAO.getINSTANCE();

    private CurrencyService() {
    }

    public CurrencyDTO createDto(String name, String code, int sign) {
        CurrencyDTO dto = new CurrencyDTO();
        dto.setName(name);
        dto.setCode(code);
        dto.setSign(sign);
        return dto;
    }

    private CurrencyDTO transform(Currency currency) {
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setId(currency.getID());
        currencyDTO.setName(currency.getFULL_NAME());
        currencyDTO.setSign(currency.getSIGN());
        currencyDTO.setCode(currency.getCODE());
        return currencyDTO;
    }

    @Override
    public List<CurrencyDTO> getAll() {
        return DAO.getModelAll()
                .stream()
                .map(this::transform).toList();
    }

    @Override
    public CurrencyDTO getByCode(String code) {
        Currency model = DAO.getModel(code);
        CurrencyDTO dto = transform(model);
        return dto;
    }

    @Override
    public void addToBase(CurrencyDTO entity) {
        DAO.addModel(entity.getName(), entity.getCode(), String.valueOf(entity.getSign()));
    }
}
