package by.itransition.task4.dto;

import by.itransition.task4.entity.Status;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class StatusUpdateDto {

    @NotNull
    private Status status;

    @NotNull
    private List<Long> ids;
}
