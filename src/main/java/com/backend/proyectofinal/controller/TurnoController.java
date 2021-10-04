package com.backend.proyectofinal.controller;




import com.backend.proyectofinal.entity.Turno;
import com.backend.proyectofinal.service.OdontologoService;
import com.backend.proyectofinal.service.PacienteService;
import com.backend.proyectofinal.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@CrossOrigin
@RestController
@RequestMapping("/turnos")
public class TurnoController {

    /* Atributos */
    private final TurnoService turnoService;

    private final PacienteService pacienteService;

    private final OdontologoService odontologoService;

    private static final Logger logger = Logger.getLogger(String.valueOf(TurnoController.class));

    /* Constructor */
    @Autowired
    public TurnoController(TurnoService turnoService, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoService = turnoService;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    /* GET */
    @GetMapping("/{id}")
    public Turno findTurnoById(@PathVariable Long id) {
        return turnoService.findById(id).orElse(null);
    }

    @GetMapping
    public List<Turno> findAllTurnos() {
        return turnoService.findAll();
    }

    @GetMapping("/semanales")
    public List<Turno> listTurnosSemanales(){
        logger.info("Listado de los turnos semanales");
        return turnoService.listTurnosSemanales();
    }


    /* POST */
    @PostMapping("/registro")
    public ResponseEntity saveTurno(@RequestBody Turno turno) {
        ResponseEntity response;

        if(odontologoService.findById(turno.getOdontologo().getId()).isEmpty()) {
            response = new ResponseEntity("Odontólogo no encontrado!", HttpStatus.NOT_FOUND);
        } else if (pacienteService.findById(turno.getPaciente().getId()).isEmpty()){
            response = new ResponseEntity("Paciente no encontrado!", HttpStatus.NOT_FOUND);
        }else {
            response = new ResponseEntity(turnoService.save(turno), HttpStatus.OK);
        }
        return response;
    }



    /* PUT */
    @PutMapping("/modificar")
    public ResponseEntity updateTurno(@RequestBody Turno modificacion) {
        ResponseEntity respuesta;

        if(turnoService.findById(modificacion.getId()).isPresent()) {
            respuesta = new ResponseEntity(turnoService.update(modificacion), HttpStatus.OK);
        } else {
            respuesta = new ResponseEntity("Turno no encontrado!", HttpStatus.NOT_FOUND);
        }
        return respuesta;
    }


    /* DELETE */
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity deleteTurno(@PathVariable Long id) {
        ResponseEntity response;

        if(turnoService.findById(id).isPresent()) {
            turnoService.delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Turno eliminado correctamente!");
        } else {
            response = new ResponseEntity("Turno no encontrado!", HttpStatus.NOT_FOUND);
        }
        return response;
    }

}

