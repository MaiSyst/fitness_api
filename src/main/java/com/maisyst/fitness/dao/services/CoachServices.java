package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.interfaces.ICoachServices;
import com.maisyst.fitness.dao.repositories.ICoachRepository;
import com.maisyst.fitness.utils.MaiResponse;
import com.maisyst.fitness.models.CoachModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachServices implements ICoachServices {
    private final ICoachRepository coachRepository;

    public CoachServices(ICoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    @Override
    public MaiResponse<CoachModel> update(Integer id, CoachModel model) {
        try {
            var responseOptional = coachRepository.findById(id);
            if (responseOptional.isPresent()) {
                var response = responseOptional.get();
                response.setActivityCoach(model.getActivityCoach());
                response.setPhone(model.getPhone());
                response.setAddress(model.getAddress());
                response.setFirstName(model.getFirstName());
                response.setLastName(model.getLastName());
                response.setSpeciality(model.getSpeciality());
                var dataResponse = coachRepository.save(response);
                return new MaiResponse.MaiSuccess<>(dataResponse, HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Coach don't exist", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<CoachModel> insert(CoachModel model) {
       try {
            return new MaiResponse.MaiSuccess<>(coachRepository.save(model), HttpStatus.OK);

        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteById(Integer id) {
       try {
            coachRepository.deleteById(id);
            return new MaiResponse.MaiSuccess<>("Coach deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<CoachModel> findById(Integer id) {
        try {
            var response = coachRepository.findById(id);
            if (response.isPresent()) {
                return new MaiResponse.MaiSuccess<>(response.get(), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("Coach don't exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<List<CoachModel>> fetchAll() {
       try {
           var response=coachRepository.findAll();
            return new MaiResponse.MaiSuccess<>(response, HttpStatus.OK);
        } catch (Exception ex) {
           return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> insertMany(List<CoachModel> models) {
        try {
            coachRepository.saveAll(models);
            return new MaiResponse.MaiSuccess<>("Activities was added", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> deleteMany(List<Integer> ids) {
        try {
            coachRepository.deleteAllById(ids);
            return new MaiResponse.MaiSuccess<>("Activities was deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    public MaiResponse<List<CoachModel>> fetchWithAll() {
       try {
           var response=coachRepository.findAll();
            return new MaiResponse.MaiSuccess<>(response, HttpStatus.OK);
        } catch (Exception ex) {
           return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
