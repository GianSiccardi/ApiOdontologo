package com.example.demo.dto;

import com.example.demo.model.Odontologo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoDto {
    private Long id;
    private PacienteDto paciente;
    private OdontologoDto odontologo;
    private LocalDate fecha;
    private LocalTime hora;

}
