package kafka.resource;

import kafka.model.StudentBean;
import kafka.service.StudentKafkaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:53812")
@RestController
@RequestMapping("kafka/")
public class StudentResource {

    private static final String TOPIC = "Kafka_Example";

    @PostMapping("addStudent")
    public String createSurveyRecord(@RequestBody StudentBean studentBean) {

        try {
            StudentKafkaService service = StudentKafkaService.getInstance();
            service.produce(studentBean);
        } catch (Exception e) {
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
