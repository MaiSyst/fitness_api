package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.interfaces.ICoachServices;
import com.maisyst.fitness.dao.interfaces.ISubscriptionServices;
import com.maisyst.fitness.dao.repositories.CoachRepository;
import com.maisyst.fitness.dao.repositories.SubscriptionRepository;
import com.maisyst.fitness.models.CoachModel;
import com.maisyst.fitness.models.SubscriptionModel;
import com.maisyst.fitness.utils.MaiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServices implements ISubscriptionServices {
    private final SubscriptionRepository subscriptionRepository;
    public SubscriptionServices(SubscriptionRepository subscriptionRepository){
        this.subscriptionRepository=subscriptionRepository;
    }

    @Override
    public MaiResponse<SubscriptionModel> insert(SubscriptionModel model) {
        try {
            return new MaiResponse.MaiSuccess<>(subscriptionRepository.save(model), HttpStatus.OK);

        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteById(String id) {
       try {
            subscriptionRepository.deleteById(id);
            return new MaiResponse.MaiSuccess<>("Subscriptions has been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<SubscriptionModel> findById(String id) {
       try {
            var response = subscriptionRepository.findById(id);
            if (response.isPresent()) {
                return new MaiResponse.MaiSuccess<>(response.get(), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Subscription don't exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<List<SubscriptionModel>> fetchAll() {
         try {
            return new MaiResponse.MaiSuccess<>(subscriptionRepository.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> insertMany(List<SubscriptionModel> models) {
          try {
            subscriptionRepository.saveAll(models);
            return new MaiResponse.MaiSuccess<>("Subscriptions has been added", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteMany(List<String> ids) {
       try {
            subscriptionRepository.deleteAllById(ids);
            return new MaiResponse.MaiSuccess<>("Subscriptions has been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<SubscriptionModel> update(String id, SubscriptionModel model) {
         try {
            var result = subscriptionRepository.findById(id);
            if (result.isPresent()) {
                result.get().setType(model.getType());
                var response = subscriptionRepository.save(result.get());
                return new MaiResponse.MaiSuccess<>(response, HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Subscription id don't exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
