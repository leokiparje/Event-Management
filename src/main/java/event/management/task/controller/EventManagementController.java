package event.management.task.controller;

import event.management.task.DTO.*;
import event.management.task.model.*;
import event.management.task.service.iService;
import event.management.task.util.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Transactional
public class EventManagementController {

    @Autowired
    iService service;

    @GetMapping("/invitations")
    public ResponseEntity getAllInvitations(){

        return new ResponseEntity(service.findAllInvitations().stream().map(InvitationsDTO::new), HttpStatus.OK);
    }

    @GetMapping("/meetings")
    public ResponseEntity getAllScheduledMeetings(){

        return new ResponseEntity(service.findAllScheduledMeetings().stream().map(MeetingsDTO::new), HttpStatus.OK);
    }

    @PostMapping("/addParticipant")
    public ResponseEntity addParticipant(@DTO(CreateParticipantDTO.class) Participant participant) {
        service.insertParticipant(participant);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/addEvent")
    public ResponseEntity addEvent(@DTO(CreateEventDTO.class) Event event) {

        service.insertEvent(event);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/participant/{participant_id}/event/{event_id}")
    public ResponseEntity addParticipantToEvent(@PathVariable("participant_id") Long participant_id,
                                                @PathVariable("event_id") Long event_id){

        Optional<Participant> optionalParticipant = service.findParticipantById(participant_id);
        Optional<Event> optionalEvent = service.findEventById(event_id);

        if (optionalParticipant.isEmpty() || optionalEvent.isEmpty()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Participant participant = optionalParticipant.get();
        Event event = optionalEvent.get();

        if (participant.getEvents()!=null){
            List<Event> events = participant.getEvents().stream()
                    .filter(e -> !e.getStart_time().after(event.getEnd_time()) && !e.getEnd_time().before(event.getStart_time()))
                    .collect(Collectors.toList());
            if (!events.isEmpty()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        participant.getEvents().add(event);
        event.getParticipants().add(participant);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/participant/{id}/meeting")
    public ResponseEntity createMeeting(@PathVariable("id") Long id, @DTO(CreateMeetingDTO.class) Meeting meeting){

        if (meeting.getParticipants()==null) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Optional<Participant> optionalParticipant = service.findParticipantById(id);
        if (optionalParticipant.isEmpty()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        Participant participant = optionalParticipant.get();

        if (participant.getEvents()==null) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Event event = getCurrentEvent(participant);

        if (event==null) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        service.insertMeeting(meeting);

        List<Participant> participants = new ArrayList<>();
        participants.addAll(meeting.getParticipants());
        meeting.getParticipants().clear();

        for (Participant p : participants){
            Optional<Participant> pa = service.findParticipantById(p.getParticipant_id());
            if (pa.isEmpty()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
            if (pa.get().getEvents()==null) continue; // Ako participant nije na trenutnom eventu onda se preskače
            if (getCurrentEvent(pa.get())==null) continue; // Ako je participant na nekom drugom eventu onda se preskače
            if (getCurrentEvent(pa.get()).equals(event)){
                meeting.getParticipants().add(pa.get());
                pa.get().getMeetings().add(meeting);
                service.insertInvitation(new Invitation(meeting, pa.get()));
            }
        }
        service.insertMeeting(meeting);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("participant/{id}/meeting/status")
    public ResponseEntity respondToInvite(@PathVariable("id") Long id,
                                       @RequestParam(value = "invitation_id") Long invitation_id,
                                       @RequestParam(value = "status") int status){

        Optional<Participant> optionalParticipant = service.findParticipantById(id);
        if (optionalParticipant.isEmpty()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        Participant participant = optionalParticipant.get();

        Optional<Invitation> optionalInvitation = service.findInvitationById(invitation_id);
        if (optionalInvitation.isEmpty()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        Invitation invitation = optionalInvitation.get();
        Meeting meeting = invitation.getMeeting();
        if (meeting==null) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if (status==0) {
            invitation.setStatus(InvitationStatus.REJECTED);
        }else if(status==1){
            invitation.setStatus(InvitationStatus.ACCEPTED);
        }else return new ResponseEntity(HttpStatus.BAD_REQUEST);

        for (Invitation inv : meeting.getInvitations()){
            if (inv.getStatus().equals(InvitationStatus.PENDING)){
                return new ResponseEntity(HttpStatus.OK);
            }
        }

        // Ako je participant bio zadnji koji je prihvatio onda je meeting scheduled
        meeting.setStatus(MeetingStatus.SCHEDULED);

        return new ResponseEntity(HttpStatus.CREATED);
    }




    private List<Invitation> getInvitationsWithMeeting(Meeting meeting){

        List<Invitation> invitations = new ArrayList<>();

        invitations.stream()
                .filter(inv -> inv.getMeeting()!=null && inv.getMeeting().equals(meeting))
                .collect(Collectors.toList());

        return invitations;
    }

    private Event getCurrentEvent(Participant participant){
        Date now = new Date();
        for (Event e : participant.getEvents()){
            if (e.getStart_time().before(now) && e.getEnd_time().after(now)){
                return e;
            }
        }
        return null;
    }
}