package pl.mdj.rejestrbiurowy.service.mappers;

import pl.mdj.rejestrbiurowy.clientaccess.dto.MyDto;
import pl.mdj.rejestrbiurowy.entity.interfaces.MyEntity;

import java.util.List;

public interface BasicMapper<E extends MyEntity, D extends MyDto> {
    D mapToDto(E inputEntity);
    List<D> mapToDto(List<E> inputEntityList);
    E mapToEntity(D inputDto);
    List<E> mapToEntity(List<D> inputDtoList);

//    default List<D> mapToDto(List<E> inputEntityList){
//        return inputEntityList.stream()
//                .map(o -> mapToDto(o))
//                .collect(Collectors.toList());
//    }

}
