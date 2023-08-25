package com.site.bemystory.repository;

import com.site.bemystory.domain.Follow;
import com.site.bemystory.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<List<Follow>> findByFromUser(User from_user);
    Optional<List<Follow>> findByToUser(User to_user);
}
