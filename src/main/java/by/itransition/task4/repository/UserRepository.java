package by.itransition.task4.repository;

import by.itransition.task4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    int deleteByIdIn(List<Long> ids);

    List<User> getByIdIn(List<Long> ids);

    @Modifying
    @Query("update User user set user.dateOfLastLogin = :date where user.id = :id")
    void updateDateOfLastLogin(@Param("date") LocalDate date, @Param("id") Long id);
}
