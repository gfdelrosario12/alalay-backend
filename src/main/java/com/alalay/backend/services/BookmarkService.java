package com.alalay.backend.services;

import com.alalay.backend.model.BookmarkedUser;
import com.alalay.backend.repository.BookmarkedUserRepository;
import com.alalay.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookmarkService {

    private final BookmarkedUserRepository bookmarkRepo;
    private final UserRepository userRepo;

    public BookmarkService(BookmarkedUserRepository bookmarkRepo, UserRepository userRepo) {
        this.bookmarkRepo = bookmarkRepo;
        this.userRepo = userRepo;
    }

    /* =============================
       CREATE BOOKMARK
       ============================= */
    @Transactional
    public boolean bookmarkUser(UUID userId, UUID bookmarkedUserId) {

        if (!userRepo.existsById(userId) || !userRepo.existsById(bookmarkedUserId))
            throw new RuntimeException("User does not exist");

        if (bookmarkRepo.existsByIdUserIdAndIdBookmarkedUserId(userId, bookmarkedUserId))
            return false; // already bookmarked

        BookmarkedUser bookmark = new BookmarkedUser(
                new BookmarkedUser.BookmarkedUserId(userId, bookmarkedUserId)
        );
        bookmarkRepo.save(bookmark);
        return true;
    }

    /* =============================
       GET BOOKMARKED USERS
       ============================= */
    public List<UUID> getBookmarkedUserIds(UUID userId) {
        return bookmarkRepo.findByIdUserId(userId)
                .stream()
                .map(b -> b.getId().getBookmarkedUserId())
                .toList();
    }

    /* =============================
       DELETE BOOKMARK
       ============================= */
    @Transactional
    public boolean removeBookmark(UUID userId, UUID bookmarkedUserId) {

        if (!bookmarkRepo.existsByIdUserIdAndIdBookmarkedUserId(userId, bookmarkedUserId))
            return false;

        bookmarkRepo.deleteByIdUserIdAndIdBookmarkedUserId(userId, bookmarkedUserId);
        return true;
    }
}
