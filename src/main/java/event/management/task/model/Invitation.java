package event.management.task.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "invitation")
@Data
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invitation_id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private InvitationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Participant participant;

    @ManyToOne(fetch = FetchType.LAZY)
    private Meeting meeting;

    public Invitation() {}

    public Invitation(Meeting meeting, Participant participant){
        this.status = InvitationStatus.PENDING;
        this.participant = participant;
        this.meeting = meeting;
    }
}
