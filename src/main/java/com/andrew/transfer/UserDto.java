package com.andrew.transfer;

import com.andrew.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private String username;

    public static UserDto from(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .build();
    }
}
