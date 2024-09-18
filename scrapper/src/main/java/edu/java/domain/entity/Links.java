package edu.java.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table (name = "links")
@RequiredArgsConstructor
public class Links {
    @Id
    @Column(name = "link_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "link", unique = true)
    private String link;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "last_update")
    private OffsetDateTime lastUpdate;

    @Column(name = "last_check")
    private OffsetDateTime lastCheck;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "followingLinks")
    private List<Chat> followingChats = new ArrayList<>();

    public Links(String link, OffsetDateTime createdAt, OffsetDateTime lastUpdate, OffsetDateTime lastCheck) {
        this.link = link;
        this.createdAt = createdAt;
        this.lastUpdate = lastUpdate;
        this.lastCheck = lastCheck;
    }
}
