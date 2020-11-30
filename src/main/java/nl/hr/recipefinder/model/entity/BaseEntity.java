//package nl.hr.recipefinder.model.entity;
//
//import lombok.Data;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@MappedSuperclass
//public @Data
//class BaseEntity {
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @Column(name = "id")
//  private Long id;
//
//  @CreationTimestamp
//  private LocalDateTime createdOn;
//
//  @UpdateTimestamp
//  private LocalDateTime lastModifiedOn;
//}
