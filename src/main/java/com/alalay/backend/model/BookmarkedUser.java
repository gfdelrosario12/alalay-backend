package com.alalay.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "bookmarked_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkedUser {
    @EmbeddedId
    private BookmarkedUserId id;

    @Embeddable
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class BookmarkedUserId implements java.io.Serializable {
        @Column(columnDefinition = "uuid")
        private UUID userId;

        @Column(columnDefinition = "uuid")
        private UUID bookmarkedUserId;
    }

    public BookmarkedUserId getId() {
        return id;
    }

    public void setId(BookmarkedUserId id) {
        this.id = id;
    }
}
