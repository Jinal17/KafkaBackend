package kafka.service;

import kafka.model.StudentBean;
import kafka.model.StudentRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.*;

public class StudentKafkaService {
    // variable to hold the singleton database instance
    private static StudentKafkaService instance = null;
    public static final String TOPIC_NAME = "survey-data-topic";
    public static final String SERVER = "aa161fb2c5fd84b2e9c0762498b5d1c9-788544347.us-east-1.elb.amazonaws.com:9092";
    private Producer<Long, StudentRecord> producer;
    private KafkaConsumer<Long, StudentRecord> kafkaConsumer;
    private ConsumerRecords<Long, StudentRecord> records;
    List<StudentBean> studList =new ArrayList<>();
    List<StudentRecord> sb =new ArrayList<StudentRecord>();
    private StudentKafkaService() {
        setKafkaProducer();
        setKafkaConsumer();
    }

    /**
     * creating a singleton instance of database connection class
     *
     * @return
     */
    public static synchronized StudentKafkaService getInstance() {
        // if singleton instance is not available create an instance object
        if (null == instance) {
            instance = new StudentKafkaService();
        }
        // else return the existing instance object
        return instance;
    }

    private void setKafkaProducer() {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StudentRecord.class.getName());
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "all");
        producer = new KafkaProducer<>(producerProperties);
    }

    private void setKafkaConsumer() {
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StudentRecord.class.getName());
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        kafkaConsumer = new KafkaConsumer<>(consumerProperties);
        kafkaConsumer.subscribe(Collections.singletonList(TOPIC_NAME));

    }


    private Producer<Long, StudentRecord> getKafkaProducer() {
        return producer;
    }

    public StudentBean readStudent(String username) throws Exception {

//        if(null!=records) {
        kafkaConsumer.poll(0);
        // Now there is heartbeat and consumer is "alive"
        kafkaConsumer.seekToBeginning(kafkaConsumer.assignment());
        // Now consume
        records =  kafkaConsumer.poll(100);

        System.out.println("Fetched " + records.count() + " records");
        System.out.println("records " + records);
            for (ConsumerRecord<Long, StudentRecord> record : records) {
                System.out.println("Received: " + record.key() + ":" + record.value());
                StudentRecord temp = (StudentRecord) record.value();
                sb.add(temp);
            }

            for(StudentRecord s: sb){
                if (username.equals(s.getName())) {
                    return s.convert();
                }
            }
//        }

        return null;
    }

    public List<StudentBean> readStudentIds() throws Exception {
        List<StudentBean> studIDList = new ArrayList<>();
        //if(!sb.isEmpty()){
        //sb.clear();
        //}
        kafkaConsumer.poll(0);
        // Now there is heartbeat and consumer is "alive"
        kafkaConsumer.seekToBeginning(kafkaConsumer.assignment());
        // Now consume
        records =  kafkaConsumer.poll(100);

        System.out.println("Fetched " + records.count() + " records");
        System.out.println("records " + records);

        for (ConsumerRecord<Long, StudentRecord> record : records) {
            System.out.println("record " + record);
            System.out.println("Received: " + record.key() + ":" + record.value());
            StudentRecord studentRecord = (StudentRecord) record.value();
            //sb.add(temp);
            studIDList.add(studentRecord.convert());
        }
        //kafkaConsumer.commitSync();
        return studIDList;
    }

    public void produce(StudentBean studentBean) throws Exception {
        Producer<Long, StudentRecord> producer = getKafkaProducer();
        StudentRecord stdRecord = new StudentRecord(studentBean);
        ProducerRecord<Long, StudentRecord> record = new ProducerRecord<>(TOPIC_NAME, null,
                Long.valueOf(100), stdRecord);
        producer.send(record);
        System.out.println("Send record#" + stdRecord);
    }

}