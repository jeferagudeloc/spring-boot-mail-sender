package spring.api.asyn.email.rest.controller;

import lombok.var;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import spring.api.asyn.email.rest.model.EmailBody;
import org.springframework.data.util.Pair;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class EmailControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void sendEmail() throws Exception {
        final var END_POINT = "/email/send";

        final var content = "Hola!! acabas de recibir un email automatizado";
        final var email = "xxxxxx@gmail.com";
        final var subject = "Correo de test";

        final var resContent = Pair.of("$.content", content);
        final var resEmail = Pair.of("$.email", email);
        final var resSubject = Pair.of("$.subject", subject);

        final var bodyContent = "{\"email\":\"" + email + "\", \"content\":\"" + content + "\", \"subject\":\"" + subject + "\"}";

        final var mvcResult = this.mockMvc.perform(
                post(END_POINT)
                        .content(bodyContent)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(request().asyncStarted())
                .andExpect(request().asyncResult(instanceOf(Boolean.class)))
                .andReturn();

        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

    }
}