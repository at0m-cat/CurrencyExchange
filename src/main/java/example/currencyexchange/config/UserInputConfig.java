package example.currencyexchange.config;

public class UserInputConfig {

    /**
     * Gives the correct code values for exchange. Has internal check call
     *
     * @param text user input
     * @return array of strings (base code, target code) if internal check was successful, otherwise null
     */
    public static String[] getCodeExchange(String text) {
        String[] userText = text.split("/");
        String codes = userText[1].toUpperCase();
        if (!isCorrectCodeExchange(codes)) {
            throw new IllegalArgumentException();
        }
        return codes.split("(?<=\\G...)");
    }

    /**
     * Gives the correct code value for currency. Has internal check call
     *
     * @param text user input
     * @return string code if internal check succeeded, null otherwise
     */
    public static String getCodeCurrency(String text) {
        String[] userText = text.split("/");
        String code = userText[1].toUpperCase();
        if (!isCorrectCodeCurrency(code)) {
            return null;
        }
        return code;
    }

    private static boolean isCorrectCodeExchange(String splitCode) {
        return splitCode.length() == 6;
    }

    private static boolean isCorrectCodeCurrency(String code) {
        return code.length() == 3;
    }

    public static boolean isCorrectPostParams(String[] postParams) {
        for (String postParam : postParams) {
            if (postParam == null || postParam.isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
