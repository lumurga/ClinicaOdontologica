package com.backend.proyectofinal.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "pacientes")
public class Paciente {

    /* Atributos */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paciente_sequence")
    @SequenceGenerator(name= "paciente_sequence", sequenceName = "paciente_sequence")
    @Column(name = "id_paciente")
    private Long id;
    private String nombre;
    private String apellido;
    private Integer dni;
    private LocalDate fechaIngreso;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "id_domicilio")
    private Domicilio domicilio;


    @OneToMany(mappedBy = "paciente", orphanRemoval = true)
    @JsonIgnore
    private Set<Turno> turnos = new HashSet<>();




    /* Constructor */
    public Paciente() {
    }

    public Paciente(String nombre, String apellido, Integer dni, LocalDate fechaIngreso, Domicilio domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;

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

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Set<Turno> getTurnos() {return turnos;}

    public void setTurnos(Set<Turno> turnos) {this.turnos = turnos;}

    /*Métodos*/
    @Override
    public String toString() {
        return "Paciente: " + nombre + " " + apellido + "\n" +
                "Dni: " + dni + "\n" +
                "FechaAlta: " + fechaIngreso +
                '.';
    }
}

