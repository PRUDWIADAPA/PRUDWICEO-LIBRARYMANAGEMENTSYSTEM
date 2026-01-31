package org.example.libreraymanagementsystem_byprudwiceo.Model.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class BorrowOrReturnResponse {

    private String bookName;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String msg;
   // private boolean IsReturn;
}
