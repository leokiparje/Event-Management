package event.management.task.service;

import event.management.task.model.Event;
import event.management.task.model.Invitation;
import event.management.task.model.Meeting;
import event.management.task.model.Participant;

import java.util.List;
import java.util.Optional;

public interface iService {
    void insertParticipant(Participant participant);
    void insertEvent(Event event);
    void insertInvitation(Invitation invitation);
    void insertMeeting(Meeting meeting);
    Optional<Participant> findParticipantById(Long id);
    Optional <Event> findEventById(Long id);
    Optional <Invitation> findInvitationById(Long id);
    List<Invitation> findAllInvitations();
    List<Meeting> findAllScheduledMeetings();
}
