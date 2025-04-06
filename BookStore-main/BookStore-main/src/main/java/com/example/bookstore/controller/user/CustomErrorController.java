package com.example.bookstore.controller.user;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "user/security/404page"; // Đảm bảo file này tồn tại
    }

    public String getErrorPath() {
        return "/error";
    }
}
