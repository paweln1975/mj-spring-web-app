package pl.paweln.mjspringwebapp.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.paweln.mjspringwebapp.domain.Category;
import pl.paweln.mjspringwebapp.domain.UnitOfMeasure;
import pl.paweln.mjspringwebapp.repositories.CategoryRepository;
import pl.paweln.mjspringwebapp.repositories.UnitOfMeasureRepository;

@Profile({"dev", "prod"})
@Slf4j
@Component
public class BootStrapMySQL implements ApplicationListener<ContextRefreshedEvent> {
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public BootStrapMySQL(CategoryRepository categoryRepository,
                          UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (categoryRepository.count() == 0L){
            log.debug("Loading Categories");
            loadCategories();
        }

        if (unitOfMeasureRepository.count() == 0L){
            log.debug("Loading UOMs");
            loadUom();
        }
    }

    private void loadCategories(){
        Category cat1 = new Category();
        cat1.setCategoryName("American");
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setCategoryName("Italian");
        categoryRepository.save(cat2);

        Category cat3 = new Category();
        cat3.setCategoryName("Mexican");
        categoryRepository.save(cat3);

        Category cat4 = new Category();
        cat4.setCategoryName("Fast Food");
        categoryRepository.save(cat4);
    }
    private void loadUom() {
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setUnitName("Teaspoon");
        unitOfMeasureRepository.save(uom1);
    }
}
