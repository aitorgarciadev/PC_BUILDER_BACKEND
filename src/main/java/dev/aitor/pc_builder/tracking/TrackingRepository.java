package dev.aitor.pc_builder.tracking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
    
}
