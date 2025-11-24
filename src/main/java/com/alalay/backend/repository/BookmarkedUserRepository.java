package com.alalay.backend.repository;

import com.alalay.backend.model.BookmarkedUser;
import com.alalay.backend.model.BookmarkedUser.BookmarkedUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookmarkedUserRepository extends JpaRepository<BookmarkedUser, BookmarkedUserId> {

    List<BookmarkedUser> findByIdUserId(UUID userId);

    void deleteByIdUserIdAndIdBookmarkedUserId(UUID userId, UUID bookmarkedUserId);

    boolean existsByIdUserIdAndIdBookmarkedUserId(UUID userId, UUID bookmarkedUserId);
}
