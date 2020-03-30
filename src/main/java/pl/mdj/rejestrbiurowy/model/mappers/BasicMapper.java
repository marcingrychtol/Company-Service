package pl.mdj.rejestrbiurowy.model.mappers;

import java.util.List;

public interface BasicMapper<E, D> {
    D mapToDto(E entity);
    List<D> mapToDto(List<E> entityList);
    E mapToEntity(D dto);
    List<E> mapToEntity(List<D> dtoList);
}
