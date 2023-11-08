package com.example.demo.contollers;


import com.example.demo.Services.TurnoServicesImpl;
import com.example.demo.dto.TurnoDto;
import com.example.demo.exception.DateNotFound;
import com.example.demo.exception.DuplicateExeption;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.NumFoundException;
import com.example.demo.model.Turno;
import com.example.demo.repository.TurnoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private static final Logger logger = LogManager.getLogger(TurnoController.class);

    @Autowired
    TurnoServicesImpl services;
    @Autowired
    TurnoRepository turnoRepository;

    @PostMapping
    public ResponseEntity<TurnoDto> guardar(@RequestBody Turno turno) throws DateNotFound, NotFoundException, DuplicateExeption {
        logger.info("Guardando turno: " + turno.toString());
        TurnoDto turnoDto = services.guardar(turno);
        return ResponseEntity.ok(turnoDto);
    }

    @GetMapping()
    public ResponseEntity<List<TurnoDto> > listar() {
        logger.info("Obteniendo lista de turnos");
     List<TurnoDto>listaTurnoDto=services.listar();

        return ResponseEntity.ok(listaTurnoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDto> obtenerPorId(@PathVariable("id") Long id) throws NumFoundException, NotFoundException {
        logger.debug("Obteniendo turno con ID: " + id);
        TurnoDto turnoDto = services.obtener(id);
        if (turnoDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(turnoDto);
        }
        logger.warn("Turno no encontrado con ID: " + id);
        ResponseEntity<TurnoDto> response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turno> actualizar(@PathVariable Long id, @RequestBody Turno turno) throws NotFoundException, NumFoundException, DateNotFound, DuplicateExeption {
        logger.info("Actualizando turno con ID: " + id);

        if (turnoRepository.existsById(id)) {
            if (turno != null) {
                logger.info("Actualizando turno: " + turno.toString());
                turno.setId(id);
                Turno turnoActualizado = services.actualizar(turno);
                return ResponseEntity.ok(turnoActualizado);
            } else {
                logger.error("El turno es nulo");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } else {
            logger.warn("No se encontró un turno con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @DeleteMapping(path = "{id}")
    public ResponseEntity eliminar(@PathVariable("id") Long id) throws NotFoundException {
        logger.debug("Eliminando turno con ID: " + id);
        services.eliminar(id);
        return ResponseEntity.ok("eliminado");
    }
}
