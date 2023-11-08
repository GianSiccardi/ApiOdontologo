package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDto {

    private Long id;
    private String nombre;
    private String apellido;
    @JsonIgnore
    private String mail;
    private LocalDate fechaIngreso;


}
