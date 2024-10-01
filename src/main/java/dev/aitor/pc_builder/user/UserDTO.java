package dev.aitor.pc_builder.user;

import java.util.*;

import dev.aitor.pc_builder.account_settings.AccountSettingsDTO;
import dev.aitor.pc_builder.cart.CartDTO;
import dev.aitor.pc_builder.order.OrderDTO;
import dev.aitor.pc_builder.profile.ProfileDTO;
import dev.aitor.pc_builder.review.ReviewDTO;
import dev.aitor.pc_builder.role.RoleDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long id;
    private String email;
    private String password;
    private ProfileDTO profile;
    private Set<RoleDTO> roles;
    private List<ReviewDTO> reviews;
    private AccountSettingsDTO accountSettings;
    private List<OrderDTO> orders;
    private CartDTO cart;
}
