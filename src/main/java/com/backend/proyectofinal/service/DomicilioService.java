package com.backend.proyectofinal.service;


import com.backend.proyectofinal.entity.Domicilio;
import com.backend.proyectofinal.repository.impl.IDomicilioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class DomicilioService implements IEntidadService<Domicilio> {

    /* Atributos */
    private IDomicilioRepository domicilioRepository;
    java.util.logging.Logger logger = Logger.getLogger(String.valueOf(DomicilioService.class));

    /* Constructor */
    @Autowired
    public DomicilioService(IDomicilioRepository domicilioRepository) {
        this.domicilioRepository = domicilioRepository;
    }

    /* Métodos */
    public Domicilio save(Domicilio domicilio) {
        return domicilioRepository.save(domicilio);
    }

    public Optional<Domicilio> findById(Long id) {
        return domicilioRepository.findById(id);
    }

    public List<Domicilio> findAll() {
        return domicilioRepository.findAll();
    }

    public Domicilio update(Domicilio domicilioNew) {
        Domicilio dom = domicilioRepository.findById(domicilioNew.getId()).get();
        dom.setCalle(domicilioNew.getCalle());
        dom.setNumero(domicilioNew.getNumero());
        dom.setLocalidad(domicilioNew.getLocalidad());
        dom.setProvincia(domicilioNew.getProvincia());
        domicilioRepository.save(dom);
        logger.info("Ha sido actualizado el domicilio correspondiente al ID: "+ dom.getId());
        return dom;
    }

    public void delete(Long id) {
        if(domicilioRepository.findById(id).isPresent()){
            domicilioRepository.deleteById(id);
            logger.info("Registro eliminado de la entidad Domicilios correctamente");
            System.out.println("Eliminado con éxito!");
        } else {
            logger.info("No ha sido posible eliminar el registro de la entidad Domicilios: ID no encontrado");
            System.out.println("Domicilio no encontrado!");
        }
    }
}
