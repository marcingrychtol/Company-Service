package pl.mdj.rejestrbiurowy.service.mappers;

import java.util.List;

public interface BasicMapper<E, D> {
    D mapToDto(E entity);
    List<D> mapToDto(List<E> entityList);
    E mapToEntity(D dto);
    List<E> mapToEntity(List<D> dtoList);

    // TODO: think about MapStruct https://mapstruct.org/

//    default List<D> mapToDto(List<E> inputEntityList){
//        return inputEntityList.stream()
//                .map(o -> mapToDto(o))
//                .collect(Collectors.toList());
//    }

}
