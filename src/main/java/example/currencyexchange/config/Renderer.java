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
}
