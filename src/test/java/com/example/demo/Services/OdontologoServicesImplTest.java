package com.example.demo.Services;

import com.example.demo.dto.OdontologoDto;
import com.example.demo.exception.ExceptionExiste;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.NumFoundException;
import com.example.demo.model.Odontologo;
import com.example.demo.repository.OdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OdontologoServicesImplTest {
    @Autowired
    private OdontologoServicesImpl odontologoServices;
    @Autowired
    private OdontologoRepository odontologoRepository;





    @Test
public void testCrear() throws NumFoundException, NotFoundException, ExceptionExiste {
    Odontologo odontologo= new Odontologo();
    odontologo.setNombre("Juan");
    odontologo.setApellido("Perez");
    odontologo.setMatricula(1000L);
    odontologoServices.guardar(odontologo);

    OdontologoDto odontologoDto=odontologoServices.obtenerById(1L);

    assertTrue(odontologoDto!=null);

}
    @Test
    public void testEliminarOdontologo() throws NotFoundException {
        // Arrange
        Long odontologoId = 1L;

        // Mockear el repositorio
        OdontologoRepository odontologoRepositoryMock = mock(OdontologoRepository.class);

        // Configurar el comportamiento del repositorio mock
        when(odontologoRepositoryMock.existsById(odontologoId)).thenReturn(true);

        // Crear una instancia del servicio con el repositorio mock
        odontologoServices = new OdontologoServicesImpl(odontologoRepositoryMock);

        // Act
        odontologoServices.eliminar(odontologoId);

        // Assert
        verify(odontologoRepositoryMock, times(1)).deleteById(odontologoId);
    }



    @Test
    public void testListarOdontologo() {
        // Arrange
        Odontologo odontologo1 = new Odontologo(1L, 100l, "Juan", "Ramirez");
        Odontologo odontologo2 = new Odontologo(2L, 200l, "Manuel", "Perez");
        List<Odontologo> odontologoList = Arrays.asList(odontologo1, odontologo2);

        OdontologoRepository odontologoRepositoryMock = mock(OdontologoRepository.class);
        when(odontologoRepositoryMock.findAll()).thenReturn(odontologoList);

       odontologoServices = new OdontologoServicesImpl(odontologoRepositoryMock);

        // Act
        List<OdontologoDto> lista = odontologoServices.listar();

        // Assert
        Assertions.assertEquals(2, lista.size());
    }
    @Test
    public void testBuscarPorID() throws NotFoundException {
        // Arrange
        Odontologo odontologo1 = new Odontologo(1L, 100l, "Juan", "Ramirez");
        OdontologoRepository odontologoRepositoryMock = mock(OdontologoRepository.class);
        when(odontologoRepositoryMock.findById(1L)).thenReturn(Optional.of(odontologo1));

         odontologoServices = new OdontologoServicesImpl(odontologoRepositoryMock);

        // Act
        OdontologoDto odontologoDto = odontologoServices.obtenerById(1L);

        // Assert
        Assertions.assertEquals("Juan", odontologoDto.getNombre());
    }












}