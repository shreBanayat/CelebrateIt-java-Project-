package com.celebrateit.service;

import com.celebrateit.dto.UsersResponseDTO;

public interface UsersService {
    UsersResponseDTO getUsersDetailsById(Integer userid);
}
