package br.com.jhonecmd.courses_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/")
public class RedirectController {

    @Hidden
    @GetMapping("")
    public RedirectView redirectToSwagger() {
        return new RedirectView("/swagger-ui/index.html");
    }
}
