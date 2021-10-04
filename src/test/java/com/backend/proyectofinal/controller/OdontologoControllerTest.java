package com.backend.proyectofinal.controller;

import com.backend.proyectofinal.entity.Odontologo;
import com.backend.proyectofinal.service.OdontologoService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OdontologoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OdontologoService odontologoService;


    Odontologo oDefault;

    @BeforeEach
    void init(){

        oDefault = new Odontologo("Luz", "Gómez", 54368);
    }

    @Test
    @Order(1)
    @DisplayName("Insertar registro mediante API en la tabla Odontólogo")
    void saveOdontologo(){

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/odontologos/registro")
                            .content(asJsonString(oDefault))
                            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(200))
                    .andExpect(content().contentType("application/json"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(2)
    @DisplayName("Búsqueda por ID mediante API en la tabla Odontólogo")
    void findOdontologoById() {

        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/odontologos/{id}", 1))
                    .andDo(print())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(status().is(200))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.matricula").value(oDefault.getMatricula()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @Order(3)
    @DisplayName("Listar mediante API todos los registros de la tabla Odóntologo")
    public void listAllOdontologos() throws Exception {
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/odontologos")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());

    }





    /* Métodos */

    public static String asJsonString(Object object){
        try{
            ObjectMapper objectMapper = getObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            return objectMapper.writeValueAsString(object);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper().registerModule(new ParameterNamesModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    }

}