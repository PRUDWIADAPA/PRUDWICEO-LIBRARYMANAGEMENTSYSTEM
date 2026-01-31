package org.example.libreraymanagementsystem_byprudwiceo.Controller;

import org.example.libreraymanagementsystem_byprudwiceo.Model.Books;
import org.example.libreraymanagementsystem_byprudwiceo.Model.DTO.AppointmentReqDTO;
import org.example.libreraymanagementsystem_byprudwiceo.Model.DTO.BorrowOrReturnResponse;
import org.example.libreraymanagementsystem_byprudwiceo.Model.DTO.ReturnOrBorrowRequest;
import org.example.libreraymanagementsystem_byprudwiceo.Model.Schedule;
import org.example.libreraymanagementsystem_byprudwiceo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/library")
public class UserController {

    @Autowired
    private UserService service;


    @PutMapping({"/user/borrow","/user/return"})
    @ResponseStatus(HttpStatus.OK)
    public BorrowOrReturnResponse borrowOrReturnBook(@RequestBody ReturnOrBorrowRequest BookRequest)
    {
        return service.borrowOrReturnBook(BookRequest);
    }


    @GetMapping("/getSlots/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<LocalDateTime> timeSlotsAvailable(@PathVariable int id)
    {

        return service.getAvailSlots(id);
    }


    @PostMapping("/booking/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Schedule  requestAssistance(@PathVariable int id,@RequestBody AppointmentReqDTO localDateTime )
    {

  return  service.scheduleAppointment(id,localDateTime.getRequestedTime());
    }


    @DeleteMapping("/booking/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String cancelAppointment(@PathVariable int id)
    {
        service.cancelAppointment(id);

        return "Appointment Cancelled successfully";

    }


    @PutMapping("/booking/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Schedule updateAppointment(@PathVariable int id,@RequestBody AppointmentReqDTO req)
    {
        return service.UpdateAppointment(id,req);
    }


    @GetMapping("/booking/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Schedule getAppointment(@PathVariable int id)
    {

        return service.getAppointment(id);
    }



}
