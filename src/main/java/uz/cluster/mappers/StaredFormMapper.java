package uz.cluster.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uz.cluster.db.entity.forms.StaredForm;
import uz.cluster.payload.form.stared_forms.StaredFormCreateDTO;
import uz.cluster.payload.form.stared_forms.StaredFormDTO;
import uz.cluster.payload.form.stared_forms.StaredFormUpdateDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StaredFormMapper extends BaseMapper<
        StaredForm,
        StaredFormDTO,
        StaredFormCreateDTO,
        StaredFormUpdateDTO
        > {

    @Override
    StaredFormDTO toDTO(StaredForm staredForm);

    @Override
    List<StaredFormDTO> toDto(List<StaredForm> staredForms);

    @Override
    StaredForm formCreateDTO(StaredFormCreateDTO staredFormCreateDTO);

    @Override
    StaredForm fromUpdateDTO(StaredFormUpdateDTO d);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StaredForm fromUpdateDto(StaredFormUpdateDTO updateDTO, @MappingTarget StaredForm staredForm);
}
