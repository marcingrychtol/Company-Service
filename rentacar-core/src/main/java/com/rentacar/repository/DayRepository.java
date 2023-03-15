package com.rentacar.repository;

import com.rentacar.model.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DayRepository extends JpaRepository<Day, LocalDate> {
    List<Day> findAllByIdAfterOrderByIdAsc(LocalDate date);
    List<Day> findAllByIdBeforeOrderByIdAsc(LocalDate date);
    List<Day> findAllByIdBetweenOrderByIdAsc(LocalDate date, LocalDate date2);
//    should dop everything by one day
}
