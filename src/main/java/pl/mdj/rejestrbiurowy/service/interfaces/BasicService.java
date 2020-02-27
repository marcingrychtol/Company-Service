package pl.mdj.rejestrbiurowy.service.interfaces;


import java.util.List;

public interface BasicService<T, ID> {
    List<T> getAll();
    T getOne(ID id);
    T addOne(T t);
    void deleteById(ID id);
}
