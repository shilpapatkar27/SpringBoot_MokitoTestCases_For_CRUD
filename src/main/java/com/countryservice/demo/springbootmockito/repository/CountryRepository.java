package com.countryservice.demo.springbootmockito.repository;

import com.countryservice.demo.springbootmockito.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CountryRepository extends JpaRepository<Country, Integer>{

}
