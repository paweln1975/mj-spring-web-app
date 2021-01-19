package pl.paweln.mjspringwebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.paweln.mjspringwebapp.services.AuthorService;

@Controller
@RequestMapping("/authors2")
public class AuthorController2 {
    private final AuthorService authorService;

    public AuthorController2(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String listAuthors(Model model) {
        model.addAttribute("authors", this.authorService.findAll());
        return "authors/list";
    }
}
