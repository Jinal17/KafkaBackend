package kafka.resource;

import kafka.bean.StudentBean;
import kafka.impl.StudentKafkaImpl;
import kafka.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("kafka/")
public class StudentResource {

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    private static final String TOPIC = "Kafka_Example";

    @PostMapping("publish/{name}/{lastName}")
    public String post(@PathVariable("name") final String name,
                       @PathVariable("lastName") final String lastName) {

        kafkaTemplate.send(TOPIC, new User(name, lastName, 12000L));

        return "Published successfully";
    }
    @PostMapping("addStudent")
    public String createSurveyRecord(@RequestBody StudentBean studentBean) {

//        kafkaTemplate.send(TOPIC, new User(name, lastName, 12000L));
        try {
            StudentKafkaImpl impl = StudentKafkaImpl.getInstance();
            // insert the student into the kafka, if any reason student insert fails it
            impl.saveToDatabase(studentBean);
        } catch (Exception e) {
            // if the student insertion fails send to the home page with reason for the
            // error
            System.out.println("exception occurred during insertion " + e.getMessage());
            e.printStackTrace();
            return "Error";
        }
        return "Published successfully";
    }

    @GetMapping("students")
    public List<StudentBean> getAllStudentsSurveyForm() {
        try {
            List<StudentBean> obj = StudentKafkaImpl.getInstance().readStudentIds();
            return obj;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    @GetMapping("/get/{name}")
    public StudentBean fetchRecord(@PathVariable("name") final String name) {
        try {
            System.out.println("input name " + name);
            StudentBean obj = StudentKafkaImpl.getInstance().readStudent(name);
            return obj;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
