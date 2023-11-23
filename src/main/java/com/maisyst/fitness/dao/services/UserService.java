package com.maisyst.fitness.dao.services;

import com.maisyst.fitness.dao.repositories.IUserRepository;
import com.maisyst.fitness.dao.services.interfaces.IUserService;
import com.maisyst.fitness.models.*;
import com.maisyst.fitness.security.jwt.MaiJWTConfig;
import com.maisyst.fitness.security.jwt.MaiJwtDecoder;
import com.maisyst.fitness.utils.AuthRole;
import com.maisyst.fitness.utils.MaiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService {
    private final IUserRepository repository;
    private final MaiJWTConfig maiJWTConfig;
    private final MaiJwtDecoder maiJwtDecoder;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserService(IUserRepository repository, MaiJWTConfig maiJWTConfig, MaiJwtDecoder maiJwtDecoder, JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.maiJWTConfig = maiJWTConfig;
        this.maiJwtDecoder = maiJwtDecoder;
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public MaiResponse<UserModel> findById(String userId) {
        try {
            var response = repository.findById(userId);
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
            var response = repository.findByUsernameAndIsActive(username,true);
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
    public MaiResponse<String> delete(List<String> ids) {
        try {
            repository.deleteAllById(ids);
            return new MaiResponse.MaiSuccess<>("User has been deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<AuthResponse> signIn(AuthRequest authRequest) {
        try {
            var responseUsername = repository.findByUsernameAndIsActive(authRequest.username(), true);
            if (responseUsername.isPresent()) {
                var model = responseUsername.get();
                var result = new BCryptPasswordEncoder().matches(authRequest.password(), model.getPassword());
                if (result) {
                    int daysValidate = 3;
                    if (model.getRole() == AuthRole.ADMIN) {
                        daysValidate = 1;
                    }
                    return new MaiResponse.MaiSuccess<>(new AuthResponse(
                            model.getUsername(),
                            maiJWTConfig.createToken(
                                    model.getUserId(), model.getUsername(),
                                    model.getRole() == AuthRole.ADMIN ? "admin" : model.getRoom().getRoomId(),
                                    model.getRole(), model.getIsActive(),
                                    daysValidate),
                            model.getRole() == AuthRole.ADMIN ? "admin" : model.getRoom().getRoomId(),
                            model.getRole(),
                            model.getIsActive()
                    ), HttpStatus.OK);
                } else {
                    return new MaiResponse.MaiError<>("Password doesn't matches.", HttpStatus.NOT_FOUND);
                }

            } else {
                return new MaiResponse.MaiError<>("User doesn't exist or disabled", HttpStatus.FORBIDDEN);
            }

        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MaiResponse<Map<String, Object>> checkToken(String token) {
        var response = maiJwtDecoder.decodedJWT(token);
        if (response == null) {
            return new MaiResponse.MaiError<>("Invalid token", HttpStatus.FORBIDDEN);
        }

        return new MaiResponse.MaiSuccess<>(new HashMap<>() {{
            put("username", response.getClaim("username").asString());
            put("role", response.getClaim("role").asString());
            put("isActive", response.getClaim("isAccountActivate").asBoolean());
            put("token", token);
            put("roomId", response.getClaim("roomId").asString());
        }}, HttpStatus.OK);
    }

    public MaiResponse<List<UserResponse>> fetchAll() {
        try {

            var response = jdbcTemplate.query("SELECT * FROM login WHERE role='USER'", (rs, rowNum) -> {
                var room = jdbcTemplate.query("SELECT room_id,room_name FROM room WHERE room_id=?",
                        (rs1, rowNum1) -> new RoomModel(rs1.getString("room_id"), rs1.getString("room_name")), rs.getString("room_id")).get(0);
                return new UserResponse(
                        rs.getString("user_id"),
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("date"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        room,
                        rs.getBoolean("is_active"),
                        AuthRole.USER
                );
            });
            return new MaiResponse.MaiSuccess<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public MaiResponse<UserModel> update(String userId, UserModel model) {
        try {
            var response = repository.findByUsername(userId);
            if (response.isPresent()) {
                response.get().setFirstName(model.getFirstName());
                response.get().setLastName(model.getLastName());
                response.get().setDate(model.getDate());
                response.get().setAddress(model.getAddress());
                response.get().setUsername(model.getFirstName()+"@"+response.get().getUsername().split("@")[1]);
                if (!model.getPassword().isBlank() && model.getPassword() == null) {
                    response.get().setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
                }
                return new MaiResponse.MaiSuccess<>(repository.save(response.get()), HttpStatus.OK);
            } else {
                return new MaiResponse.MaiError<>("User doesn't exist", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new MaiResponse.MaiError<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
