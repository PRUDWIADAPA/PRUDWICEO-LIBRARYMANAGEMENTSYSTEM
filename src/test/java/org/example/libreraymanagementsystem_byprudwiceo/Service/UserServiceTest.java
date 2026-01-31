package org.example.libreraymanagementsystem_byprudwiceo.Service;

import org.assertj.core.api.Assertions;
import org.example.libreraymanagementsystem_byprudwiceo.Model.Assistant;
import org.example.libreraymanagementsystem_byprudwiceo.Model.DTO.AppointmentReqDTO;
import org.example.libreraymanagementsystem_byprudwiceo.Model.Schedule;
import org.example.libreraymanagementsystem_byprudwiceo.Repo.AssistantRepo;
import org.example.libreraymanagementsystem_byprudwiceo.Repo.BookRepo;
import org.example.libreraymanagementsystem_byprudwiceo.Repo.ScheduleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private BookRepo repo;

    @Mock
    private AssistantRepo asitRepo;


    @Mock
    private ScheduleRepo schedulerepo;


    @InjectMocks
    private UserService userService;

    private LocalDateTime start;

    private LocalDateTime start2;

    private  Schedule schedule;

    private Assistant assistant;

    private int id;

    private AppointmentReqDTO req;

    @BeforeEach
    public void init()
    {
        id=1;
        start=LocalDateTime.now();

        start2=LocalDateTime.now().plusMinutes(120);

        List<LocalDateTime> slots=new ArrayList<>();

         req=new AppointmentReqDTO();

        req.setId(id);
        req.setRequestedTime(start);

         schedule=new Schedule();

        schedule.setId(id);
        schedule.setStartTime(start);
        schedule.setConfirmationStatus(Schedule.Confirmation.Confirmed);
        schedule.setEndTime(start.plusMinutes(30));
        schedule.setEmpId(id);

        slots.add(start);
        slots.add(start2);

        assistant=new Assistant();
        assistant.setAssistant_Id(id);
        assistant.setAssistant_Name("PRUDWI CEO");

        assistant.setAvailability(slots);


    }


    @Test
    public void Given_EmpIdAndAppointmentTime_When_scheduleAppointment_Then_ReturnBookedAppointment()
    {

        BDDMockito.given(asitRepo.findById(id)).willReturn(Optional.of(assistant));

        BDDMockito.given(asitRepo.save(ArgumentMatchers.any(Assistant.class))).willReturn(assistant);

        BDDMockito.given(schedulerepo.save(ArgumentMatchers.any(Schedule.class))).willReturn(schedule);

        //BDDMockito.given(schedulerepo.save(schedule)).willReturn(schedule);


        Schedule schedule1=userService.scheduleAppointment(id,start);

        Assertions.assertThat(schedule1).isNotNull();

        Assertions.assertThat(schedule1.getStartTime()).isEqualTo(start);



    }


    @Test
    public void Test_Given_AppointmentId_When_cancelAppointment_Then_CancelAppointment()
    {

        BDDMockito.given(schedulerepo.findById(id)).willReturn(Optional.of(schedule));

        BDDMockito.given(asitRepo.findById(id)).willReturn(Optional.of(assistant));

        //BDDMockito.willDoNothing().given(schedulerepo).deleteById(id);


        userService.cancelAppointment(id);

        Assertions.assertThat(assistant.getAvailability()).contains(start);

        Mockito.verify(asitRepo,Mockito.times(1)).save(assistant);

        Mockito.verify(schedulerepo,Mockito.times(1)).delete(schedule);
    }


    @Test
    public void Test_Given_AppointmentIdAndAppointmentReqDTO_When_updateAppointment_Then_Return_UpdatedAppointment()
    {

        req.setRequestedTime(start2);
        schedule.setStartTime(start2);

        BDDMockito.given(schedulerepo.findById(id)).willReturn(Optional.of(schedule));

        BDDMockito.given(asitRepo.findById(id)).willReturn(Optional.of(assistant));

        BDDMockito.given(schedulerepo.save(ArgumentMatchers.any(Schedule.class))).willReturn(schedule);

        Schedule updatedSchedule=userService.UpdateAppointment(id,req);


        Assertions.assertThat(updatedSchedule).isNotNull();

        Assertions.assertThat(updatedSchedule.getStartTime()).isEqualTo(start2);

    }



    @Test
    public void Test_Given_AppointmentId_When_GetAppointmentById_Then_ReturnAppointment()
    {

        BDDMockito.given(schedulerepo.findById(id)).willReturn(Optional.of(schedule));

        Schedule schedule1=userService.getAppointment(id);

        Assertions.assertThat(schedule1).isNotNull();

        Assertions.assertThat(schedule.getStartTime()).isEqualTo(start);

    }


    @Test
    public void Test_Given_EmpId_When_GetSlots_Then_Return_AvailableSlots()
    {
       BDDMockito.given(asitRepo.findById(id)).willReturn(Optional.of(assistant));

       List<LocalDateTime> slots=userService.getAvailSlots(id);

       Assertions.assertThat(slots).isNotNull();

       Assertions.assertThat(slots).size().isEqualTo(2);


    }





}
