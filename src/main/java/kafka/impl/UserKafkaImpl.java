package kafka.impl;

import kafka.model.User;
import kafka.model.UserRecord;
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

public class UserKafkaImpl {
    // variable to hold the singleton database instance
    private static UserKafkaImpl instance = null;
    public static final String TOPIC_NAME = "Kafka_Example";
    public static final String SERVER = "localhost:9092";
    private Producer<Long, UserRecord> producer;
    private KafkaConsumer<Long, UserRecord> kafkaConsumer;
    private ConsumerRecords<Long, UserRecord> records;
    List<String> studList =new ArrayList<String>();
    List<UserRecord> sb =new ArrayList<UserRecord>();
    private UserKafkaImpl() {
//        setKafkaProducer();
        setKafkaConsumer();
    }

    /**
     * creating a singleton instance of database connection class
     *
     * @return
     */
    public static synchronized UserKafkaImpl getInstance() {
        // if singleton instance is not available create an instance object
        if (null == instance) {
            instance = new UserKafkaImpl();
        }
        // else return the existing instance object
        return instance;
    }

    private void setKafkaProducer() {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserRecord.class.getName());
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "all");
        producer = new KafkaProducer<>(producerProperties);
    }

    private void setKafkaConsumer() {
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UserRecord.class.getName());
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        kafkaConsumer = new KafkaConsumer<>(consumerProperties);
        kafkaConsumer.subscribe(Collections.singletonList(TOPIC_NAME));

    }


    private Producer<Long, UserRecord> getKafkaProducer() {
        return producer;
    }

    public User readStudent(String name) throws Exception {
        if(null!=records) {
            for (ConsumerRecord<Long, UserRecord> record : records) {
                System.out.println("Received: " + record.key() + ":" + record.value());
                UserRecord temp = (UserRecord) record.value();
                sb.add(temp);
            }

            for(UserRecord s: sb){
                if (name.equals(s.getName())) {
                    return s.convert();
                }
            }
        }

        return null;
    }

    public List<String> readStudentIds() throws Exception {
        List<String> studIDList = new ArrayList<String>();
        //if(!sb.isEmpty()){
        //sb.clear();
        //}
        kafkaConsumer.poll(0);
        // Now there is heartbeat and consumer is "alive"
        kafkaConsumer.seekToBeginning(kafkaConsumer.assignment());
        // Now consume
        records =  kafkaConsumer.poll(100);

        System.out.println("Fetched " + records.count() + " records");
        for (ConsumerRecord<Long, UserRecord> record : records) {
            System.out.println("Received: " + record.key() + ":" + record.value());
            UserRecord temp = (UserRecord) record.value();
            //sb.add(temp);
            studIDList.add(String.valueOf(temp.getName()));


        }
        for(String s: studIDList){
            if(!studList.contains(s)){
                studList.add(s);
            }

        }

        //kafkaConsumer.commitSync();
        return studList;
    }

    public void saveToDatabase(User studentBean) throws Exception {
        Producer<Long, UserRecord> producer = getKafkaProducer();
        UserRecord stdRecord = new UserRecord(studentBean);
        ProducerRecord<Long, UserRecord> record = new ProducerRecord<Long, UserRecord>(TOPIC_NAME, null,
                Long.valueOf(100), stdRecord);
        producer.send(record);
        System.out.println("Send record#" + stdRecord);
    }

}