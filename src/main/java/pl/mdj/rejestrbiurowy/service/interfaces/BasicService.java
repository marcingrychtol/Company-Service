package pl.mdj.rejestrbiurowy.service.interfaces;


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
    D findById(I id);
    D addOne(D d) throws EntityNotCompleteException, EntityConflictException;
    void deleteById(I id);
}
