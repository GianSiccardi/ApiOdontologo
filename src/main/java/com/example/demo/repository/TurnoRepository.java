package com.example.demo.repository;

import com.example.demo.model.Odontologo;
import com.example.demo.model.Paciente;
import com.example.demo.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TurnoRepository extends JpaRepository<Turno,Long> {

    boolean existsByOdontologoAndFecha(Odontologo odontologo, LocalDate fecha);

    boolean existsByPacienteAndFecha(Paciente paciente, LocalDate fecha);
}
