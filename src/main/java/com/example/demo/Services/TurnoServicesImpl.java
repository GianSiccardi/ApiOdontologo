package com.example.demo.Services;

import com.example.demo.dto.TurnoDto;
import com.example.demo.exception.DateNotFound;
import com.example.demo.exception.DuplicateExeption;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.NumFoundException;
import com.example.demo.model.Turno;
import com.example.demo.repository.TurnoRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class TurnoServicesImpl {
@Autowired
    TurnoRepository turnoRepository;
@Autowired
    ObjectMapper mapper;
    private static final Logger logger = LogManager.getLogger(TurnoServicesImpl.class);




    public TurnoDto guardar(Turno turno) throws NotFoundException, DateNotFound, DuplicateExeption {
    LocalDate fechaTurno = turno.getFecha();
    LocalTime horaTurno = LocalTime.from(turno.getHora());
    LocalDateTime fechaHoraTurno = LocalDateTime.of(fechaTurno, horaTurno);
    LocalDateTime fechaHoraInicio = LocalDateTime.of(fechaTurno, LocalTime.of(8, 0));
    LocalDateTime fechaHoraFin = LocalDateTime.of(fechaTurno, LocalTime.of(17, 0));
    LocalDate fechaActual = LocalDate.now();
    DayOfWeek diaSemana = fechaTurno.getDayOfWeek();
        if(turno.getPaciente()==null||turno.getOdontologo()==null){
    logger.error("Ocurrio un error");
    throw new NotFoundException("El campoo de paciente o odontologo no puede estar vacio");
} else if (turno.getFecha()==null||turno.getHora()==null||turno.getFecha().isBefore(fechaActual)||diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
    logger.error("Ocurrio un error");
    throw new DateNotFound("la fecha no existe o revisa si nos es un dia pasado o fin de semana ");
}else if (fechaHoraTurno.isBefore(fechaHoraInicio) || fechaHoraTurno.isAfter(fechaHoraFin)) {
            throw new DateNotFound("El turno debe estar dentro del rango de las 8:00 am y las 17:00 pm");
        }else if(turnoRepository.existsByOdontologoAndFecha(turno.getOdontologo(), fechaTurno)) {
            throw new DuplicateExeption ("Ya existe un turno para el odontólogo en la misma fecha");
        }else if  (turnoRepository.existsByPacienteAndFecha(turno.getPaciente(), fechaTurno)) {
            throw new DuplicateExeption ("Ya existe un turno para el paciente en la misma fecha");
        }

        Turno turnoEntidad=turnoRepository.save(turno);
        logger.info("se guardo el objeto en DTO");

     return mapper.convertValue(turnoEntidad,TurnoDto.class);



    }


    public TurnoDto obtener(Long id) throws NumFoundException, NotFoundException {
        if (id == null || id <= 0) {
            logger.error("Ocurrio un error");
            throw new NumFoundException("El ID debe ser un valor válido");
        }
        logger.debug("se hacen los mapeos para transformar en dto");
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

        //para que no falle si falta una propiedad
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        Optional<Turno> turno=turnoRepository.findById(id);

        if(turno.isPresent()){
            logger.info("se retorno el TURNO DTO");
            return mapper.convertValue(turno.get(), TurnoDto.class);
        }else {
            logger.error("Ocurrio un error");
            throw new NotFoundException("NO EXISTE");
        }
    }


    public Turno actualizar(Turno turno) throws NotFoundException, NumFoundException, DateNotFound, DuplicateExeption {

        LocalDate fechaTurno = turno.getFecha();
        LocalTime horaTurno = LocalTime.from(turno.getHora());
        LocalDateTime fechaHoraTurno = LocalDateTime.of(fechaTurno, horaTurno);
        LocalDateTime fechaHoraInicio = LocalDateTime.of(fechaTurno, LocalTime.of(8, 0));
        LocalDateTime fechaHoraFin = LocalDateTime.of(fechaTurno, LocalTime.of(17, 0));
        LocalDate fechaActual = LocalDate.now();
        DayOfWeek diaSemana = fechaTurno.getDayOfWeek();
        if (turno == null || turno.getId() == null) {
            logger.error("Ocurrió un error");
            throw new NotFoundException("El turno no existe o faltan completar campos");
        }

        if (turno.getPaciente() == null || turno.getOdontologo() == null) {
            logger.error("Ocurrio un error");
            throw new NotFoundException("El campo de paciente u odontólogo no puede estar vacío");
        }
        else if (turno.getFecha()==null||turno.getHora()==null||fechaTurno.isBefore(fechaActual)||diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
            logger.error("Ocurrio un error");
            throw new DateNotFound("la fecha no existe o revisa si nos es un dia pasado o fin de semana ");
        }else if (fechaHoraTurno.isBefore(fechaHoraInicio) || fechaHoraTurno.isAfter(fechaHoraFin)) {
            logger.error("Ocurrio un error");
            throw new DateNotFound("El turno debe estar dentro del rango de las 8:00 am y las 17:00 pm");
        }


        Optional<Turno> turnoExistente = turnoRepository.findById(turno.getId());
        if (turnoExistente.isPresent()) {
            Turno turnoActualizado = turnoExistente.get();
            turnoActualizado.setPaciente(turno.getPaciente());
            turnoActualizado.setOdontologo(turno.getOdontologo());
            turnoActualizado.setFecha(turno.getFecha());
            turnoActualizado.setHora(turno.getHora());

            logger.info("El objeto fue actualizado");
            return turnoRepository.save(turnoActualizado);
        } else {
            logger.error("Ocurrió un error");
            throw new NotFoundException("El odontólogo no existe");
        }
    }


    public void eliminar(Long id) throws NotFoundException {
        if (id == null || id <= 0) {
            logger.error("Ocurrio un error");
            throw new NotFoundException("El ID debe ser un valor válido");
        }
        turnoRepository.deleteById(id);
    }


    public List<TurnoDto> listar() {


        List<Turno>turnos=turnoRepository.findAll();
        logger.debug("se hacen los mapeos para transformar en dto");
        List<TurnoDto>turnosDtos=turnos
                .stream()
                .map(turno->mapper.convertValue(turno,TurnoDto.class))
                .collect(Collectors.toList());
        logger.info("se retorno la lista con los objeto DTO");


        return turnosDtos;
    }
}
