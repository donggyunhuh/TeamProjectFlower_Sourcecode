package org.flower.controllers.commons;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("org.flower.controllers")
public class CommonsController {

    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, Model model){
        e.printStackTrace();
        model.addAttribute("message", e.getMessage());

        return "admin/";
    }
}
