//package example.currencyexchange.model;
//import example.currencyexchange.config.Renderer;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.util.Optional;
//
//public class Error extends RuntimeException {
//
//    private final String MESSAGE;
//
//    public Error(HttpServletResponse response, String message, int status) {
//        super(message);
//        MESSAGE = message;
//        response.setStatus(status);
//        printMessage(response, message, status);
//    }
//
//
//    private void printMessage(HttpServletResponse response, String message, int status) {
//        Renderer renderer = new Renderer();
//        Error error = new Error(response, message, status);
//        renderer.print(response, error);
//    }
//
//}
