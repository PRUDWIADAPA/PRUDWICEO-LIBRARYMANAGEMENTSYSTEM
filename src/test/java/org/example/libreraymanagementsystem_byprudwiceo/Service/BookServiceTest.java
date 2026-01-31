package org.example.libreraymanagementsystem_byprudwiceo.Service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.assertj.core.api.Assertions;
import org.example.libreraymanagementsystem_byprudwiceo.Exception.BookException;
import org.example.libreraymanagementsystem_byprudwiceo.Model.Books;
import org.example.libreraymanagementsystem_byprudwiceo.Repo.BookRepo;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepo repo;

    @InjectMocks
    private LibraryService service;

    private Books book1;

    private int id;


    @BeforeEach
    public void setup()
    {
        //repo.deleteAll();;
        id=7;
        book1=new Books();
        book1.setName("As A Man Thinketh");
        book1.setQuantity(7);
        book1.setLocation("A-11-A");
        book1.setId(id);

        //repo.save(book1);

    }


    @Test
    public void givenBookObj_WhenSaveBook_ThenReturnSavedBookObj()
    {


        //given

        BDDMockito.given(repo.findById(book1.getId())).willReturn(Optional.empty());

        BDDMockito.given(repo.save(book1)).willReturn(book1);

        //when

        Books books=service.saveBook(book1);

        // Then

        Assertions.assertThat(books.getId()).isEqualTo(7);

        Assertions.assertThat(books).isNotNull();



    }


    @Test
    public void givenExistingBookObj_WhenSaveBook_ThenThrowException()
    {


        //given

        BDDMockito.given(repo.findById(book1.getId())).willReturn(Optional.of(book1));

        //BDDMockito.given(repo.save(book1)).willReturn(book1);

        //when
        org.junit.jupiter.api.Assertions.assertThrows(BookException.class,()->
        {
            service.saveBook(book1);
        });


        // Then
//
//        Assertions.assertThat(books.getId()).isEqualTo(7);
//
//        Assertions.assertThat(books).isNotNull();

        Mockito.verify(repo,Mockito.never()).save(Mockito.any(Books.class));



    }



    @Test
    public void givenListOfBook_WhenFindAll_ThenReturnBooksList()
    {

        Books book2=new Books();

        book2.setId(4);
        book2.setName("Zero To One");
        book2.setLocation("A-25_d");
        book2.setQuantity(77);

        List<Books>booksList=new ArrayList<>();

        booksList.add(book1);
        booksList.add(book2);


        BDDMockito.given(repo.findAll()).willReturn(booksList);


        List<Books>books=service.getAllBooks();



        Assertions.assertThat(books).isNotNull();
        Assertions.assertThat(books).size().isEqualTo(2);





    }

    @Test
    public void givenEmptyBookList_WhenFindAll_ThenReturnBooksList()
    {


        BDDMockito.given(repo.findAll()).willReturn(Collections.emptyList());


        List<Books>books=service.getAllBooks();



        Assertions.assertThat(books).isEmpty();
        Assertions.assertThat(books).size().isEqualTo(0);





    }



    @Test
    public void given_BookId_When_FindById_Then_Return_Book()
    {

        BDDMockito.given(repo.findById(id)).willReturn(Optional.of(book1));


        Optional<Books>books=service.getBookById(id);


        Assertions.assertThat(books).isNotEmpty();

    }



    @Test
    public void given_BookIdAndUpdatedBook_When_UpdatedBook_Then_ReturnUpdatedBook()
    {

        int quantity=777;

        String location="A-2m-5f";
        BDDMockito.given(repo.findById(id)).willReturn(Optional.of(book1));

        book1.setQuantity(quantity);
        book1.setLocation(location);

        BDDMockito.given(repo.save(book1)).willReturn(book1);


        Books books=service.getBookById(id).get();


        books.setQuantity(quantity);
        books.setLocation(location);

        Books updatedBook=service.updateBook(id,books);


        //Assertions.assertThat(updatedBook.getQuantity()).

        Assertions.assertThat(updatedBook.getQuantity()).isEqualTo(quantity);

        Assertions.assertThat(updatedBook.getLocation()).isEqualTo(location);

    }


    @Test
    public void given_BookId_WhenDeleteBookById_Then_DeleteBook()
    {

        BDDMockito.willDoNothing().given(repo).deleteById(id);


        service.deleteById(id);


        Mockito.verify(repo,Mockito.times(1)).deleteById(id);

    }





}
