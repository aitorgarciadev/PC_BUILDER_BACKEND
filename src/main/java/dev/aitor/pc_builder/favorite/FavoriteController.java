package dev.aitor.pc_builder.favorite;

import dev.aitor.pc_builder.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("${api-endpoint}/favorites")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<Page<FavoriteDTO>> getFavorites(Principal connectedUser, @PageableDefault(size = 8, sort = {"product"}) Pageable pageable) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Page<FavoriteDTO> favorites = favoriteService.getFavoriteByUserId(user.getId(), pageable);
        return ResponseEntity.ok(favorites);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkFavorite(Principal connectedUser, @RequestHeader("Product-ID") Long productId) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Boolean favorite = favoriteService.checkFavorite(user.getId(), productId);
        return ResponseEntity.ok(favorite);
    }

    @PostMapping
    public ResponseEntity <List<FavoriteDTO>> addToFavorite(Principal connectedUser, @RequestHeader("Product-ID") Long productId) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        List<FavoriteDTO> addFavorite = favoriteService.addProductToFavorite(user.getId(),productId);
        return ResponseEntity.ok(addFavorite);
    }


    @DeleteMapping
    public ResponseEntity<Void> removeFromFavorite(Principal connectedUser, @RequestHeader("Product-ID") Long productId) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
    
        favoriteService.removeProductFromFavorite(user.getId(), productId);
        return ResponseEntity.noContent().build();
    }
}
