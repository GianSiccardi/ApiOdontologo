package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   @ManyToOne
   @JoinColumn(name="paciente_id",nullable = false)
    private Paciente paciente;
   @ManyToOne
   @JoinColumn(name="odontologo_id",nullable = false)
    private Odontologo odontologo;
    private LocalDate fecha;
    private LocalTime hora;
}
