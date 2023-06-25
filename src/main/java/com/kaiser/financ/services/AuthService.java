package com.kaiser.financ.services;

import com.kaiser.financ.dtos.ResetPasswordDTO;

public interface AuthService {

  void sendResetPassword(String email);

  void changePassword(ResetPasswordDTO objDto);
}
