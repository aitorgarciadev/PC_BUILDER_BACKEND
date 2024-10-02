package dev.aitor.pc_builder.order;

import java.util.List;

import dev.aitor.pc_builder.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
