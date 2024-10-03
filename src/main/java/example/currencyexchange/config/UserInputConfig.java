package example.currencyexchange.config;

public class UserInputConfig {

    public static String[] getCodeExchange(String text) {

        String[] userText = text.split("/");
        String codes = userText[1].toUpperCase();

        if (!isCorrectCodeExchange(codes)) {
            return null;
        }

        return codes.split("(?<=\\G...)");
    }

    public static String getCodeCurrency(String text) {
        String[] userText = text.split("/");
        String codes = userText[1].toUpperCase();
        if (!isCorrectCodeCurrency(codes)) {
            return null;
        }
        return codes;
    }

    public static boolean isCorrectCodeExchange(String splitCode) {
        return splitCode.length() == 6;
    }

    public static boolean isCorrectCodeCurrency(String code){
        return code.length() == 3;
    }

}
