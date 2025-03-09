package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dtos.BorrowDTO;
import org.acme.entities.Book;
import org.acme.entities.Borrow;
import org.acme.entities.User;
import org.acme.repositories.BorrowRepository;

import java.sql.SQLException;
import java.time.LocalDate;

@ApplicationScoped
public class BorrowService {
    @Inject
    BorrowRepository borrowRepository;

    @Inject
    private UserService userService;

    @Inject
    private BookService bookService;


    public Borrow createBorrow(BorrowDTO dto) throws SQLException {
        User user = userService.getUserByCpf(dto.cpf()).orElseThrow(SQLException::new);
        Book book = bookService.getBookByIsbn(dto.isbn()).orElseThrow(SQLException::new);

        Borrow newBorrow = new Borrow();
        newBorrow.setUserId(user.getId());
        newBorrow.setBookId(book.getId());
        newBorrow.setDate(LocalDate.now());
        newBorrow.setDueDate(LocalDate.now().plusDays(10));

        return borrowRepository.saveBorrow(newBorrow);
    }

}
