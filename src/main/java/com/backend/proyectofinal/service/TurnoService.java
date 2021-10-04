package com.backend.proyectofinal.service;




import com.backend.proyectofinal.entity.Turno;
import com.backend.proyectofinal.repository.impl.ITurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import java.time.*;

import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.now;


@Service

public class TurnoService implements IEntidadService<Turno> {
    private ITurnoRepository turnoRepository;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;
    private static final Logger logger = Logger.getLogger(TurnoService.class);

    @Autowired
    public TurnoService(ITurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }


    /* Métodos */
    public Turno save(Turno turno) {
        return turnoRepository.save(turno);
    }

    public Optional<Turno> findById(Long id) {
        return turnoRepository.findById(id);
    }

    public List<Turno> findAll() {
        return turnoRepository.findAll();
    }

    public Turno update(Turno turnoNew) {
        Turno turno = turnoRepository.findById(turnoNew.getId()).get();
        turno.setFechaHora(turnoNew.getFechaHora());

        turno.setOdontologo(turnoNew.getOdontologo());
        turnoNew.setPaciente(turnoNew.getPaciente());
        turnoRepository.save(turno);
        return turno;
    }

    public void delete(Long id) {
        if (turnoRepository.findById(id).isPresent()) {
            turnoRepository.deleteById(id);
            System.out.println("Eliminado con éxito!");
        } else {
            System.out.println("Turno no encontrado!");
        }
    }

    /* Métodos útiles */

    public List<Turno> findTurnosByPacienteDni(Integer dni){
        return turnoRepository.findTurnosByPacienteDni(dni);
    }

    public List<Turno> findTurnosByOdontologoMatricula(String matricula){
        return turnoRepository.findTurnosByOdontologoMatricula(matricula);
    }

    public List<Turno> listTurnosSemanales(){
        LocalDateTime hoy = LocalDateTime.now();
        LocalDateTime unaSemana = hoy.plusDays(7);
        return turnoRepository.turnosSemanales(hoy, unaSemana);
    }


}
