package pl.paweln.mjspringwebapp.controllers.exercises;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import pl.paweln.mjspringwebapp.services.exercises.PetService;

@Controller
public class PetController {

    private final PetService petService;

    public PetController(@Qualifier("animalService") PetService petService) {
        this.petService = petService;
    }

    public String whichPetIsTheBest(){
        return petService.getPetType();
    }
}
