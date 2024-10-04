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

    /**
     * Checks user input string for type "Double",
     * the method may throw an exception "NumberFormatException"
     *
     * @param userRate String
     * @return "true" if type is double, "false" if other type
     */
    public static boolean isDoubleRate(String userRate) {
        try {
            Double.parseDouble(userRate);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks user input string for type "Integer",
     * the method may throw an exception "NumberFormatException"
     *
     * @param userSign String
     * @return "false" if type is Integer, "true" if other type
     */
    public static boolean isTextSign(String userSign) {
        try {
            Integer.parseInt(userSign);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    /**
     * Checks parameters for null value
     *
     * @param postParams String[]
     * @return "true" if the type is not null, "false" if it is null
     */
    public static boolean isNotNullParams(String[] postParams) {
        for (String postParam : postParams) {
            if (postParam == null || postParam.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCorrectCodeExchange(String splitCode) {
        return splitCode.length() == 6;
    }

    private static boolean isCorrectCodeCurrency(String code) {
        return code.length() == 3;
    }



}
