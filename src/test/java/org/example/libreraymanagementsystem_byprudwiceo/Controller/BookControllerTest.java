package org.example.libreraymanagementsystem_byprudwiceo.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.libreraymanagementsystem_byprudwiceo.Model.Books;
import org.example.libreraymanagementsystem_byprudwiceo.Service.LibraryService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.ObjDoubleConsumer;

@WebMvcTest(LibraryController.class)
public class BookControllerTest {


    @MockBean
    private LibraryService service;



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


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
    public void givenBookObj_WhenSaveBook_ThenReturnSavedBookObj() throws Exception {
        BDDMockito.given(service.saveBook(ArgumentMatchers.any(Books.class))).willAnswer((in)->
                in.getArgument(0));


        ResultActions rs= mockMvc.perform(MockMvcRequestBuilders.post("/library/addBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book1)));


        rs.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("As A Man Thinketh")));

    }


    @Test
    public void test_Given_BookObj_When_findById_Then_Return_Optional_Book() throws Exception
    {

        BDDMockito.given(service.getBookById(id)).willReturn(Optional.of(book1));


        ResultActions rs=mockMvc.perform(MockMvcRequestBuilders.get("/library/book/{id}",id));


        rs.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("As A Man Thinketh")));

    }



//    @Test
//    public void givenBookObj_WhenUpdateBook_ThenReturnUpdatedBookObj() throws Exception
//    {
//
//        BDDMockito.given(service.getBookById(id)).willReturn(Optional.of(book1));
//
//        BDDMockito.given(service.updateBook(ArgumentMatchers.eq(id),ArgumentMatchers.any(Books.class)))
//                .willReturn(book1);
//
//
//        ResultActions rs= mockMvc.perform(MockMvcRequestBuilders.put("/library/{id}",id).contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(book1)));
//
//
//        rs.andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is("As A Man Thinketh")));
//
//    }


    @Test
    public void givenBookObj_WhenUpdateBook_ThenReturnUpdatedBookObj() throws Exception {


        BDDMockito.given(service.getBookById(id)).willReturn(Optional.of(book1));


        BDDMockito.given(service.updateBook(ArgumentMatchers.eq(id),ArgumentMatchers.any(Books.class)))
                .willReturn(book1);

        ResultActions rs= mockMvc.perform(MockMvcRequestBuilders.put("/library/{id}",id)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(book1)));


        rs.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("As A Man Thinketh")));
    }

    @Test
    public void givenBookObj_WhenDeleteBook_DeleteBookObj() throws Exception {

        BDDMockito.given(service.getBookById(id)).willReturn(Optional.of(book1));

        BDDMockito.willDoNothing().given(service).deleteById(id);

        ResultActions rs=mockMvc.perform(MockMvcRequestBuilders.delete("/library/{id}",id));


        rs.andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service,Mockito.times(1)).deleteById(id);
    }


    @Test
    public void Test_Given_BookList_When_findAll_ThenReturnBookList() throws Exception
    {

        Books book2 = new Books();

        book2.setName("Zero To One");
        book2.setLocation("A-12-c");
        book2.setQuantity(27);


        List<Books>list=new ArrayList<>();

        list.add(book1);
        list.add(book2);

        BDDMockito.given(service.getAllBooks()).willReturn(list);


        ResultActions rs= mockMvc.perform(MockMvcRequestBuilders.get("/library/book"));

        rs.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(2)));
    }




}
