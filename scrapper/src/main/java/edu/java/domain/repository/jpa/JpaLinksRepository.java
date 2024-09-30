package edu.java.domain.repository.jpa;

import edu.java.domain.entity.Links;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinksRepository extends JpaRepository<Links, Long> {
    Links findByLink(String url);

    @Query(value = "select * from links where last_check <= ?1", nativeQuery = true)
    List<Links> findAllOutdatedLinks(OffsetDateTime lastCheck);
}
