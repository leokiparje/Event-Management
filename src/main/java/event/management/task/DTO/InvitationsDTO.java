package event.management.task.DTO;

import event.management.task.model.Invitation;
import event.management.task.model.InvitationStatus;
import lombok.Data;

@Data
public class InvitationsDTO {

    private Long invitation_id;
    private InvitationStatus status;
    private Long participant_id;
    private Long meeting_id;

    public InvitationsDTO() {}

    public InvitationsDTO(Invitation invitation) {
        this.invitation_id = invitation.getInvitation_id();
        this.status = invitation.getStatus();
        this.participant_id = invitation.getParticipant().getParticipant_id();
        this.meeting_id = invitation.getMeeting().getMeeting_id();
    }
}
