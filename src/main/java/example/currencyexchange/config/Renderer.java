package example.currencyexchange.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

import java.io.IOException;
import java.io.PrintWriter;

public class Renderer {

    @Getter
    private static final Renderer RENDERER = new Renderer();

    private Renderer() {
    }

    /**
     * Generic method for outputting any object in JSON format
     *
     * @param response HttpServletResponse to send the response
     * @param data     Object to output in JSON
     * @param <T>      Object type
     */
    public <T> void print(HttpServletResponse response, T data) {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            PrintWriter writer = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    /**
//     * Print "message" errors in JSON format,
//     * inside the method is set:
//     * encoding UTF 8,
//     * the error is determined by the numerical value
//     *
//     * @param response HttpServletResponse
//     * @param errorType int error type, ex. 500
//     */
//    @SneakyThrows
//    public void printMessage(HttpServletResponse response, int errorType) {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        response.setStatus(errorType);
////        String message = getMessage(errorType);
//
//        class Error{
//            String message;
//            public Error(String message) {
//                this.message = message;
//            }
//        }
//
//        Error errorObj = new Error(message);
//        print(response, errorObj);
//    }
//
//
//    public void printMessage(HttpServletResponse response, String message, int errorType) {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.setStatus(errorType);
//        Error errorObj = new Error(message);
//        print(response, errorObj);
//    }
//
//    private String getMessage(int errorType) {
//        String message;
//        switch (errorType) {
//            case 500 -> message = "DataBase unavailable";
//            case 400 -> message = "A required form field is missing";
//            case 404 -> message = "Not found";
//            case 409 -> message = "A currency with this code already exists";
//            case 201 -> message = "Success";
//            default -> message = String.valueOf(errorType);
//
//        }
//        return message;
//    }

}
