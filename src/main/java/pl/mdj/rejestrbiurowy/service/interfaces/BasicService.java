package pl.mdj.rejestrbiurowy.service.interfaces;


import java.util.List;

/**
 *
 * @param <D> is always DTO
 * @param <I> id, is usually Long
 */
public interface BasicService<D, I> {
    List<D> getAll();
    D findOne(I id);  // TODO: maybe change name properly
    D addOne(D d);
    void deleteById(I id);
}
