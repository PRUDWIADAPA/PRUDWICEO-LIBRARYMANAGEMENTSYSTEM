package org.example.libreraymanagementsystem_byprudwiceo.Service;

import org.example.libreraymanagementsystem_byprudwiceo.Exception.AppointmentException;
import org.example.libreraymanagementsystem_byprudwiceo.Exception.BookException;
import org.example.libreraymanagementsystem_byprudwiceo.Model.Assistant;
import org.example.libreraymanagementsystem_byprudwiceo.Model.Books;
import org.example.libreraymanagementsystem_byprudwiceo.Model.DTO.AppointmentReqDTO;
import org.example.libreraymanagementsystem_byprudwiceo.Model.DTO.BorrowOrReturnResponse;
import org.example.libreraymanagementsystem_byprudwiceo.Model.DTO.ReturnOrBorrowRequest;
import org.example.libreraymanagementsystem_byprudwiceo.Model.Schedule;
import org.example.libreraymanagementsystem_byprudwiceo.Repo.AssistantRepo;
import org.example.libreraymanagementsystem_byprudwiceo.Repo.BookRepo;
import org.example.libreraymanagementsystem_byprudwiceo.Repo.ScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private BookRepo repo;

    @Autowired
    private AssistantRepo AsitRepo;


    @Autowired
    private ScheduleRepo Schedulerepo;

    public BorrowOrReturnResponse borrowOrReturnBook(ReturnOrBorrowRequest bookRequest) {

        BorrowOrReturnResponse response=new BorrowOrReturnResponse();

        Optional<Books> b=repo.findByName(bookRequest.getBookName());

        if(b.isEmpty())
        {
            throw  new BookException("Book Not found Please try again");
        }

       Books currentBook=b.get();
        response.setBookName(currentBook.getName());

        if(bookRequest.getIsReturn())
        {
            currentBook.setQuantity(currentBook.getQuantity()+bookRequest.getBookQuantity());

            response.setReturnDate(LocalDate.now());
            response.setMsg("Thanks for returning Book "+currentBook.getName()+"  "+"on "+LocalDate.now()+" ."+"Please drop your valuable feedBack");

        }

        else {
            if(currentBook.getQuantity()<bookRequest.getBookQuantity())
            {
                throw  new BookException("Book is out stock.Please wait few days");
            }
            response.setDueDate(LocalDate.now().plusDays(21));
            response.setMsg("Thank you for Taking the book"+"  "+currentBook.getName()+"  "+"Please make sure you return or extend on or before"+"  "+LocalDate.now().plusDays(21));
            currentBook.setQuantity(currentBook.getQuantity() - bookRequest.getBookQuantity());
        }


        repo.save(b.get());
        return response;

    }


    public List<LocalDateTime> getAvailSlots(int id)
    {
        List<LocalDateTime> availableSlots= AsitRepo.findById(id).get().getAvailability();

        return availableSlots;

    }

    public Schedule scheduleAppointment(int id,LocalDateTime time)
    {
       Optional<Assistant> assistant1=AsitRepo.findById(id);

       if(assistant1.isEmpty())
       {
           throw new AppointmentException("Assistant with id "+ id + "Not found");
       }

        Assistant assistant=assistant1.get();


        List<LocalDateTime> availableSlots= assistant.getAvailability();


        if(availableSlots.contains(time))
        {
            availableSlots.remove(time);


            assistant.setAvailability(availableSlots);
            AsitRepo.save(assistant);

            Schedule schedule= new Schedule();
            schedule.setStartTime(time);
            schedule.setEndTime(time.plusMinutes(30));
            schedule.setConfirmationStatus(Schedule.Confirmation.Confirmed);
            schedule.setEmpId(id);

            return Schedulerepo.save(schedule);
        }
        else
            throw new RuntimeException("Please select Available Time Slot");
    }


    public void cancelAppointment(int id)
    {
        Schedule schedule=Schedulerepo.findById(id).get();

        Assistant assistant=AsitRepo.findById(id).get();

        LocalDateTime appointment=schedule.getStartTime();

        assistant.getAvailability().add(appointment);
        AsitRepo.save(assistant);

        Schedulerepo.delete(schedule);

    }

    public Schedule UpdateAppointment(int id, AppointmentReqDTO req) {

        Optional<Schedule> schedule=Schedulerepo.findById(id);

        if(schedule.isEmpty())
        {
            throw new AppointmentException("Please enter correct Appointment Id");
        }

        Schedule schedule1=schedule.get();

        Assistant assistant=AsitRepo.findById(schedule1.getEmpId()).get();

        assistant.getAvailability().add(schedule1.getStartTime());

        AsitRepo.save(assistant);

        return scheduleAppointment(req.getId(),req.getRequestedTime());
    }


    public Schedule getAppointment(int id) {

        Optional<Schedule> schedule=Schedulerepo.findById(id);

        if(schedule.isEmpty())
        {
            throw new AppointmentException("Please enter correct Appointment Id");
        }

        return schedule.get();
    }
}
