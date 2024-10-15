package edu.java.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "chat")
@RequiredArgsConstructor
public class Chat {
    @Id
    @Column(name = "chat_id")
    private Long id;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "communications",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id")
    )
    private List<Links> followingLinks = new ArrayList<>();

    public Chat(Long id, OffsetDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }
}
