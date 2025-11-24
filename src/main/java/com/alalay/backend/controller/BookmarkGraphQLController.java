package com.alalay.backend.controller;

import com.alalay.backend.model.User;
import com.alalay.backend.services.BookmarkService;
import com.alalay.backend.services.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class BookmarkGraphQLController {

    private final BookmarkService bookmarkService;
    private final UserService userService;

    public BookmarkGraphQLController(BookmarkService bookmarkService, UserService userService) {
        this.bookmarkService = bookmarkService;
        this.userService = userService;
    }

    /* =============================
       GET BOOKMARKED USERS
       ============================= */
    @QueryMapping
    public List<User> bookmarkedUsers(@Argument UUID userId) {
        List<UUID> ids = bookmarkService.getBookmarkedUserIds(userId);
        return ids.stream()
                .map(id -> userService.findById(id).orElse(null))
                .filter(u -> u != null)
                .toList();
    }

    /* =============================
       CREATE BOOKMARK
       ============================= */
    @MutationMapping
    public boolean bookmarkUser(
            @Argument UUID userId,
            @Argument UUID bookmarkedUserId
    ) {
        return bookmarkService.bookmarkUser(userId, bookmarkedUserId);
    }

    /* =============================
       DELETE BOOKMARK
       ============================= */
    @MutationMapping
    public boolean removeBookmark(
            @Argument UUID userId,
            @Argument UUID bookmarkedUserId
    ) {
        return bookmarkService.removeBookmark(userId, bookmarkedUserId);
    }
}
