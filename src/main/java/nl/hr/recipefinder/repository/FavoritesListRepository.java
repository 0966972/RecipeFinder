package nl.hr.recipefinder.repository;

import nl.hr.recipefinder.model.entity.FavoritesList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesListRepository extends JpaRepository<FavoritesList, Long> {
  List<FavoritesList> findAllByUserId(Long userId);
}
