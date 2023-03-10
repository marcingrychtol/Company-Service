package pl.mdj.rejestrbiurowy.service;


import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.exceptions.WrongInputDataException;

import java.util.List;

/**
 *
 * @param <D> is always DTO
 * @param <I> id, is usually Long
 */
public interface BasicService<D, I> {
    List<D> findAll();
    List<D> findAllActive();
    D findById(I id) throws CannotFindEntityException;
    Long addOne(D d) throws EntityNotCompleteException, EntityConflictException, WrongInputDataException, CannotFindEntityException;
    void cancelByDto(D d) throws CannotFindEntityException, WrongInputDataException;
    void deleteByDto(D d) throws WrongInputDataException, CannotFindEntityException;
    void enableByDto(D d) throws CannotFindEntityException;
    void update(D d) throws EntityConflictException, WrongInputDataException, CannotFindEntityException;
}
