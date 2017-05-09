package org.traccar.kafka;//


import java.util.Properties;

//import simple producer packages
import org.apache.kafka.clients.producer.Producer;

//import KafkaProducer packages
import org.apache.kafka.clients.producer.KafkaProducer;

//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerRecord;

//Create java class named “SimpleProducer”
public class StringProducer {


    Producer<String, String> producer ;
    public StringProducer() {

        // Check arguments length value


        //Assign topicName to string variable

        // create instance for properties to access producer configs
        Properties props = new Properties();

        //Assign localhost id
        props.put("bootstrap.servers", "localhost:9092");

        //Set acknowledgements for producer requests.
        props.put("acks", "all");

                //If the request fails, the producer can automatically retry,
        props.put("retries", 0);

        //Specify buffer size in config
        props.put("batch.size", 16384);

        //Reduce the no of requests less than 0
        props.put("linger.ms", 1);

        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("buffer.memory", 33554432);

        props.put("key.serializer",
                "org.apache.kafka.common.org.traccar.kafka.serialization.StringSerializer");

        props.put("value.serializer",
                "org.apache.kafka.common.org.traccar.kafka.serialization.StringSerializer");

        producer = new KafkaProducer<String, String>(props);

    }


    public void send(String topic , String message){
        producer.send(new ProducerRecord<String, String>(topic , message , message));
    }

    public void close(){
        producer.close();
    }
}