package pl.mdj.rejestrbiurowy.service;


import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.exceptions.WrongInputDataException;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;

import java.util.List;

/**
 *
 * @param <D> is always DTO
 * @param <I> id, is usually Long
 */
public interface BasicService<D, I> {
    List<D> getAll();
    D findById(I id) throws CannotFindEntityException;
    D addOne(D d) throws EntityNotCompleteException, EntityConflictException;
    void cancelById(I id);

    void update(CarDto carDto) throws EntityConflictException, WrongInputDataException;
}
