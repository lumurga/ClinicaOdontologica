package com.backend.proyectofinal.controller;



import com.backend.proyectofinal.entity.Paciente;
import com.backend.proyectofinal.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    /* Atributos */
    private final PacienteService pacienteService;
    Logger logger = Logger.getLogger(String.valueOf(PacienteController.class));

    /* Constructor */
    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }


    /* GET */
    @GetMapping("/{id}")
    public Paciente findPacienteById(@PathVariable Long id) {

        logger.info("Búsqueda de paciente por el ID: " + id);
        return pacienteService.findById(id).orElse(null);
    }

    @GetMapping()
    public List<Paciente> findAllPacientes() {
        logger.info("Listado de Pacientes");
        return pacienteService.findAll();
    }


    /* POST */
    @PostMapping("/registro")
    public ResponseEntity savePaciente(@RequestBody Paciente paciente) {
        ResponseEntity response;
        if(pacienteService.findPacienteByDni(paciente.getDni()) != null) {
            response = new ResponseEntity("El dni ingresado ya se encuentra registrado!", HttpStatus.CONFLICT);
        } else {
            response = new ResponseEntity(pacienteService.save(paciente), HttpStatus.OK);
        }
        return response;
    }


    /* PUT */
    @PutMapping("/modificar")
    public ResponseEntity updatePaciente(@RequestBody Paciente modificacion) {
        ResponseEntity respuesta;

        if(pacienteService.findById(modificacion.getId()).isPresent()) {
            respuesta = new ResponseEntity(pacienteService.update(modificacion), HttpStatus.OK);
        } else {
            logger.info("Listado de Pacientes");
            respuesta = new ResponseEntity("Paciente no encontrado!", HttpStatus.NOT_FOUND);
        }
        return respuesta;
    }

    /* DELETE */
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity deletePaciente(@PathVariable Long id) {
        ResponseEntity respuesta;

        if(pacienteService.findById(id).isPresent()) {
            pacienteService.delete(id);
            respuesta = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Paciente eliminado correctamente!");
        } else {
            respuesta = new ResponseEntity("Paciente no encontrado!", HttpStatus.NOT_FOUND);
        }
        return respuesta;
    }

}

