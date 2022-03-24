package com.countryservice.demo.springbootmockito;


import com.countryservice.demo.springbootmockito.model.Country;
import com.countryservice.demo.springbootmockito.model.controller.CountryController;
import com.countryservice.demo.springbootmockito.services.CountryServices;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ControllerMackitoTests.class})
public class ControllerMackitoTests {

    @Mock
    CountryServices countryServices;

    @InjectMocks
    CountryController countryController;

    List<Country> mycountries;
    Country country;

    @Test
    @Order(1)
    public  void test_getAllCountries()
    {
        mycountries=new ArrayList<Country>();
        mycountries.add(new Country(1,"India","Delhi"));
        mycountries.add(new Country(2,"USA","Washington"));

        when(countryServices.getAllCountries()).thenReturn(mycountries);

        ResponseEntity<List<Country>>res=countryController.getCountries();

        assertEquals( HttpStatus.FOUND,res.getStatusCode());
        assertEquals(2,res.getBody().size());

        verify(countryServices,times(1)).getAllCountries();
        verify(countryServices, timeout(100)).getAllCountries();
        assertNotNull(mycountries);
    }

    @Test
    @Order(2)
    public void test_getCountrybyID()
    {
        country= new Country(2,"USA","Washington");
        int countryID=2;

        when(countryServices.getCountrybyID(countryID)).thenReturn(country);
        ResponseEntity<Country> res=countryController.getCountryById(countryID);

        assertEquals( HttpStatus.FOUND,res.getStatusCode());
        assertEquals(countryID,res.getBody().getId());

        verify(countryServices,times(1)).getCountrybyID(2);
        assertNotNull(country);
    }

    @Test
    @Order(3)
    public void test_getCountrybyName()
    {
        country= new Country(2,"USA","Washington");
       String countryName="USA";

        when(countryServices.getCountrybyName(countryName)).thenReturn(country);
        ResponseEntity<Country> res=countryController.getCountryByName(countryName);

        assertEquals( HttpStatus.FOUND,res.getStatusCode());
        assertEquals(countryName,res.getBody().getCountryName());

        verify(countryServices,times(1)).getCountrybyName("USA");
        assertNotNull(country);
    }

    @Test
    @Order(4)
    public void test_addCountry()
    {
        country= new Country(3,"Germany","Berlin");

        when(countryServices.addCountry(country)).thenReturn(country);
        ResponseEntity<Country> res=countryController.addCountry(country);

        assertEquals( HttpStatus.CREATED,res.getStatusCode());
        assertEquals(country,res.getBody());


        verify(countryServices,times(1)).addCountry(country);
        assertNotNull(country);

    }

    @Test
    @Order(5)
    public void test_updateCountry()
    {
        country= new Country(3,"Japan","Tokyo");
        int countryID=3;

        when(countryServices.getCountrybyID(countryID)).thenReturn(country);
        when(countryServices.updateCountry(country)).thenReturn(country);

        ResponseEntity<Country> res=countryController.updateCountry(countryID,country);

        assertEquals( HttpStatus.OK,res.getStatusCode());
        assertEquals(3,res.getBody().getId());
        assertEquals("Japan",res.getBody().getCountryName());
        assertEquals("Tokyo",res.getBody().getCountryCapital());

        verify(countryServices,times(1)).updateCountry(country);
        assertNotNull(country);
    }

    @Test
    @Order(6)
    public void test_deleteCountry()
    {
        country= new Country(4,"Japan","Tokyo");
        int countryID=4;

        when(countryServices.getCountrybyID(countryID)).thenReturn(country);
        ResponseEntity<Country> res=countryController.deleteCountry(countryID);

        assertEquals( HttpStatus.OK,res.getStatusCode());
        assertEquals(4,res.getBody().getId());


        verify(countryServices,times(1)).deleteCountry(country);
        assertNotNull(country);
    }






}
