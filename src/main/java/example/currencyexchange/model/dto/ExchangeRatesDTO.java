package example.currencyexchange.model.dto;

import example.currencyexchange.model.Currencies;

public class ExchangeRatesDTO {

    private final int ID;
    private final Currencies BASE_CURRENCY;
    private final Currencies TARGET_CURRENCY;
    private final double RATE;

    public ExchangeRatesDTO(int ID, Currencies BASE_CURRENCY, Currencies TARGET_CURRENCY, double RATE) {
        this.ID = ID;
        this.BASE_CURRENCY = BASE_CURRENCY;
        this.TARGET_CURRENCY = TARGET_CURRENCY;
        this.RATE = RATE;
    }

    public int getID() {
        return ID;
    }

    public Currencies getBASE_CURRENCY() {
        return BASE_CURRENCY;
    }

    public Currencies getTARGET_CURRENCY() {
        return TARGET_CURRENCY;
    }

    public double getRATE() {
        return RATE;
    }
}
