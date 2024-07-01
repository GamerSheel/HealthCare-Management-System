package org.example.healthcare_management_system.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;


@Entity @NoArgsConstructor  @Data @AllArgsConstructor
//@Data automatically generates below annotations
//@Getter @Setter @ToString
public class User{
    @Id
    private String email;

    private String firstName;
    private String lastName;
    private Integer role;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;

    private String password;
    private String gender;
    private String mobileNumber;
    private String address;
    private String authority;
}
