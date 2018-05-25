package net.hundredtickets.yahtzee;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        return String.format("<html><body><h2>Too many players registered</h2><div></div>"
                        + "<div></html>",
                statusCode, exception==null? "N/A": exception.getMessage());
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
