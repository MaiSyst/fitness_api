package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.models.*;
import com.maisyst.fitness.utils.MaiResponse;

import java.util.List;
import java.util.Map;

public interface IUserService {
    MaiResponse<UserModel> findById(String userId);
    MaiResponse<UserModel> disableOrEnableUsername(String username, boolean isActive);
    MaiResponse<UserModel> updatePassword(String username, String model);
    MaiResponse<UserModel> add(UserModel model);
    MaiResponse<String> delete(List<String> ids);
    MaiResponse<AuthResponse> signIn(AuthRequest authRequest);
    MaiResponse<Map<String,Object>>checkToken(String token);
    MaiResponse<List<UserResponse>> fetchAll();
}
