package example.currencyexchange.service;

import example.currencyexchange.dao.CurrencyDAO;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.model.Currency;
import example.currencyexchange.exceptions.status_400.IncorrectParams;
import example.currencyexchange.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.exceptions.status_409.ObjectAlreadyExist;
import example.currencyexchange.exceptions.status_500.DataBaseNotAvailable;
import lombok.Getter;

import java.util.List;

public class CurrencyService implements ServiceIntefrace<CurrencyDTO, String> {

    @Getter
    private static final CurrencyService instance = new CurrencyService();
    private static final CurrencyDAO dao = CurrencyDAO.getInstance();

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
        dto.setId(currency.getId());
        dto.setName(currency.getFullName());
        dto.setSign(currency.getSign());
        dto.setCode(currency.getCode());
        return dto;
    }

    @Override
    public List<CurrencyDTO> findAll()
            throws ObjectNotFound, DataBaseNotAvailable {
        return dao.findAll()
                .stream()
                .map(this::transform).toList();
    }

    @Override
    public CurrencyDTO findByCode(String code)
            throws ObjectNotFound, DataBaseNotAvailable {
        Currency model = dao.find(code);
        return transform(model);
    }

    @Override
    public void save(CurrencyDTO entity)
            throws ObjectAlreadyExist, DataBaseNotAvailable, IncorrectParams {
        dao.save(entity.getName(), entity.getCode(), String.valueOf(entity.getSign()));
    }
}
