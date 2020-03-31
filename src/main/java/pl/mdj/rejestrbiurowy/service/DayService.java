package pl.mdj.rejestrbiurowy.service;

import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.model.entity.Day;
import pl.mdj.rejestrbiurowy.model.entity.Trip;

import java.time.LocalDate;
import java.util.List;

public interface DayService extends BasicService<Day, LocalDate> {
    boolean saveAll(List<Day> days) throws EntityNotCompleteException, EntityConflictException;
    void addTripToDay(Trip trip) throws EntityNotCompleteException, EntityConflictException;
}