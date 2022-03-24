package com.countryservice.demo.springbootmockito.model.controller;

import com.countryservice.demo.springbootmockito.model.Country;
import com.countryservice.demo.springbootmockito.services.CountryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class CountryController {

    @Autowired
    CountryServices countryServices;

    @GetMapping("/getcountries")
    public ResponseEntity<List<Country>> getCountries()
    {
        try {
            List<Country> countries=countryServices.getAllCountries();
            return new ResponseEntity<List<Country>>(countries, HttpStatus.FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getcountries/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable(value="id")int id)
    {
        try {
            Country country =countryServices.getCountrybyID(id);
            return new ResponseEntity<Country>(country, HttpStatus.FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getcountries/countryname")
    public ResponseEntity<Country> getCountryByName(@RequestParam(value="name") String countryName)
    {
        try {
            Country country =countryServices.getCountrybyName(countryName);
            return new ResponseEntity<Country>(country, HttpStatus.FOUND);
        }
        catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addcountry")
    public ResponseEntity<Country> addCountry(@RequestBody Country country)
    {
        try
        {
            country=countryServices.addCountry(country);
            return new ResponseEntity<Country>(country,HttpStatus.CREATED);
        }
        catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/updatecountry/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") int id,@RequestBody Country country)
    {
        try
        {
            Country exitCountry = countryServices.getCountrybyID(id);

            exitCountry.setCountryName(country.getCountryName());
            exitCountry.setCountryCapital(country.getCountryCapital());

            Country updated_country=countryServices.updateCountry(exitCountry);
            return new ResponseEntity<Country>(updated_country,HttpStatus.OK);

        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/deletecountry/{id}")
    public ResponseEntity<Country> deleteCountry(@PathVariable("id") int id)
    {
        Country country = null;
        try
        {
            country=countryServices.getCountrybyID(id);
            countryServices.deleteCountry(country);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Country>(country,HttpStatus.OK);
    }

}
