package com.example.demo.contollers;


import com.example.demo.Services.OdontologoServicesImpl;
import com.example.demo.dto.OdontologoDto;
import com.example.demo.exception.ExceptionExiste;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.NumFoundException;
import com.example.demo.model.Odontologo;
import com.example.demo.repository.OdontologoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
@Autowired
OdontologoServicesImpl services;
@Autowired
    OdontologoRepository odontologoRepository;
    private static final Logger logger = LogManager.getLogger(OdontologoController.class);

   @GetMapping()
   public List<OdontologoDto>listar() throws NotFoundException {
       logger.debug("Se ha recibido una solicitud en /api/endpoint");
       logger.info("Se devuelve la lista con exito");

       return services.listar();

}

   @PostMapping
   public ResponseEntity<OdontologoDto> guardar(@RequestBody Odontologo odontologo) throws NotFoundException, NumFoundException, ExceptionExiste {
       logger.info("Se ha recibido una solicitud en /api/endpoint");
       if(odontologo!=null){
           logger.debug("se verifica que el odntologo exista , se ejecuta el metodo guardar");
     return ResponseEntity.ok(services.guardar(odontologo));
 }
logger.error("ALGO SALIO MAL ");
 return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
   }


   @GetMapping("/{id}")
   public ResponseEntity<OdontologoDto> obtenerPorId(@PathVariable("id") Long id) throws NotFoundException {
       logger.info("Obteniendo odontólogo por ID: " + id);
       OdontologoDto odontologoDto=services.obtenerById(id);
     if(odontologoDto==null){
         logger.warn("No se encontró ningún odontólogo con ID: " + id);
         ResponseEntity<OdontologoDto>response=ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         return response;

     }
       logger.info("Odontólogo encontrado: " + odontologoDto);

       return ResponseEntity.status(HttpStatus.OK).body(odontologoDto);
   }

@DeleteMapping(path = "{id}")
public ResponseEntity eliminar(@PathVariable("id") Long id) throws NotFoundException {
    logger.debug("Eliminando odontólogo con ID: " + id);
       services.eliminar(id);
    logger.info("Odontólogo eliminado con éxito");
      return ResponseEntity.ok("eliminado");

}

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<OdontologoDto> obtenerPorNombre(@PathVariable("nombre") String nombre) throws NotFoundException {
        logger.info("Obteniendo odontólogo por nombre: " + nombre);
       OdontologoDto odontologoDto = services.buscarPorNombre(nombre);
        if (odontologoDto != null) {
            logger.debug("Odontólogo encontrado: " + odontologoDto);
            return ResponseEntity.status(HttpStatus.OK).body(odontologoDto);
        }
        logger.warn("No se encontró ningún odontólogo con nombre: " + nombre);
        ResponseEntity<OdontologoDto> response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Odontologo> actualizar(@PathVariable Long id, @RequestBody Odontologo odontologo) throws NotFoundException, NumFoundException {
        logger.info("Actualizando odontólogo con ID: " + id);

        if (!odontologoRepository.existsById(id)) {
            logger.warn("No se encontró un odontólogo con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (odontologo == null || odontologo.getNombre() == null || odontologo.getApellido() == null || odontologo.getMatricula() == null) {
            logger.error("ocurrio un error.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } else if (odontologo.getMatricula() > 10000||odontologo.getMatricula()<0||odontologo.getMatricula()==null) {
            logger.error("Ocurrió un error. Número de matrícula inválido.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        odontologo.setId(id);
       Odontologo odontologoActualizado= services.actualizar(odontologo);
        logger.info("Odontólogo actualizado.");
        return ResponseEntity.ok(odontologoActualizado);
    }


}



