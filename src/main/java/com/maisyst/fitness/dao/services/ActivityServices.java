package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.services.interfaces.IActivityServices;
import com.maisyst.fitness.dao.repositories.IActivityRepository;
import com.maisyst.fitness.models.*;
import com.maisyst.fitness.utils.MaiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ActivityServices implements IActivityServices {
    private final IActivityRepository activityRepository;
    private final SubscriptionServices subscriptionServices;

    public ActivityServices(IActivityRepository activityRepository,
                            SubscriptionServices subscriptionServices) {
        this.activityRepository = activityRepository;
        this.subscriptionServices = subscriptionServices;
    }

    @Override
    public MaiResponse<ActivityModel> insert(ActivityModel model) {
        try {
            return new MaiResponse.MaiSuccess<>(activityRepository.save(model), HttpStatus.OK);

        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<ActivityModel> insertWithSubscription(String type, ActivityModel model) {
        try {
            var subscriptionResponse = subscriptionServices.findByType(type);
            if (subscriptionResponse.getStatus() == HttpStatus.OK) {
                model.getSubscriptions().add(subscriptionResponse.getData());
                return new MaiResponse.MaiSuccess<>(activityRepository.save(model), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>(subscriptionResponse.getMessage(), HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteById(String id) {
        try {
            activityRepository.deleteById(id);
            return new MaiResponse.MaiSuccess<>("Activity deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<ActivityModel> update(String id, ActivityModel model) {
        try {
            var result = activityRepository.findById(id);
            if (result.isPresent()) {
                result.get().setDescription(model.getDescription());
                result.get().setLabel(model.getLabel());
                var response = activityRepository.save(result.get());
                return new MaiResponse.MaiSuccess<>(response, HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Activity don't exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<ActivityModel> findById(String id) {
        try {
            var response = activityRepository.findById(id);
            if (response.isPresent()) {
                return new MaiResponse.MaiSuccess<>(response.get(), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Activity is empty.", HttpStatus.NO_CONTENT);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<List<ActivityModel>> fetchAll() {
       try {
           var response=activityRepository.findAll();
            return new MaiResponse.MaiSuccess<>(response, HttpStatus.OK);
        } catch (Exception ex) {
           return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteMany(List<String> ids) {
        try {
            activityRepository.deleteAllById(ids);
            return new MaiResponse.MaiSuccess<>("Activities has been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public MaiResponse<List<ActivityModel>> searchIt(String query) {
         try {
           var response=activityRepository.searchIt(query);
            return new MaiResponse.MaiSuccess<>(response, HttpStatus.OK);
        } catch (Exception ex) {
           return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
