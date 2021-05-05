package pl.paweln.mjspringwebapp.services.exercises;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"dog", "default", "dev"})
@Service("animalService")
public class DogPetService implements PetService {
    public String getPetType(){
        return "Dogs are the best!";
    }
}
