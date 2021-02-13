package event.management.task.DTO;

import event.management.task.model.Meeting;
import event.management.task.model.MeetingStatus;
import lombok.Data;

import java.util.Date;

@Data
public class MeetingsDTO {

    private Long meeting_id;
    private String meeting_name;
    private Date end_time;
    private Date start_time;
    private MeetingStatus status;

    public MeetingsDTO() {}

    public MeetingsDTO(Meeting meeting){
        this.meeting_id = meeting.getMeeting_id();
        this.meeting_name = meeting.getMeeting_name();
        this.end_time = meeting.getEnd_time();
        this.start_time = meeting.getStart_time();
        this.status = meeting.getStatus();
    }
}
