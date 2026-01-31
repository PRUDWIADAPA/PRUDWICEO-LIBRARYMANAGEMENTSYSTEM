package org.example.libreraymanagementsystem_byprudwiceo.Repo;

import org.assertj.core.api.Assertions;
import org.example.libreraymanagementsystem_byprudwiceo.Model.Author;
import org.example.libreraymanagementsystem_byprudwiceo.Model.Books;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class BookRepoTest {

    @Autowired
    public BookRepo repo;

    private Books book1;

    private int id;

    @BeforeEach
    public void init()
    {
        id=7;
        book1=new Books();
        book1.setName("As A Man Thinketh");
        book1.setQuantity(7);
        book1.setLocation("A-11-A");


    }


    @Test
    public void giveBookObj_whenSaveBookObj_ThenReturnSavedBookObj()
    {
        //Given


        //When

        Books savedBook=repo.save(book1);

        // Then

//        Assertions.assertNotNull(savedBook);
//        Assertions.assert

        Assertions.assertThat(savedBook).isNotNull();

        Assertions.assertThat(savedBook.getId()).isGreaterThan(0);


    }


    @Test
    public void givenBookId_whenFindById_thenReturnBookObj()
    {

        //Given

        //book1.setId(id);
        Books saved=repo.save(book1);

        //When
        Optional<Books> books= repo.findById(saved.getId());

        //Then

        Assertions.assertThat(books).isNotEmpty();

    }


    @Test
    public void Given_ListOfBooks_When_findAll_Then_GetAllBooks()
    {

        Books book2=new Books();
        book2.setQuantity(77);
        book2.setName("Zero To One");
        book2.setLocation("B-7");

        List<Books> books= new ArrayList<>();

        books.add(book1);
        books.add(book2);

        List<Books>savedBooks= repo.saveAll(books);


        Assertions.assertThat(savedBooks).isNotNull();

        Assertions.assertThat(savedBooks).size().isEqualTo(2);

    }

    @Test
    public void GivenBookName_When_FindByBook_ThenReturnBook()
    {

       Books SavedBooks= repo.save(book1);

       Optional<Books> books=repo.findByName(book1.getName());

       Assertions.assertThat(books).isNotEmpty();
        org.junit.jupiter.api.Assertions.assertEquals(books.get().getName(),book1.getName());

    }



    @Test
    public void givenBookObj_whenUpdateBookObj_thenReturnUpdatedBookObj()
    {

       repo.save(book1);

       Books books=repo.findById(book1.getId()).get();

       books.setQuantity(7);
       books.setLocation("A - 11");

       Books updated=repo.save(books);

       Assertions.assertThat(updated.getQuantity()).isEqualTo(7);

       Assertions.assertThat(updated.getLocation()).isEqualTo("A - 11");


    }


    @Test
    public void givenEmpObj_WhenDeleteEmp_ThenDeleteEmp()
    {
        repo.save(book1);

        repo.deleteById(book1.getId());


        Optional<Books> books=repo.findById(book1.getId());

        Assertions.assertThat(books).isEmpty();
    }



    @Test
    public void givenBookNameAndAuthorName_when_IndexBasedMethodFindByAuthorAndName_ThenReturnBook()
    {
        Author author=new Author();
        author.setName("James Allen");

        book1.setAuthor(author);

        repo.save(book1);


        Optional<Books> books=repo.customQueryWithIndex(book1.getName(),author.getName());


        Assertions.assertThat(books).isNotEmpty();


    }


    @Test
    public void givenBookNameAndAuthorName_when_NameBasedMethodFindByAuthorAndName_ThenReturnBook()
    {
        Author author=new Author();
        author.setName("James Allen");

        book1.setAuthor(author);

        repo.save(book1);


        Optional<Books> books=repo.customQueryWithName(book1.getName(),author.getName());


        Assertions.assertThat(books).isNotEmpty();

        Assertions.assertThat(books.get().getName()).isEqualTo(book1.getName());


    }


    @Test
    public void given_BookNameAndAuthorName_When_nativeCustomQueryWithParamNames_Then_ReturnBook()
    {
        Author author=new Author();
        author.setName("James Allen");

        book1.setAuthor(author);

        repo.save(book1);


        Optional<Books> books=repo.nativeCustomQueryWithParamNames(book1.getName(),author.getName());


        Assertions.assertThat(books).isNotEmpty();

        Assertions.assertThat(books.get().getName()).isEqualTo(book1.getName());

    }


    @Test
    public void given_BookNameAndAuthorName_When_nativeCustomQueryWithIndex_Then_ReturnBook()
    {
        Author author=new Author();
        author.setName("James Allen");

        book1.setAuthor(author);

        repo.save(book1);


        Optional<Books> books=repo.nativeCustomQueryWithIndex(book1.getName(),author.getName());


        Assertions.assertThat(books).isNotEmpty();

        Assertions.assertThat(books.get().getName()).isEqualTo(book1.getName());

    }



}
