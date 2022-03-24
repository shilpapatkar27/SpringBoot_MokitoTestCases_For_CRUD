package com.countryservice.demo.springbootmockito.services;

import com.countryservice.demo.springbootmockito.model.Country;
import com.countryservice.demo.springbootmockito.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class CountryServices {

    @Autowired
    CountryRepository countryRepository;

    public List<Country> getAllCountries()
    {
        List<Country> countries= countryRepository.findAll();

        return countries;


    }

    public Country getCountrybyID(int id)
    {
        List<Country> countries= countryRepository.findAll();
        Country country = null;

        for(Country con:countries)
        {
            if(con.getId()==id)
                country=con;
        }
        return country;
    }

    public Country getCountrybyName(String countryName)
    {
        List<Country> countries= countryRepository.findAll();
        Country country = null;

        for(Country con:countries)
        {
            if(con.getCountryName().equalsIgnoreCase(countryName))
                country=con;
        }
        return country;
    }

    public Country addCountry(Country country)
    {
        country.setId(getMaxId());
        countryRepository.save(country);

        return country;
    }


    //Utility method to get max id
    public  int getMaxId()
    {
        // TODO Auto-generated method stub
        return countryRepository.findAll().size()+1;
    }


    public Country updateCountry(Country country )
    {
        countryRepository.save(country);
        return country;
    }



    public void deleteCountry(Country country)
    {
        countryRepository.delete(country);
    }
}
