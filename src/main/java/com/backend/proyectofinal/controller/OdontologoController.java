package com.backend.proyectofinal.controller;



import com.backend.proyectofinal.entity.Odontologo;
import com.backend.proyectofinal.service.OdontologoService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@CrossOrigin
@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    /* Atributos */
    private final OdontologoService odontologoService;
    Logger logger = Logger.getLogger(String.valueOf(OdontologoController.class));

    /* Constructor */
    @Autowired
    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }


    /* GET */
    @GetMapping("/{id}")
    public Odontologo findOdontologoById(@PathVariable Long id) {
        logger.info("Búsqueda de odontólogo por el ID: " + id);
        return odontologoService.findById(id).orElse(null);
    }

    @GetMapping
    public List<Odontologo> findAllOdontologos() {
        logger.info("Listado de Odontólogos");
        return odontologoService.findAll();
    }


    /* POST */
    @PostMapping("/registro")
    public ResponseEntity saveOdontologo(@RequestBody Odontologo odontologo){
        ResponseEntity response;
        if(odontologoService.findOdontologoByMatricula(odontologo.getMatricula()) != null) {
            response = new ResponseEntity("La matrícula ingresada ya existe!", HttpStatus.CONFLICT);
        } else {
            response = new ResponseEntity(odontologoService.save(odontologo), HttpStatus.OK);
        }
        return response;
    }



    /* PUT */
    @PutMapping("/modificar")
    public ResponseEntity updateOdontologo(@RequestBody Odontologo odontologo) {
        ResponseEntity response;
        if(odontologoService.findById(odontologo.getId()).isPresent()) {
            response = new ResponseEntity(odontologoService.update(odontologo), HttpStatus.OK);
        } else {
            logger.info("Listado de Odontólogos");
            response = new ResponseEntity("Odontólogo no encontrado!", HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /* DELETE */
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity deleteOdontologo(@PathVariable Long id) {
        ResponseEntity response;

        if(odontologoService.findById(id).isPresent()) {
            odontologoService.delete(id);
            response= ResponseEntity.status(HttpStatus.NO_CONTENT).body("Odontólogo eliminado correctamente!");
        } else {
            response = new ResponseEntity("Odóntologo no encontrado!", HttpStatus.NOT_FOUND);
        }
        return response;
    }

}

