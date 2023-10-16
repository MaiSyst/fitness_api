package com.maisyst.fitness.dao.services.interfaces;

import com.maisyst.fitness.models.AuthRequest;
import com.maisyst.fitness.models.AuthResponse;
import com.maisyst.fitness.models.UserModel;
import com.maisyst.fitness.utils.MaiResponse;

public interface IUserService {
    MaiResponse<UserModel> findByUsername(String username);
    MaiResponse<UserModel> disableOrEnableUsername(String username, boolean isActive);
    MaiResponse<UserModel> updatePassword(String username, String model);
    MaiResponse<UserModel> add(UserModel model);
    MaiResponse<String> delete(String username);
    MaiResponse<AuthResponse> signIn(AuthRequest authRequest);
}