package com.maisyst.fitness;

import com.maisyst.fitness.dao.repositories.ISubscriptionRepository;
import com.maisyst.fitness.dao.repositories.IUserRepository;
import com.maisyst.fitness.utils.AuthRole;
import com.maisyst.fitness.models.SubscriptionModel;
import com.maisyst.fitness.models.UserModel;
import com.maisyst.fitness.utils.MaiUID;
import com.maisyst.fitness.utils.TypeSubscription;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import static com.maisyst.fitness.utils.MaiUtils.getPriceSubscription;


@SpringBootApplication
public class FitnessApplication {

    public static void main(String[] args) {
        var configurableApplicationContext = SpringApplication.run(FitnessApplication.class, args);
        IUserRepository repository = configurableApplicationContext.getBean(IUserRepository.class);
        ISubscriptionRepository subscriptionRepository = configurableApplicationContext.getBean(ISubscriptionRepository.class);
        var subscription = subscriptionRepository.findAll();
        if (subscription.isEmpty()) {
            List<SubscriptionModel> models = List.of(
                    new SubscriptionModel(TypeSubscription.GOLD.getValue(), getPriceSubscription(TypeSubscription.GOLD), TypeSubscription.GOLD),
                    new SubscriptionModel(TypeSubscription.PRIME.getValue(), getPriceSubscription(TypeSubscription.PRIME), TypeSubscription.PRIME),
                    new SubscriptionModel(TypeSubscription.STANDARD.getValue(), getPriceSubscription(TypeSubscription.STANDARD), TypeSubscription.STANDARD)
            );
            subscriptionRepository.saveAll(models);
        }
        var result = repository.findByUsername("emf@Admin90");
        if (result.isEmpty()) {
            repository.save(new UserModel(
                    "emf@Admin90",
                    "admin",
                    "admin",
                    null,
                    null,
                    null,
                    null,
                    "$2a$10$ShZnV8OdBzUpzSZGqqw3dOsbGM4VLS8qPwvf725S1nVoAXnf62ijq",
                    true,
                    AuthRole.ADMIN));
        }
    }

}
