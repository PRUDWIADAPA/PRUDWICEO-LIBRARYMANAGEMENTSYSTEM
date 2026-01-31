package org.example.libreraymanagementsystem_byprudwiceo.Controller;

import org.example.libreraymanagementsystem_byprudwiceo.Exception.BookException;
import org.example.libreraymanagementsystem_byprudwiceo.Model.Books;
import org.example.libreraymanagementsystem_byprudwiceo.Model.DTO.BookStockUp;
import org.example.libreraymanagementsystem_byprudwiceo.Service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryService service;

    @PostMapping("/addBook")
    public ResponseEntity<Books> addBook(@RequestBody Books book)
    {
        //System.out.println("-----------controller called------------------------");

        return  new ResponseEntity<>(service.saveBook(book), HttpStatus.CREATED);
    }


    @GetMapping("/book/{id}")
    public ResponseEntity<Optional<Books>> getBookById(@PathVariable int id)
    {
        return new ResponseEntity<>(service.getBookById(id),HttpStatus.OK);
    }


    @GetMapping("/book")
    public ResponseEntity<List<Books>> getAllBook()
    {
        return new ResponseEntity<>(service.getAllBooks(),HttpStatus.OK);
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id,@RequestBody Books book)
    {
       return new ResponseEntity<>(service.updateBook(id,book),HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteBook(@PathVariable int id)
    {
        service.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }



    @GetMapping("/book/location/{bookName}")
    public ResponseEntity<?>getBookLocation( @PathVariable String bookName)
    {
       return new ResponseEntity<>( service.BookLocation(bookName),HttpStatus.FOUND);

    }


    @PutMapping("/book/stockUp/{id}")
    public ResponseEntity<?>bookStockUp(@PathVariable int id, @RequestBody BookStockUp stockUp)
    {

        return new ResponseEntity<>(service.stockUpBooks(id,stockUp),HttpStatus.OK);
    }



}
