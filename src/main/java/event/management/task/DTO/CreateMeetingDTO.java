package event.management.task.DTO;

import event.management.task.model.Participant;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateMeetingDTO {

    private String meeting_name;
    private Date start_time;
    private Date end_time;
    private List<Participant> participants;

    public CreateMeetingDTO() {}
}
