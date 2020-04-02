package pl.mdj.rejestrbiurowy.service;


import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;

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
}
