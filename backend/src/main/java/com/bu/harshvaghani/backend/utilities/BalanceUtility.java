package com.bu.harshvaghani.backend.utilities;

import com.bu.harshvaghani.backend.dto.UserDTO;

public class BalanceUtility {
    public static String generateBalanceId(UserDTO user1, UserDTO user2) {
        Long user1ID = user1.getId();
        Long user2ID = user2.getId();
        return user1ID < user2ID ?
                user1ID + "_" + user2ID :
                user2ID + "_" + user1ID;
    }
}

