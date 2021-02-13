package event.management.task.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "meeting")
@Data
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meeting_id;

    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date start_time;

    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date end_time;

    @Column(name = "meeting_name")
    private String meeting_name;

    @Column(name = "scheduled")
    @Enumerated(EnumType.STRING)
    private MeetingStatus status = MeetingStatus.PREPARING;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<Invitation> invitations;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "meetings")
    private List<Participant> participants;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(meeting_id, meeting.meeting_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meeting_id);
    }
}
