package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.interfaces.IActivityServices;
import com.maisyst.fitness.dao.repositories.IActivityRepository;
import com.maisyst.fitness.models.ActivityModel;
import com.maisyst.fitness.utils.MaiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServices implements IActivityServices {
    private final IActivityRepository activityRepository;

    public ActivityServices(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
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
    public MaiResponse<String> deleteById(Integer id) {
        try {
            activityRepository.deleteById(id);
            return new MaiResponse.MaiSuccess<>("Activity deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<ActivityModel> update(Integer id, ActivityModel model) {
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
    public MaiResponse<ActivityModel> findById(Integer id) {
        try {
            var response = activityRepository.findById(id);
            if (response.isPresent()) {
                return new MaiResponse.MaiSuccess<>(response.get(), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Activity don't exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<List<ActivityModel>> fetchAll() {
        try {
            return new MaiResponse.MaiSuccess<>(activityRepository.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> insertMany(List<ActivityModel> models) {
        try {
            activityRepository.saveAll(models);
            return new MaiResponse.MaiSuccess<>("Activities has been added", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteMany(List<Integer> ids) {
        try {
            activityRepository.deleteAllById(ids);
            return new MaiResponse.MaiSuccess<>("Activities has been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
