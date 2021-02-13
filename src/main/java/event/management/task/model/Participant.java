package event.management.task.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "participant")
@Data
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participant_id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "organisation_name")
    private String organisation_name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "participant", cascade = CascadeType.ALL)
    private List<Invitation> invitations;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "participant_event",
            joinColumns = @JoinColumn(name = "participant_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "participant_meeting",
            joinColumns = @JoinColumn(name = "participant_id"),
            inverseJoinColumns = @JoinColumn(name = "meeting_id"))
    private List<Meeting> meetings;

    public Participant() {}

    public Participant(String name, String surname, String organisation_name){
        this.name = name;
        this.surname = surname;
        this.organisation_name = organisation_name;
    }
}
