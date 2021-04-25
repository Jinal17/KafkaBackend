package kafka.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class StudentRecord implements Serializer<StudentRecord>, Deserializer<StudentRecord> {

    String name;
    String lastname;
    String address;
    String city;
    String state;
    String zip;
    String phone;
    String email;
    String dos;
    String about_college;
    String interested;
    String recommend;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDos() {
        return dos;
    }

    public void setDos(String dos) {
        this.dos = dos;
    }

    public String getAbout_college() {
        return about_college;
    }

    public void setAbout_college(String about_college) {
        this.about_college = about_college;
    }

    public String getInterested() {
        return interested;
    }

    public void setInterested(String interested) {
        this.interested = interested;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public StudentRecord() {

    }

    /**
     * Constructor to create StudentDAO object based on a studentBean passed to it
     *
     * @param studentBean
     */
    public StudentRecord(StudentBean studentBean) throws Exception {
        this.name = studentBean.getName();
        this.lastname = studentBean.getLastname();
        this.address = studentBean.getAddress();
        this.city = studentBean.getCity();
        this.state = studentBean.getState();
        this.zip = studentBean.getZip();
        this.phone = studentBean.getPhone();
        this.email = studentBean.getEmail();
        this.dos = studentBean.getDos();
        this.about_college = studentBean.getAbout_college();
        this.interested = studentBean.getInterested();
        this.recommend = studentBean.getRecommend();

    }

    /**
     * Method to convert the StudentDatabase object back to student bean object
     *
     * @return StudentBean object
     */
    public StudentBean convert() {
        StudentBean bean = new StudentBean();
        bean.setName(this.getName());
        bean.setLastname(this.getLastname());
        bean.setAddress(this.getAddress());
        bean.setCity(this.getCity());
        bean.setState(this.getState());
        bean.setZip(this.getZip());
        bean.setPhone(this.getPhone());
        bean.setEmail(this.getEmail());
        bean.setDos(this.getDos());
        bean.setAbout_college(this.getAbout_college());
        bean.setInterested(this.getInterested());
        bean.setRecommend(this.getRecommend());
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
    public byte[] serialize(String topic, StudentRecord data) {
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
    public StudentRecord deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        StudentRecord surveys = null;
        try {
            surveys = mapper.readValue(data, StudentRecord.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return surveys;
    }

}