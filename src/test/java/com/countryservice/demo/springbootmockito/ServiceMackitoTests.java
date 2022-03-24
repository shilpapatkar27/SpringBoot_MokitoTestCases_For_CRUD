package com.countryservice.demo.springbootmockito;


import com.countryservice.demo.springbootmockito.model.Country;
import com.countryservice.demo.springbootmockito.repository.CountryRepository;
import com.countryservice.demo.springbootmockito.services.CountryServices;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ServiceMackitoTests.class})
public class ServiceMackitoTests {

    @Mock
    CountryRepository countryRepository;

    @InjectMocks
    CountryServices countryServices;

    //public List<Country> mycountries;

    @Test
    @Order(1)
    public void test_getAllCountries()
    {
        List<Country> mycountries=new ArrayList<>();
        mycountries.add(new Country(1,"India","Delhi"));
        mycountries.add(new Country(2,"USA","Washington"));

        when(countryRepository.findAll()).thenReturn(mycountries);//Mocking Part
        assertEquals(2,countryServices.getAllCountries().size());
        verify(countryRepository,times(1)).findAll();
        assertNotNull(mycountries);

    }
    @Test
    @Order(2)
    public void test_getAllCountryByID()
    {
        List<Country> mycountries=new ArrayList<>();
        mycountries.add(new Country(1,"India","Delhi"));
        mycountries.add(new Country(2,"USA","Washington"));

        int countryID=1;

        when(countryRepository.findAll()).thenReturn(mycountries);
        assertEquals(countryID, countryServices.getCountrybyID(countryID).getId());

        verify(countryRepository,times(1)).findAll();
        assertNotNull(mycountries);


    }

    @Test
    @Order(3)
    public void test_getAllCountryByName()
    {
        List<Country> mycountries=new ArrayList<>();
        mycountries.add(new Country(1,"India","Delhi"));
        mycountries.add(new Country(2,"USA","Washington"));

        String countryName="India";

        when(countryRepository.findAll()).thenReturn(mycountries);
        assertEquals(countryName, countryServices.getCountrybyName(countryName).getCountryName());

        verify(countryRepository,times(1)).findAll();
        assertNotNull(mycountries);
    }

    @Test
    @Order(4)
    public void test_addCountry()
    {
        Country country= new Country(3,"Germany","Berlin");

        when(countryRepository.save(country)).thenReturn(country);
        assertEquals(country,countryServices.addCountry(country));
        verify(countryRepository,times(1)).save(country);
        assertNotNull(country);
    }

    @Test
    @Order(5)
    public void test_updateCountry()
    {
        Country country= new Country(3,"Germany","Berlin");

        when(countryRepository.save(country)).thenReturn(country);
        assertEquals(country,countryServices.updateCountry(country));
        verify(countryRepository,times(1)).save(country);
        assertNotNull(country);
    }

    @Test
    @Order(6)
    public void test_deleteCountry()
    {
        Country country= new Country(3,"Germany","Berlin");

        countryServices.deleteCountry(country);
        verify(countryRepository,times(1)).delete(country);
        assertNotNull(country);
    }


}
