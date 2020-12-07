package nl.hr.recipefinder.service;

import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.repository.UserRepository;
import nl.hr.recipefinder.security.UserDetailsAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userRepository.findUserByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException(String.format("No user  with username: %s was found", username));
    }

    return new UserDetailsAdapter(user);
  }
}
