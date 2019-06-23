package com.example.spring_task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "Phone number cannot be blank")
    @Size(min = 9, message = "Invalid phone number")
    private String phone;
    @NotBlank(message = "Message cannot be blank")
    @Type(type = "text")
    private String message;
    private LocalDateTime dateTime = LocalDateTime.now();
    //status false rozumiem jako nieprzeczytany
    private boolean status = false;


}
