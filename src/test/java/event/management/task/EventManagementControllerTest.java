package event.management.task;

import event.management.task.model.Participant;
import event.management.task.service.iService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventManagementApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventManagementControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    iService service;

    @Test
    public void addParticipantStatusTest(){
        Participant participant = new Participant("Marko","Manić","FSB");
        ResponseEntity responseEntity = this.restTemplate
                .postForEntity("http://localhost:"+port+"/addParticipant", participant, Participant.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

}