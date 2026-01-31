package org.example.libreraymanagementsystem_byprudwiceo.Model.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ReturnOrBorrowRequest {

    private String bookName;
    private int  bookQuantity;
    private Boolean isReturn;


}
