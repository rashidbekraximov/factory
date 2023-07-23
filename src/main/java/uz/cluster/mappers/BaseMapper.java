package uz.cluster.mappers;

import java.util.List;

/**
 *
 * @param <E> Entity type
 * @param <D> Base DTO of Entity
 * @param <CD> Create Dto of Entity
 * @param <UD> Update Dto of Entity
 * @see uz.cluster.mappers.Mapper
 * @apiNote https://mapstruct.org/
 */

public interface BaseMapper<E, D, CD, UD> extends Mapper {
    D toDTO(E e);

    List<D> toDto(List<E> eList);

    E formCreateDTO(CD cd);

    E fromUpdateDTO(UD d);



}
