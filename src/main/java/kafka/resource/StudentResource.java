package kafka.resource;

import kafka.model.StudentBean;
import kafka.service.StudentKafkaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("kafka/")
public class StudentResource {

    private static final String TOPIC = "Kafka_Example";

    @PostMapping("addStudent")
    public String createSurveyRecord(@RequestBody StudentBean studentBean) {

        try {
            StudentKafkaService service = StudentKafkaService.getInstance();
            // insert the student into the kafka, if any reason student insert fails it
            service.produce(studentBean);
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
            List<StudentBean> obj = StudentKafkaService.getInstance().readStudentIds();
            return obj;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    @GetMapping("get/{name}")
    public StudentBean fetchRecord(@PathVariable("name") final String name) {
        try {
            System.out.println("input name " + name);
            StudentBean obj = StudentKafkaService.getInstance().readStudent(name);
            return obj;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
