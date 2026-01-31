package org.example.libreraymanagementsystem_byprudwiceo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.libreraymanagementsystem_byprudwiceo.Model.DTO.AppointmentReqDTO;
import org.example.libreraymanagementsystem_byprudwiceo.Model.Schedule;
import org.example.libreraymanagementsystem_byprudwiceo.Service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    private int id;

    private Schedule schedule;

    private LocalDateTime start;

    private AppointmentReqDTO reqDTO;


    @BeforeEach
    public void setUp()
    {
        id=1;
        start=LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
         schedule=new Schedule();
        schedule.setEmpId(1);
        schedule.setId(1);
        schedule.setStartTime(start);
        schedule.setEndTime(start.plusMinutes(30));
        schedule.setConfirmationStatus(Schedule.Confirmation.Confirmed);
        reqDTO=new AppointmentReqDTO();

        reqDTO.setId(1);
        reqDTO.setRequestedTime(start);
    }


    @Test
    public void givenAppointmentReqDTO_When_requestAssistance_Then_Return_ScheduledAppointment() throws Exception
    {


        BDDMockito.given(service.scheduleAppointment(ArgumentMatchers.eq(id),ArgumentMatchers.eq(start))).willReturn(schedule);



        ResultActions response=mvc.perform(MockMvcRequestBuilders.post("/library/booking/{id}",id).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reqDTO)));


        response.andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.startTime", CoreMatchers.is(start.toString())));

    }



    @Test
    public void given_AppointmentId_When_cancelAppointment_Then_Cancel_Appointment() throws Exception
        {

            BDDMockito.willDoNothing().given(service).cancelAppointment(id);


            ResultActions response=mvc.perform(MockMvcRequestBuilders.delete("/library/booking/{id}",id));


            Mockito.verify(service,Mockito.times(1)).cancelAppointment(ArgumentMatchers.eq(id));

            response.andExpect(MockMvcResultMatchers.status().isOk());

        }


        @Test
        public void Test_Given_AppointmentId_When_Update_Appointment_Update_Appointment() throws Exception
        {
            LocalDateTime newApp=LocalDateTime.now().plusMinutes(7).truncatedTo(ChronoUnit.MILLIS);
            reqDTO.setRequestedTime(newApp);

            schedule.setStartTime(newApp);

            BDDMockito.given(service.UpdateAppointment(ArgumentMatchers.eq(id),ArgumentMatchers.any(AppointmentReqDTO.class))).willReturn(schedule);

          ResultActions response=  mvc.perform(MockMvcRequestBuilders.put("/library/booking/{id}",id).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(reqDTO)));

            response.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.startTime",CoreMatchers.is(newApp.toString())));
        }







}
