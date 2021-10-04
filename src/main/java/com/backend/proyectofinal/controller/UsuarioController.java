package com.backend.proyectofinal.controller;



import com.backend.proyectofinal.entity.Usuario;
import com.backend.proyectofinal.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    /* Atributos */
    private UsuarioService usuarioService;
    Logger logger = Logger.getLogger(String.valueOf(UsuarioController.class));


    /* Constructor */
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    /* GET */
    @GetMapping("/")
    public String home() {
        return "Bienvenidos al sistema";
    }

    @GetMapping("/user")
    public String user() {
        return "Hola User";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hola Admin";
    }

    @GetMapping("/{id}")
    public Usuario findUsuarioById(@PathVariable Long id) {
        logger.info("Búsqueda de usuario por el ID: " + id);
        return usuarioService.findUsuarioById(id).orElse(null);
    }

    @GetMapping
    public List<Usuario> findAllUsuarios() {
        logger.info("Listado de Usuarios");
        return usuarioService.findAllUsuarios();
    }

   @GetMapping("/{nombreUsuario}")
    public ResponseEntity findUsuarioByName(@PathVariable String nombreUsuario){

        ResponseEntity response = null;
        Optional<Usuario> usuario = usuarioService.findUsuarioByName(nombreUsuario);

        if(usuario == null){
            response = new ResponseEntity("El nombre de usuario no se encontró", HttpStatus.NOT_FOUND);
            logger.info("Búsqueda de campos en la entidad Usuarios fallida: Usuario inexistente(NOT_FOUND)");
        }else{
            logger.info("Búsqueda en la entidad Usuarios filtrada por el usuario: " + nombreUsuario);
            response = new ResponseEntity("Usuario encontrado", HttpStatus.OK);
        }

        return response;
    }


    /* PUT */
    @PutMapping("/modificar")
    public ResponseEntity updateUsuario(@RequestBody Usuario modificacion) {
        ResponseEntity respuesta;

        if(usuarioService.findUsuarioById(modificacion.getId()).isPresent()) {
            respuesta = new ResponseEntity(usuarioService.update(modificacion), HttpStatus.OK);
        } else {
            respuesta = new ResponseEntity("Usuario no encontrado!", HttpStatus.NOT_FOUND);
        }
        return respuesta;
    }





}




