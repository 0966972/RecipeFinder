//package nl.hr.recipefinder.repository;
//
//import nl.hr.recipefinder.model.entity.BaseEntity;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.repository.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.Serializable;
//import java.util.List;
//
//public interface CrudRepository<T extends BaseEntity, ID extends Serializable> extends Repository<T, ID> {
//
//  <S extends T> S save(S entity);
//
//  T findById(ID primaryKey);
//
//  List<T> findAll();
//
//  @Transactional
//  @Modifying(flushAutomatically = true)
//  void deleteById(ID primaryKey);
//}
