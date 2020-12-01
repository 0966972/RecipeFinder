package nl.hr.recipefinder.repository;

import nl.hr.recipefinder.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findUserByUsername(String username);
  User findUserByUsernameAndPassword(String username, String password);
}