package com.countryservice.demo.springbootmockito;

import com.countryservice.demo.springbootmockito.model.Country;
import com.countryservice.demo.springbootmockito.model.controller.CountryController;
import com.countryservice.demo.springbootmockito.services.CountryServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.http.MediaType;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.lang.invoke.MethodHandles.catchException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.restservices.demo")
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = {ControllerMackitoTests.class})
public class ControllerMockMvcTests
{
    @Autowired
    MockMvc mockMvc;

    @Mock
    CountryServices countryServices;

    @InjectMocks
    CountryController countryController;

    List<Country> mycountries;
    Country country;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    @Order(1)
    public  void test_getAllCountries() throws Exception {

        mycountries= new ArrayList<>();
        mycountries.add(new Country(1,"India","Delhi"));
        mycountries.add(new Country(2,"USA","Washington"));
        mycountries.add(new Country(3,"USA","Washington"));

        when(countryServices.getAllCountries()).thenReturn(mycountries);

        this.mockMvc.perform(get("/getcountries"))
                .andExpect(status().isFound())
                .andDo(print());


        verify(countryServices,timeout(10).times(1)).getAllCountries();
        verifyNoMoreInteractions(countryServices);
        assertEquals(3, mycountries.size());
        assertThat(mycountries).doesNotContainNull();
       assertNotNull(mycountries);



    }

    @Test
    @Order(2)
    public void test_getCountrybyID() throws  Exception{
        country = new Country(2,"USA","Washington");
        int countryID=2;

        when(countryServices.getCountrybyID(countryID)).thenReturn(country);

        this.mockMvc.perform(get("/getcountries/{id}",countryID))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("countryName").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington"))
                .andDo(print());

        verify(countryServices,times(1)).getCountrybyID(2);
       // assertThat(mycountries).doesNotContainNull();
        assertNotNull(country);
    }

    @Test
    @Order(3)
    public void test_getCountrybyName() throws  Exception{
        country = new Country(2,"USA","Washington");
        String countryName="USA";

        when(countryServices.getCountrybyName(countryName)).thenReturn(country);

        this.mockMvc.perform(get("/getcountries/countryname").param("name","USA"))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("countryName").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington"))
                .andDo(print());

        verify(countryServices,times(1)).getCountrybyName("USA");
        assertNotNull(country);
    }

    @Test
    @Order(4)
    public void test_addCountry() throws Exception
    {
        country = new Country(3,"Germany","Berlin");



        when(countryServices.addCountry(country)).thenReturn(country);



        ObjectMapper mapper=new ObjectMapper();
        String jsonbody=mapper.writeValueAsString(country);



        this.mockMvc.perform(post("/addcountry")
                        .content(jsonbody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(print());



        assertEquals(country,countryServices.addCountry(country));
        verify(countryServices,times(1)).addCountry(country);
        assertNotNull(country);

    }

    @Test
    @Order(5)
    public void test_updateCountry() throws Exception
    {
        country= new Country(3,"Japan","Tokyo");
        int countryID=3;

        when(countryServices.getCountrybyID(countryID)).thenReturn(country);
        when(countryServices.updateCountry(country)).thenReturn(country);

        ObjectMapper mapper=new ObjectMapper();
        String  jsonbody=mapper.writeValueAsString(country);

        this.mockMvc.perform(put("/updatecountry/{id}",countryID)
                        .content(jsonbody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("countryName").value("Japan"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Tokyo"))
                .andDo(print());


        verify(countryServices,times(1)).updateCountry(country);
        assertNotNull(country);
    }

    @Test
    @Order(6)
    public void test_deleteCountry() throws Exception {
        country = new Country(3,"Germany","Berlin");
        int countryID=3;

        when(countryServices.getCountrybyID(countryID)).thenReturn(country);


        this.mockMvc.perform(delete("/deletecountry/{id}",countryID))
                .andExpect(status().isOk());

        verify(countryServices,times(1)).deleteCountry(country);
        assertNotNull(country);
    }




}
