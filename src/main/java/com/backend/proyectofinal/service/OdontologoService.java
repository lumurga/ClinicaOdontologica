package com.backend.proyectofinal.service;



import com.backend.proyectofinal.entity.Odontologo;
import com.backend.proyectofinal.repository.impl.IOdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class OdontologoService implements IEntidadService<Odontologo> {

    /* Atributos */
    private IOdontologoRepository odontologoRepository;
    java.util.logging.Logger logger = Logger.getLogger(String.valueOf(OdontologoService.class));

    /* Cosntructor */
    @Autowired
    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    /* Getters y Setters */

    public IOdontologoRepository getOdontologoRepository() {
        return odontologoRepository;
    }

    public void setOdontologoRepository(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    /* Métodos */
    public Odontologo save(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    public Optional<Odontologo> findById(Long id) {
        return odontologoRepository.findById(id);
    }

    public List<Odontologo> findAll() {
        return odontologoRepository.findAll();
    }

    public Odontologo update(Odontologo odontologoNew) {
        Odontologo odon = odontologoRepository.findById(odontologoNew.getId()).get();
        odon.setNombre(odontologoNew.getNombre());
        odon.setApellido(odontologoNew.getApellido());
        odon.setMatricula(odontologoNew.getMatricula());
        odontologoRepository.save(odon);
        logger.info("Ha sido actualizado el registro correspondiente al ID: "+ odon.getId());
        return odon;
    }

    public void delete(Long id) {
        if (odontologoRepository.findById(id).isPresent()) {
            odontologoRepository.deleteById(id);
            logger.info("Registro eliminado de la entidad Odontólogos correctamente");
            System.out.println("Eliminado con éxito!");
        } else {
            logger.info("No ha sido posible eliminar el registro de la entidad Odontólogos: ID no encontrado");
            System.out.println("Odontólogo no encontrado!");
        }
    }

    public Odontologo findOdontologoByMatricula(Integer matricula) {
        logger.info("Búsqueda en la entidad Odontólogos filtrada por DNI");
        return odontologoRepository.findOdontologoByMatricula(matricula);
    }

    public Odontologo findOdontologoByFullname(String nombre, String apellido){
        logger.info("Búsqueda en la entidad Odontologos filtrada por Nombre y Apellido");
        return odontologoRepository.findOdontologoByFullname(nombre,apellido);
    }


}

