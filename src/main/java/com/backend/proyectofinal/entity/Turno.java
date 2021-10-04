package com.backend.proyectofinal.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="turnos")
public class Turno {

    /* Atributos */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turno_sequence")
    @SequenceGenerator(name= "turno_sequence", sequenceName = "turno_sequence")
    @Column(name= "id_turno")
    private Long id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime fechaHora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "odontologo_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Odontologo odontologo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Paciente paciente;


    /* Constructor */

    public Turno() {
    }

    public Turno(LocalDateTime fechaHora, Odontologo odontologo, Paciente paciente) {
        this.fechaHora = fechaHora;
        this.odontologo = odontologo;
        this.paciente = paciente;
    }

    /* Getters y Setters */
    public Long getId() {
        return id;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /* Métodos */
    @Override
    public String toString() {
        return "Turno: " +
                "Paciente :" + paciente +
                ", Odontólogo :" + odontologo +
                ", Fecha y Hora :" + fechaHora +
                '.';
    }
}
