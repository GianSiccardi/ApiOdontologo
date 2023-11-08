package com.example.demo.contollers;

import com.example.demo.Services.OdontologoServicesImpl;
import com.example.demo.Services.PacienteServicesImpl;
import com.example.demo.dto.OdontologoDto;
import com.example.demo.dto.PacienteDto;
import com.example.demo.exception.DateNotFound;
import com.example.demo.exception.ExceptionExiste;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.NumFoundException;
import com.example.demo.model.Odontologo;
import com.example.demo.model.Paciente;
import com.example.demo.repository.PacienteRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private static final Logger logger = LogManager.getLogger(PacienteController.class);

    @Autowired
    PacienteServicesImpl pacienteServices;
    @Autowired
    PacienteRepository pacienteRepository;

    @GetMapping
    public List<PacienteDto> listar() {
        logger.info("Obteniendo lista de pacientes");
        return pacienteServices.listar();
    }

    @PostMapping
    public ResponseEntity<PacienteDto> guardar(@RequestBody Paciente paciente) throws NumFoundException, NotFoundException, DateNotFound, ExceptionExiste {
        if (paciente != null) {
            logger.info("Guardando paciente: " + paciente.toString());
            //paciente.setFechaIngreso(paciente.getFechaIngreso());
            PacienteDto pacienteDto = pacienteServices.guardar(paciente);
            return ResponseEntity.ok(pacienteDto);
        }

        logger.error("El paciente es nulo");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?>eliminar(@PathVariable("id") Long id) throws NotFoundException {
        logger.debug("Eliminando paciente con ID: " + id);
        pacienteServices.eliminar(id);
        return ResponseEntity.ok("eliminado");
    }
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> obtenerPorId(@PathVariable("id") Long id) throws NumFoundException, NotFoundException {
        logger.debug("Obteniendo paciente con ID: " + id);
        PacienteDto pacienteDto = pacienteServices.obtenerPorID(id);
        if (pacienteDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(pacienteDto);
        }
        logger.warn("Paciente no encontrado con ID: " + id);
        ResponseEntity<PacienteDto> response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }



    @GetMapping("/pacientes/{nombre}")
    public ResponseEntity<PacienteDto> obtenerPorNombre(@PathVariable("nombre") String nombre) throws NotFoundException {
        logger.debug("Obteniendo paciente con nombre: " + nombre);
        PacienteDto pacienteDto = pacienteServices.buscarPorNombre(nombre);
        if (pacienteDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(pacienteDto);
        }
        logger.warn("Paciente no encontrado con nombre: " + nombre);
        ResponseEntity<PacienteDto> response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(@PathVariable Long id, @RequestBody Paciente paciente) throws NotFoundException, NumFoundException, DateNotFound, ExceptionExiste {
        logger.info("Actualizando paciente con ID: " + id);

        if (pacienteRepository.existsById(id)) {
            if (paciente != null) {
                logger.info("Actualizando paciente: " + paciente.toString());
                paciente.setId(id);
                Paciente pacienteActualizado = pacienteServices.actualizar(paciente);
                return ResponseEntity.ok(pacienteActualizado);
            } else {
                logger.error("El paciente es nulo");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } else {
            logger.warn("No se encontró un paciente con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }





}

