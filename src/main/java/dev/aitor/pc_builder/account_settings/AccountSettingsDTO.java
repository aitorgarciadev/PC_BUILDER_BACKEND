package dev.aitor.pc_builder.account_settings;

import dev.aitor.pc_builder.user.UserDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSettingsDTO {
    
    private Long id;
    private String language;
    private UserDTO user;
}
