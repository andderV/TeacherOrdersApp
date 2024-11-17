package ru.andderv.order.TeacherOrdersApp.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.andderv.order.TeacherOrdersApp.services.RegistrationService;
import ru.andderv.order.TeacherOrdersApp.services.TeachersService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author andderV
 * @date 11.07.2024 7:34
 * TeacherOrdersApp
 */
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
    @Mock
    private RegistrationService registrationService;
    @Mock
    private TeachersService teachersService;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void index() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertNotNull(teachersService.findAll());
    }

    @Test
    void edit() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}