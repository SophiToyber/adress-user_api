package servlet;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.User;
import lombok.extern.slf4j.Slf4j;
import service.UserService;

@Slf4j
@WebServlet("/user/*")
public class MainServlet extends HttpServlet {
    private static final int _1 = 1;

    private static final String UTF_8 = "UTF-8";

    private static final String APPLICATION_JSON = "application/json";

    private static final long serialVersionUID = 1L;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService service = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(APPLICATION_JSON);
        response.setCharacterEncoding(UTF_8);

        response.getWriter()
            .append(objectMapper
                .writeValueAsString(service.doGet(Integer.parseInt(request.getPathInfo().substring(_1)))));

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(APPLICATION_JSON);
        response.setCharacterEncoding(UTF_8);

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        service.doPost(objectMapper.readValue(json, User.class));

    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(APPLICATION_JSON);
        response.setCharacterEncoding(UTF_8);
        
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            Integer id = Integer.parseInt(request.getPathInfo().substring(_1));
            service.doPut(id, objectMapper.readValue(json, User.class));
        } catch (JsonParseException | JsonMappingException e) {
            log.error(String.format("ERROR PUT data:%s", e.getMessage()));
        }
        
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(APPLICATION_JSON);
        response.setCharacterEncoding(UTF_8);

        try {
            service.doDelete(Integer.parseInt(request.getPathInfo().substring(_1)));
            log.info("Data was deleted");
        } catch (Exception e) {
            log.error(String.format("Failed to delete data:%1$s", e.getMessage()));
        }
        
    }

}