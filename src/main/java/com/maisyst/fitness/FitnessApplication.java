package com.maisyst.fitness;

import com.maisyst.fitness.dao.repositories.IUserRepository;
import com.maisyst.fitness.dao.services.UserService;
import com.maisyst.fitness.models.AuthRole;
import com.maisyst.fitness.models.UserModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FitnessApplication {

    public static void main(String[] args) {
        var configurableApplicationContext = SpringApplication.run(FitnessApplication.class, args);
        IUserRepository repository = configurableApplicationContext.getBean(IUserRepository.class);
        var result = repository.findByUsername("infinity@Admin90");
        if (result.isEmpty()) {
            repository.save(new UserModel("infinity@Admin90", "$2a$10$ShZnV8OdBzUpzSZGqqw3dOsbGM4VLS8qPwvf725S1nVoAXnf62ijq", AuthRole.ADMIN, true));
        }
    }

}
