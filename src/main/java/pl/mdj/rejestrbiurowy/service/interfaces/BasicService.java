package pl.mdj.rejestrbiurowy.service.interfaces;


import pl.mdj.rejestrbiurowy.entity.interfaces.MyEntity;

import java.util.List;

public interface BasicService<T extends MyEntity, ID> {
    List<T> getAll();
    T getOne(ID id);
    T addOne(T t);
    void deleteById(ID id);
}
