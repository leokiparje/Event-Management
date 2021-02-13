package event.management.task.service;

import event.management.task.model.*;
import event.management.task.repo.EventRepo;
import event.management.task.repo.InvitationRepo;
import event.management.task.repo.MeetingRepo;
import event.management.task.repo.ParticipantRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class Service implements iService{

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private InvitationRepo invitationRepo;

    @Autowired
    private MeetingRepo meetingRepo;

    @Autowired
    private ParticipantRepo participantRepo;

    @Override
    public void insertParticipant(Participant participant) { participantRepo.save(participant); }

    @Override
    public void insertEvent(Event event) { eventRepo.save(event); }

    @Override
    public void insertInvitation(Invitation invitation) { invitationRepo.save(invitation); }

    @Override
    public void insertMeeting(Meeting meeting) { meetingRepo.save(meeting); }

    @Override
    public Optional<Participant> findParticipantById(Long id) { return participantRepo.findById(id); }

    public Optional<Event> findEventById(Long id) { return eventRepo.findById(id); }

    @Override
    public Optional<Invitation> findInvitationById(Long id) { return invitationRepo.findById(id); }

    @Override
    public List<Invitation> findAllInvitations() { return invitationRepo.findAll(); }

    @Override
    public List<Meeting> findAllScheduledMeetings() {
        List<Meeting> scheduledMeetings = meetingRepo.findAll().stream()
                .filter(meeting -> meeting.getStatus()!=null && meeting.getStatus().equals(MeetingStatus.SCHEDULED))
                .collect(Collectors.toList());
        return scheduledMeetings;
    }
}
