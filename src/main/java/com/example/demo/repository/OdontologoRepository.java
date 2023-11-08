package com.example.demo.repository;

import com.example.demo.dto.OdontologoDto;
import com.example.demo.model.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo,Long> {

       @Query("SELECT o FROM Odontologo o WHERE  o.nombre=?1")
    Optional<Odontologo>buscarOdontologo(String nombre);

    boolean existsByMatricula(Long matricula);






}
