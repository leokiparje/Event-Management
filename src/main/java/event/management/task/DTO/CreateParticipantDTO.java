package event.management.task.DTO;

import lombok.Data;

@Data
public class CreateParticipantDTO {

    private String name;
    private String surname;
    private String organisation_name;

    public CreateParticipantDTO() {}
}
