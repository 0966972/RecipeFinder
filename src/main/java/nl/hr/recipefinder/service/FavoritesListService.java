package nl.hr.recipefinder.service;

import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.model.entity.FavoritesList;
import nl.hr.recipefinder.repository.FavoritesListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FavoritesListService {
  private final FavoritesListRepository favoritesListRepository;

  public Optional<FavoritesList> findById(Long id) {
    return favoritesListRepository.findById(id);
  }

  public List<FavoritesList> findAllByUserId(Long id) {
    return favoritesListRepository.findAllByUserId(id);
  }

  public List<FavoritesList> findAll() {
    return favoritesListRepository.findAll();
  }

  public FavoritesList save(FavoritesList favoritesList) {
    return favoritesListRepository.save(favoritesList);
  }

  public FavoritesList update(FavoritesList favoritesList) {
    return favoritesListRepository.save(favoritesList);
  }

}
