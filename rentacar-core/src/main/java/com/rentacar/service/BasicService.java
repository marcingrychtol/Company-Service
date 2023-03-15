package com.rentacar.service;


import com.rentacar.exceptions.CannotFindEntityException;
import com.rentacar.exceptions.EntityConflictException;
import com.rentacar.exceptions.EntityNotCompleteException;
import com.rentacar.exceptions.WrongInputDataException;

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
