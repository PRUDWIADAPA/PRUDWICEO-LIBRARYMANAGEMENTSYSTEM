package org.example.libreraymanagementsystem_byprudwiceo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.example.libreraymanagementsystem_byprudwiceo.Model.Books;
import org.example.libreraymanagementsystem_byprudwiceo.Repo.BookRepo;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

@ActiveProfiles("test")
public class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Autowired
    private BookRepo repo;

    private Books book1;

    private int id;


    @BeforeEach
    public void setup()
    {
        repo.deleteAll();;
        id=7;
        book1=new Books();
        book1.setName("As A Man Thinketh");
        book1.setQuantity(7);
        book1.setLocation("A-11-A");
        book1.setId(id);

    }


    @Test
    public void Test_Given_Book_Obj_When_Create_Book_Then_Return_Saved_Book_Obj() throws Exception
    {

       ResultActions response= mockMvc.perform(MockMvcRequestBuilders.post("/library/addBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book1)));


       response.andExpect(MockMvcResultMatchers.status().isCreated())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(book1.getName())));
    }

    @Test
    public void Test_Given_BookId_When_GetBookById_Then_Return_Book() throws Exception
    {

        Books saved=repo.save(book1);
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/library/book/{id}",saved.getId()));


        response.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is(book1.getName())));



    }

    @Test
    public void Test_Given_ListOfBooks_When_GetAllBooks_Then_Return_Book_List() throws Exception
    {
        Books b2=new Books();

        b2.setName("Nagomi");
        b2.setQuantity(77);
        b2.setLocation("A-12-2d");

        List<Books> booksList=new ArrayList<>();

        booksList.add(book1);
        booksList.add(b2);

        repo.saveAll(booksList);

        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/library/book"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(booksList.size())));



    }


    @Test
    public void GivenBook_IdAndBookObj_When_UpdateBook_Then_Return_Updated_BookObj() throws Exception
    {

        repo.save(book1);
        book1.setName("Nagomi");
        book1.setLocation("A-12-bc");
        book1.setQuantity(32);

        id=book1.getId();

      Books updated= repo.save(book1);

       ResultActions response= mockMvc.perform(MockMvcRequestBuilders.put("/library/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book1)));


       response.andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is(updated.getName())))
               .andExpect(MockMvcResultMatchers.jsonPath("$.location",CoreMatchers.is(updated.getLocation())));


    }


    @Test
    public void Test_Given_Book_Id_When_Delete_Book_Then_DeleteBook() throws Exception
    {


        repo.save(book1);

        id=book1.getId();

       ResultActions response= mockMvc.perform(MockMvcRequestBuilders.delete("/library/{id}",id));

       response.andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertThat(repo.findById(id)).isEmpty();



    }

}
