package com.example.demo.Services;

import com.example.demo.exception.ExceptionExiste;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.NumFoundException;
import com.example.demo.model.Paciente;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.example.demo.dto.OdontologoDto;
import com.example.demo.model.Odontologo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.OdontologoRepository;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Service
public class OdontologoServicesImpl {

    private static final Logger logger = LogManager.getLogger(OdontologoServicesImpl.class);
    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private ObjectMapper mapper;




    public OdontologoServicesImpl(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
        this.mapper = new ObjectMapper();
    }




    public OdontologoDto guardar(Odontologo odontologo) throws NotFoundException, NumFoundException, ExceptionExiste {

        String matriculaStr = odontologo.getMatricula().toString();
        if (odontologo.getMatricula() == null ||!matriculaStr.matches("\\d+")||odontologo.getMatricula()<0|| odontologo.getMatricula() > 10000) {
            throw new NumFoundException("La matrícula debe ser un número válido entre 0 y 10000");
        }

        if (odontologoRepository.existsByMatricula(odontologo.getMatricula())) {
            logger.error("Ocurrio un error");
            throw new ExceptionExiste("La matrícula ya existe");
        }
        if (odontologo.getNombre()== null ||odontologo.getNombre().trim().length()<3 ||odontologo.getNombre().isEmpty() || odontologo.getApellido() == null || odontologo.getApellido().trim().length()<3||odontologo.getApellido().isEmpty()) {
            logger.error("Ocurrio un error");
            throw new NotFoundException("El nombre y el apellido no pueden estar vacíos o tienen q ser minimo de 3 caracteres");

        }

            String nombre = StringUtils.capitalize(odontologo.getNombre());
            String apellido = StringUtils.capitalize(odontologo.getApellido());

            odontologo.setNombre(nombre);
            odontologo.setApellido(apellido);

            logger.debug("Se guarda el odontologo con las inciales en Mayuscula");
            odontologoRepository.save(odontologo);


        OdontologoDto odontologoDto = mapper.convertValue(odontologo, OdontologoDto.class);
        logger.info("retorna lo pedido");
        return odontologoDto;
    }


    public OdontologoDto obtenerById(Long id) throws NotFoundException {

        if (id == null || id <= 0) {
            logger.error("Ocurrio un error");
            throw new  NotFoundException ("El ID debe ser un valor válido");
        }

        //para que no falle si esta vacio
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

        //para que no falle si falta una propiedad
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        logger.debug("Esta buscando por id");
        Optional<Odontologo>odontologo=odontologoRepository.findById(id);

        if(odontologo.isPresent()){
            logger.info("retorna el objeto transformado en DTO");
            return mapper.convertValue(odontologo.get(),OdontologoDto.class);
        }else {
            logger.error("Ocurrio un error");
            throw new NotFoundException("NO EXISTE");
        }

    }


    public void eliminar(Long id) throws NotFoundException {
        if (id == null || id <= 0) {
            logger.error("Ocurrio un error");
            throw new NotFoundException("El ID debe ser un valor válido");
        }
        logger.info("se elimino el objeto");
        odontologoRepository.deleteById(id);
    }


    public List<OdontologoDto> listar()  {
        List<Odontologo>lista= odontologoRepository.findAll();

        //para que no falle si esta vacio
        logger.debug("se hacen los mapeos para transformar en dto");

    List<OdontologoDto>listaDto=lista.stream().map(odontologo -> mapper.convertValue(odontologo,OdontologoDto.class)).collect(Collectors.toList());
logger.info("se retorno la lista con los objeto DTO");
    return listaDto;
    }

    public OdontologoDto buscarPorNombre(String nombre) throws NotFoundException {
    if (nombre == null || nombre.trim().isEmpty()) {
        logger.error("Ocurrio un error");
        throw new NotFoundException("El nombre no puede ser nulo o vacío");
    }
        logger.debug("se hacen los mapeos para transformar en dto");
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
    //para que no falle si falta una propiedad
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    Optional<Odontologo>odontologo=odontologoRepository.buscarOdontologo(nombre);

    if(odontologo.isPresent()){
       logger.info("Se encontro el objto por el nombre, se devuelve un dto");
        return mapper.convertValue(odontologo.get(),OdontologoDto.class);
    }else {

        throw new NotFoundException("NO EXISTE");
    }

}
    public Odontologo actualizar(Odontologo odontologo) throws NotFoundException, NumFoundException {
        if (odontologo.getNombre() == null || odontologo.getNombre().trim().length() < 3 || odontologo.getNombre().isEmpty()
                || odontologo.getApellido() == null || odontologo.getApellido().trim().length() < 3
                || odontologo.getApellido().isEmpty()) {
            logger.error("Ocurrió un error");
            throw new NotFoundException("El nombre y el apellido no pueden estar vacíos o deben tener al menos 3 caracteres");

        } else if (odontologo.getMatricula() >= 10000) {
            logger.error("Ocurrió un error");
            throw new NumFoundException("El número de matrícula debe ser menor o igual a 10000");
        }

        Optional<Odontologo> odontologoExistente = odontologoRepository.findById(odontologo.getId());
        if (odontologoExistente.isPresent()) {
            Odontologo odontologoActualizado = odontologoExistente.get();
            odontologoActualizado.setNombre(odontologo.getNombre());
            odontologoActualizado.setApellido(odontologo.getApellido());
            odontologoActualizado.setMatricula(odontologo.getMatricula());

            logger.info("El objeto fue actualizado");
            Odontologo odontologoGuardado = odontologoRepository.save(odontologoActualizado);
            logger.info("retorna lo pedido");
            return odontologoGuardado;
        } else {
            logger.error("Ocurrió un error");
            throw new NotFoundException("El odontólogo no existe");
        }
    }



}

