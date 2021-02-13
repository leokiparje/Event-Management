package event.management.task.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class CreateEventDTO {

    private String event_name;
    private Date start_time;
    private Date end_time;

    public CreateEventDTO() {}
}
