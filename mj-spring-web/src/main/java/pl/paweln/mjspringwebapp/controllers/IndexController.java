package pl.paweln.mjspringwebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;

@Controller
public class IndexController {

    @GetMapping({"", "/", "index", "index.html"})
    public String index(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "index";
    }
}
