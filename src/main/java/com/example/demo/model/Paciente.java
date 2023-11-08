package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.*;
import java.time.LocalDate;

@ComponentScan
@Entity
@Table(name="Pacientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paciente  {

    @Id
    @SequenceGenerator(name="paciente_sequencia",sequenceName = "paciente_sequencia",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "paciente_sequencia")
    private Long id;
    private String nombre;
    private String apellido;
    @Column(unique = true)
    private Long dni;
    private String mail;
    private LocalDate fechaIngreso;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="domiclio_id")
    private Domicilio domicilio;

    public Long getDni() {
        return dni;
    }

    public Paciente(String id) {
        this.id = Long.parseLong(id);
    }
}
