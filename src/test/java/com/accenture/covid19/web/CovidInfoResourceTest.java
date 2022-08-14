package com.accenture.covid19.web;

import com.accenture.covid19.Covid19Application;
import com.accenture.covid19.web.errors.ExceptionTranslator;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Covid19Application.class)
public class CovidInfoResourceTest {

    private final static String BASE_API_URI = "/correlation/details";

    @Autowired
    private CovidInfoResource covidInfoResource;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(covidInfoResource)
                .setControllerAdvice(new ExceptionTranslator())
                .build();
    }

    @Test
    public void retrieve_all_countries_correlation_coefficient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_API_URI))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("correlation", Matchers.notNullValue()));
    }

    @Test
    public void retrieve_specific_continent_correlation_coefficient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_API_URI + "/Africa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("correlation", Matchers.notNullValue()));
    }

}
