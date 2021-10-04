package com.backend.proyectofinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="odontologos")
public class Odontologo {

    /* Atributos */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "odontologo_sequence")
    @SequenceGenerator(name= "odontologo_sequence", sequenceName = "odontologo_sequence")
    @Column(name = "id_odontologo")
    private Long id;
    private String nombre;
    private String apellido;
    private Integer matricula;

    @OneToMany(mappedBy = "odontologo", orphanRemoval = true)
    @JsonIgnore
    private Set<Turno> turnos = new HashSet<>();


    /* Cosntructor */
    public Odontologo() {
    }

    public Odontologo(String nombre, String apellido, Integer matricula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;

    }

    /* Getters y Setters */
    public Long getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }


    public Set<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(Set<Turno> turnos) {
        this.turnos = turnos;
    }

    /* Métodos */
    @Override
    public String toString() {
        return "Odontólogo: " + nombre + " " + apellido + "\n" +
                "Matrícula: " + matricula +
                '.';
    }
}

