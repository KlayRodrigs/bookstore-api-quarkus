package org.acme.dtos;

import org.acme.entities.Book;
import org.acme.entities.Borrow;
import org.acme.entities.User;

import java.time.LocalDate;

public record BorrowResponseDTO(Long id, User user, Book book, LocalDate date, LocalDate dueDate) {
    public static BorrowResponseDTO mapToResponse(User user, Book book, Borrow borrow) {
        return new BorrowResponseDTO(
                borrow.getId(),
                user,
                book,
                borrow.getDate(),
                borrow.getDueDate()
        );
    }
}
