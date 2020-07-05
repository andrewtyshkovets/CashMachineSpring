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
    private Long id;
    private String role;

    public static UserDto from(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .role(user.getRole().name())
                .id(user.getId())
                .build();
    }
}
