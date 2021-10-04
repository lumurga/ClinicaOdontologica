package com.backend.proyectofinal.service;



import com.backend.proyectofinal.entity.Domicilio;
import com.backend.proyectofinal.entity.Paciente;
import com.backend.proyectofinal.service.PacienteService;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;



@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PacienteServiceTest {

    @Autowired
    PacienteService pacienteService;
    Paciente pDefault;

    @BeforeEach
    void init(){
        pDefault = new Paciente("Luciana", "Murga", 31356178, LocalDate.now(), new Domicilio("Calle Wallaby", "42", "Sydney", "Sydney"));
    }


    @Test
    @Order(1)
    @DisplayName("Insertar registro -> Paciente")
    void savePaciente(){
        Paciente pac = pacienteService.save(pDefault);
        Assertions.assertNotNull(pac);
        Assertions.assertNotNull(pac.getId());
    }


    @Test
    @Order(2)
    @DisplayName("Búsqueda por ID -> Paciente")
    void findPacienteById() {
        Paciente pac = pacienteService.findById(1L).orElse(null);
        Assertions.assertNotNull(pac);
        Assertions.assertNotNull(pac.getId());
        Assertions.assertTrue(pac instanceof Paciente);
    }

    @Test
    @Order(3)
    @DisplayName("Eliminar registro -> Paciente")
    void deletePaciente() {
        Paciente pac = pacienteService.findById(1L).orElse(null);
        Assertions.assertNotNull(pac);
        pacienteService.delete(pac.getId());
        Paciente resultado = pacienteService.findById(1L).orElse(null);Assertions.assertNull(resultado);
    }

    @Test
    @Order(4)
    @DisplayName("Listar todos -> Paciente")
    public void listAllOdontologosTest( ) {
        Paciente pac2Test = new Paciente("Juan", "Pérez", 30356178, LocalDate.now(), new Domicilio("Calle Siempre viva", "454", "Sydney", "Sydney"));
        pacienteService.save(pDefault);
        pacienteService.save(pac2Test);
        List <Paciente> pacientes = pacienteService.findAll();
        Assert.assertTrue( !pacientes.isEmpty() );
        Assert.assertTrue( pacientes.size() == 2 );
    }

}
