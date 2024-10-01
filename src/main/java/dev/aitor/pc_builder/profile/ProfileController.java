package dev.aitor.pc_builder.profile;

import dev.aitor.pc_builder.profile.profile_exceptions.ProfileNotFoundException;
import dev.aitor.pc_builder.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.security.Principal;

@RestController
@RequestMapping("${api-endpoint}/profiles")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<ProfileDTO>> getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    @GetMapping("/user")
    public ResponseEntity<ProfileDTO> getProfileByUser(Principal connectedUser) {
        try {
            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            ProfileDTO profile = profileService.getProfileByUser(user);
            return ResponseEntity.ok(profile);
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/user")
    public ResponseEntity<ProfileDTO> updateProfile(Principal connectedUser,
            @Valid @RequestBody ProfileDTO profileDTO) {
        try {
            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            ProfileDTO updatedProfile = profileService.updateProfile(user, profileDTO);
            return ResponseEntity.ok(updatedProfile);
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        try {
            profileService.deleteProfile(id);
            return ResponseEntity.noContent().build();
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
