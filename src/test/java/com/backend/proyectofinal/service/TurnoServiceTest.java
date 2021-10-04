package com.backend.proyectofinal.service;




import com.backend.proyectofinal.entity.Domicilio;
import com.backend.proyectofinal.entity.Odontologo;
import com.backend.proyectofinal.entity.Paciente;
import com.backend.proyectofinal.entity.Turno;
import com.backend.proyectofinal.service.OdontologoService;
import com.backend.proyectofinal.service.PacienteService;
import com.backend.proyectofinal.service.TurnoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TurnoServiceTest {

    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private TurnoService turnoService;

    Domicilio dDefault;
    Paciente pDefault;
    Odontologo oDefault;
    Turno tDefault;

    @BeforeEach
    void init(){
        dDefault = new Domicilio("Wallaby", "42", "Sydney", "Sydney");
        oDefault = new Odontologo("Cristina", "Albornoz", 18346540);
        pDefault = new Paciente("Luciana", "Murga", 31356178, LocalDate.now(), dDefault);
        tDefault = new Turno(LocalDateTime.now(), oDefault,pDefault);
    }

    @Test
    @Order(1)
    @DisplayName("Insertar registro -> Turno")
    void saveTurno(){
        Odontologo od = odontologoService.save(oDefault);
        Paciente pac = pacienteService.save(pDefault);
        Turno tmp = new Turno(LocalDateTime.now(), oDefault,pDefault);
        Turno tur = turnoService.save(tmp);
        Assertions.assertNotNull(tur);
        Assertions.assertNotNull(tur.getId());
        Assertions.assertEquals(tur.getPaciente(), pDefault);
        Assertions.assertEquals(tur.getOdontologo(), oDefault);
    }



    @Test
    @Order(2)
    @DisplayName("Búsqueda por ID -> Turno")
    void findTurnoById() {
        Turno tur = turnoService.findById(1L).orElse(null);
        Assertions.assertNotNull(tur);
        Assertions.assertNotNull(tur.getId());
        Assertions.assertTrue(tur instanceof Turno);
    }


    @Test
    @Order(3)
    @DisplayName("Eliminar registro -> Turno")
    void deleteTurno() {
        Turno tur = turnoService.findById(1L).orElse(null);
        Assertions.assertNotNull(tur);
        pacienteService.delete(tur.getId());
        Turno resultado = turnoService.findById(1L).orElse(null);Assertions.assertNull(resultado);
    }

}
