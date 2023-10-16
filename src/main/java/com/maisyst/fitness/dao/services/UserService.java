package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.repositories.IUserRepository;
import com.maisyst.fitness.dao.services.interfaces.IUserService;
import com.maisyst.fitness.models.AuthRequest;
import com.maisyst.fitness.models.AuthResponse;
import com.maisyst.fitness.models.UserModel;
import com.maisyst.fitness.security.jwt.MaiJWTConfig;
import com.maisyst.fitness.utils.MaiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private final IUserRepository repository;
    private final MaiJWTConfig maiJWTConfig;

    @Autowired
    public UserService(IUserRepository repository, MaiJWTConfig maiJWTConfig) {
        this.repository = repository;
        this.maiJWTConfig = maiJWTConfig;
    }

    @Override
    public MaiResponse<UserModel> findByUsername(String username) {
        try {
            var response = repository.findByUsername(username);
            if (response.isPresent()) {
                return new MaiResponse.MaiSuccess<>(response.get(), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("User doesn't exist", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<UserModel> disableOrEnableUsername(String username, boolean isActive) {
        try {
            var response = repository.findByUsername(username);
            if (response.isPresent()) {
                response.get().setIsActive(isActive);
                return new MaiResponse.MaiSuccess<>(repository.save(response.get()), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("User doesn't exist", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<UserModel> updatePassword(String username, String newPassword) {
        try {
            var response = repository.findByUsername(username);
            if (response.isPresent()) {
                response.get().setPassword(new BCryptPasswordEncoder().encode(newPassword));

                return new MaiResponse.MaiSuccess<>(repository.save(response.get()), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("User doesn't exist", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<UserModel> add(UserModel model) {
        try {
             model.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
            var response = repository.save(model);
            return new MaiResponse.MaiSuccess<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<String> delete(String username) {
        try {
            repository.deleteByUsername(username);
            return new MaiResponse.MaiSuccess<>("User has been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<AuthResponse> signIn(AuthRequest authRequest) {
        try {
            var responseUsername = repository.findByUsername(authRequest.username());
            if (responseUsername.isPresent()) {
                var model = responseUsername.get();
                var result = new BCryptPasswordEncoder().matches(authRequest.password(), model.getPassword());
                if (result) {
                    return new MaiResponse.MaiSuccess<>(new AuthResponse(
                            model.getUsername(),
                            maiJWTConfig.createToken(model.getUserId(),model.getUsername(), model.getRole(),model.getIsActive()),
                            model.getRole()

                    ), HttpStatus.OK);
                } else {
                    return new MaiResponse.MaiError<>("Password doesn't matches.", HttpStatus.NOT_FOUND);
                }
            } else {
                return new MaiResponse.MaiError<>("User doesn't exist", HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
