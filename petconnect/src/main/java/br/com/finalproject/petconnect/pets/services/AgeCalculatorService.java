package br.com.finalproject.petconnect.pets.services;

import br.com.finalproject.petconnect.exceptions.runtimes.ServiceException;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculatorService {

    public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if (birthDate == null || currentDate == null) {
            throw new ServiceException("As datas não podem ser nulas");
        }

        return Period.between(birthDate, currentDate).getYears();
    }

}
