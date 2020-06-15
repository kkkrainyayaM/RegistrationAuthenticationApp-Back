package by.itransition.task4.repository;

import by.itransition.task4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    int deleteByIdIn(List<Long> ids);

    List<User> getByIdIn(List<Long> ids);
}
