package com.example.demo.Services;
import com.example.demo.dto.PacienteDto;
import com.example.demo.exception.DateNotFound;
import com.example.demo.exception.ExceptionExiste;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.NumFoundException;
import com.example.demo.model.Paciente;
import com.example.demo.repository.PacienteRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class PacienteServicesImpl{

    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    ObjectMapper mapper;
    private static final Logger logger = LogManager.getLogger(PacienteServicesImpl.class);

    public PacienteDto guardar(Paciente paciente) throws NumFoundException, NotFoundException, DateNotFound, ExceptionExiste {
        String dniStr = String.valueOf(paciente.getDni());

        if (paciente.getDni() == null || paciente.getDni() <= 0 || dniStr.length() != 8 || !dniStr.matches("\\d+")) {
            logger.error("Ocurrió un error por el tamaño del dni");
            throw new NumFoundException("El DNI no puede estar vacío, debe ser mayor a 0, tener 8 dígitos y no puede contener letras");
        }

        if (paciente.getNombre().trim().length() < 3 || paciente.getNombre().trim().isEmpty() ||
                paciente.getApellido().trim().length() < 3 || paciente.getApellido().trim().isEmpty() ||
                paciente.getMail() == null || paciente.getMail().trim().isEmpty()) {
            logger.error("Ocurrió un error por el nombre,apellido o mail");
            throw new NotFoundException("El nombre, el apellido y el correo no pueden estar vacíos y deben tener mínimo 3 caracteres");
        } else if (pacienteRepository.existsByDni(paciente.getDni())) {
            logger.error("Ocurrió un error , el dni ya existe");
            throw new ExceptionExiste("El DNI ya existe");
        }

        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaPaciente = paciente.getFechaIngreso();
        if (fechaPaciente != null && fechaPaciente.isBefore(fechaActual.minusDays(1))) {
            logger.error("Ocurrió un error por la fecha ");
            throw new DateNotFound("La fecha no puede ser del día de ayer");
        }

        String nombre = StringUtils.capitalize(paciente.getNombre());
        String apellido = StringUtils.capitalize(paciente.getApellido());

        paciente.setNombre(nombre);
        paciente.setApellido(apellido);

        Paciente pacienteEntidad = pacienteRepository.save(paciente);
        logger.info("Se guardó el objeto en DTO");
        return mapper.convertValue(pacienteEntidad, PacienteDto.class);
    }





    public PacienteDto obtenerPorID(Long id) throws NumFoundException, NotFoundException {
        if (id == null || id <= 0) {
            logger.error("Ocurrio un error");
            throw new NumFoundException("El ID debe ser un valor válido");
        }
        logger.debug("se hacen los mapeos para transformar en dto");
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

        //para que no falle si falta una propiedad
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        Optional<Paciente> paciente=pacienteRepository.findById(id);

        if(paciente.isPresent()){
            logger.info("se guardo el objeto en DTO");
            return mapper.convertValue(paciente.get(), PacienteDto.class);
        }else {

            throw new NotFoundException("NO EXISTE");
        }
    }
    public Paciente actualizar(Paciente paciente) throws NotFoundException, NumFoundException, DateNotFound, ExceptionExiste {
        String dniStr = paciente.getDni().toString();
        String dniString = String.valueOf(paciente.getDni());

        if (paciente.getDni() == null || paciente.getDni().equals("") || paciente.getDni() < 0 || dniString.length() != 8 || !dniStr.matches("\\d+")) {
            logger.error("Ocurrió un error");
            throw new NumFoundException("El DNI no puede estar vacío, tiene que ser mayor 0, tener 8 dígitos y no puede contener letras");
        }

        if (paciente.getNombre().trim().length()<3 || paciente.getNombre().trim().isEmpty() ||
                paciente.getApellido() .trim().length()<3|| paciente.getApellido().trim().isEmpty()
                ) {
            logger.error("Ocurrió un error");
            throw new NotFoundException("El nombre, el apellido y el correo no pueden estar vacíos o tener menos de 3 caracteres");
        }

        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaPaciente = paciente.getFechaIngreso();
        if (fechaPaciente.isBefore(fechaActual.minusDays(1))) {
            throw new DateNotFound("La fecha no puede ser del día de ayer");
        }

        Optional<Paciente> pacienteExistente = pacienteRepository.findById(paciente.getId());
        if (pacienteExistente.isPresent()) {
            Paciente pacienteActualizado = pacienteExistente.get();
            if (!pacienteActualizado.getDni().equals(paciente.getDni())) {
                // Si el DNI ha sido modificado, verificar si ya existe en la base de datos
                if (pacienteRepository.existsByDni(paciente.getDni())) {
                    logger.error("Ocurrió un error");
                    throw new ExceptionExiste("El DNI ya existe");
                }
            }

            pacienteActualizado.setNombre(paciente.getNombre());
            pacienteActualizado.setApellido(paciente.getApellido());
            pacienteActualizado.setDni(paciente.getDni());
            pacienteActualizado.setMail(paciente.getMail());
            pacienteActualizado.setDomicilio(paciente.getDomicilio());
            logger.info("El objeto fue actualizado");
          Paciente pacienteGuardado = pacienteRepository.save(pacienteActualizado);
            logger.info("retorna lo pedido");
            return pacienteGuardado;
        } else {
            logger.error("Ocurrió un error");
            throw new NotFoundException("El paciente no existe");
        }
    }


    public void eliminar(Long id) throws NotFoundException {
        if (id == null || id <= 0) {
            logger.error("Ocurrio un error");
            throw new NotFoundException("El ID debe ser un valor válido");
        }
        logger.info("se elimino el objeto");
        pacienteRepository.deleteById(id);
    }


    public List<PacienteDto> listar() {
         List<Paciente>pacientes=pacienteRepository.findAll();
        logger.debug("se hacen los mapeos para transformar en dto");
         List<PacienteDto>pacienteDtos=pacientes
                 .stream()
                 .map(paciente->mapper.convertValue(paciente,PacienteDto.class))
                 .collect(Collectors.toList());
        logger.info("devuelve la lsita con objetos en DTO");
         return pacienteDtos;
    }

    public PacienteDto buscarPorNombre(String nombre) throws NotFoundException {

        if (nombre == null || nombre.trim().isEmpty()) {
            logger.error("Ocurrio un error");
            throw new NotFoundException("El nombre no puede ser nulo o vacío");
        }
        logger.debug("se hacen los mapeos para transformar en dto");
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

        //para que no falle si falta una propiedad
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        Optional<Paciente>paciente=pacienteRepository.buscarPaciente(nombre);

        if(paciente.isPresent()){
            logger.info("se encontro el paciente y se devuelve en DTO");
            return mapper.convertValue(paciente.get(),PacienteDto.class);
        }else {
            logger.error("Ocurrio un error");
            throw new NotFoundException  ("NO EXISTE EL OBJETO DTO");
        }

    }
}
