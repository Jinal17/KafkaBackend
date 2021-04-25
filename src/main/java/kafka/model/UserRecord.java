package kafka.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class UserRecord implements Serializer<UserRecord>, Deserializer<UserRecord> {

    private String name;
    private String dept;
    private Long salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    /**
     * Constructor to create StudentDAO object based on a studentBean passed to it
     *
     * @param studentBean
     */
    public UserRecord(User studentBean) throws Exception {
        this.name = studentBean.getName();
        this.dept = studentBean.getDept();
        this.salary = studentBean.getSalary();
    }

    /**
     * Method to convert the StudentDatabase object back to student bean object
     *
     * @return StudentBean object
     */
    public User convert() {
        User bean = new User(this.getName(),this.getDept(),this.getSalary());
        return bean;
    }
    @Override
    public void configure(Map configs, boolean isKey) {
        // TODO Auto-generated method stub

    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public byte[] serialize(String topic, UserRecord data) {
        byte[] survey = null;
        try {
            survey = new ObjectMapper().writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return survey;

    }

    @Override
    public UserRecord deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        UserRecord surveys = null;
        try {
            surveys = mapper.readValue(data, UserRecord.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return surveys;
    }
}
