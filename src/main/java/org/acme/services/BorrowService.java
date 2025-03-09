package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import org.acme.dtos.BorrowDTO;
import org.acme.entities.Book;
import org.acme.entities.Borrow;
import org.acme.entities.User;
import org.acme.repositories.BorrowRepository;

import java.sql.SQLException;
import java.time.LocalDate;

import java.util.List;

@ApplicationScoped
public class BorrowService {
    @Inject
    BorrowRepository borrowRepository;

    @Inject
    private UserService userService;

    @Inject
    private BookService bookService;

    public int countBorrows() throws SQLException {
        return borrowRepository.countBorrows();
    }

    public List<Borrow> getAllBorrows() throws SQLException {
        return borrowRepository.getAllBorrows();
    }

    public Borrow getBorrowById(Long id) throws SQLException {
        return borrowRepository.getBorrowById(id);
    }

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

    public void deleteBorrow(Long id) throws SQLException {
        borrowRepository.deleteBorrow(id);
    }

}
