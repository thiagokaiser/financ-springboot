package com.kaiser.financ.services;

import com.kaiser.financ.dto.ResetPasswordDTO;

public interface AuthService {

  void sendResetPassword(String email);

  void changePassword(ResetPasswordDTO objDto);
}
