package com.backend.proyectofinal.service;

import com.backend.proyectofinal.entity.Odontologo;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;



@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class OdontologoServiceTest {


    @Autowired
    OdontologoService odontologoService;
    Odontologo oDefault;


    @BeforeEach
    void init(){
        oDefault = new Odontologo("Patricia", "Damiani", 165824);
    }

    @Test
    @Order(1)
    @DisplayName("Insertar registro -> Odontólogo")
    void saveOdontologo(){
        Odontologo od = odontologoService.save(oDefault);
        Assertions.assertNotNull(od);
        Assertions.assertNotNull(od.getId());
    }

    @Test
    @Order(2)
    @DisplayName("Búsqueda por ID -> Odontólogo")
    void findOdontologoById() {
        Odontologo od = odontologoService.findById(1L).orElse(null);
        Assertions.assertNotNull(od);
        Assertions.assertNotNull(od.getId());
        Assertions.assertTrue(od instanceof Odontologo);
    }

    @Test
    @Order(3)
    @DisplayName("Eliminar registro -> Odontólogo")
    void deleteOdontologo() {
        Odontologo od = odontologoService.findById(1L).orElse(null);
        Assertions.assertNotNull(od);
        odontologoService.delete(od.getId());
        Odontologo resultado = odontologoService.findById(1L).orElse(null);Assertions.assertNull(resultado);
    }


    @Test
    @Order(4)
    @DisplayName("Listar todos -> Odontólogo")
    public void listAllOdontologosTest( ) {

        Odontologo od2Test = new Odontologo("Cristina", "Albornoz", 18346540);
        odontologoService.save(oDefault);
        odontologoService.save(od2Test);
        List<Odontologo> odontologos = odontologoService.findAll();
        Assert.assertTrue( !odontologos.isEmpty() );
        Assert.assertTrue( odontologos.size() == 2 );
    }


}
