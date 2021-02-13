package event.management.task.repo;

import event.management.task.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepo extends JpaRepository<Meeting, Long> {}
