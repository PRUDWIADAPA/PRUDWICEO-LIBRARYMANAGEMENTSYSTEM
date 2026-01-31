package org.example.libreraymanagementsystem_byprudwiceo.Service;

import org.example.libreraymanagementsystem_byprudwiceo.Exception.BookException;
import org.example.libreraymanagementsystem_byprudwiceo.Model.Books;
import org.example.libreraymanagementsystem_byprudwiceo.Model.DTO.BookStockUp;
import org.example.libreraymanagementsystem_byprudwiceo.Repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    private BookRepo repo;

    public Books saveBook(Books books)
    {
        Optional<Books> book1= repo.findById(books.getId());

        if(book1.isPresent())
        {
            throw new BookException("Book is already present");
        }

       return repo.save(books);
    }

    public Optional<Books> getBookById(int id)
    {
        Optional<Books>books=repo.findById(id);

        if(books.isEmpty())
        {
            throw new BookException("Book not found with Id");
        }
        return books;
    }


    public List<Books> getAllBooks() {

        return repo.findAll();
    }

    public Books updateBook(int id,Books book) {

        Optional<Books> book1=repo.findById(id);

        if(book1.isEmpty())
        {
            throw new BookException("Book Not found.Please enter correct book id");
        }

        Books book2=book1.get();

        book2.setName(book.getName());
        book2.setQuantity(book.getQuantity());
        book2.setAuthor(book.getAuthor());

        //book2.getAuthor().getBookSet().add(book2);

       return repo.save(book2);
    }

    public void deleteById(int id) {

        repo.deleteById(id);
    }


    public String BookLocation(String Bname)
    {
        return repo.findByName(Bname).get().getLocation();
    }

    public Books stockUpBooks(int id, BookStockUp books)
    {
        Optional<Books> books1=repo.findById(id);

        if(books1.isEmpty())
        {
            throw new BookException("Book Not Found");
        }

        Books books2=books1.get();

        books2.setQuantity(books2.getQuantity()+books.getQuantity());
        return repo.save(books2);
    }





}
