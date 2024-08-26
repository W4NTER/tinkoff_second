package edu.java.domain.repository;

import edu.java.domain.dto.LinksDTO;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinksRepository {
    void add(URI link, OffsetDateTime lastUpdate);

    void remove(URI link);

    List<LinksDTO> findAll();

    List<LinksDTO> findAllOutdatedLinks();
}
