package com.example.demo.repository;

import com.example.demo.dto.PacienteDto;
import com.example.demo.model.Odontologo;
import com.example.demo.model.Paciente;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@ComponentScan
@Repository

     public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    @Query("SELECT p FROM Paciente p WHERE  p.nombre=?1")
    Optional<Paciente> buscarPaciente(String nombre);

    boolean existsByDni(Long dni);
    }


