package pl.paweln.mjspringwebapp.controllers.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(HttpServletResponse response, Exception ex) {

        if (log.isErrorEnabled()) {
            log.error("Error occurred. Text message=" + ex.getMessage());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(DEFAULT_ERROR_VIEW);
        modelAndView.getModelMap().addAttribute("recipe", new RecipeCommand());
        modelAndView.getModelMap().addAttribute("exception", ex);


        return modelAndView;
    }
}
