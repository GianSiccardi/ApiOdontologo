package com.example.demo.model;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ODONTOLOGOS")
public class Odontologo {
    @Id
    @SequenceGenerator(name="odontologo_sequencia",sequenceName = "odontologo_sequencia",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "odontologo_sequencia")
    private Long id;
    @Column(unique = true)
    private Long matricula;
    @Column(name = "nombre")
    private String nombre;
    private String apellido;

    public Odontologo(String id) {
        this.id = Long.parseLong(id);
    }


}
