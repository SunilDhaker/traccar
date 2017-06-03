package org.traccar.kafka;
import javafx.geometry.Pos;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.traccar.kafka.serialization.JacksonReadingSerializer;
import org.traccar.kafka.serialization.StringReadingSerializer;
import org.traccar.model.Position;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class Example {
    private static final Random RANDOM = new Random();




      org.traccar.kafka.schema.Position p = org.traccar.kafka.schema.Position.newBuilder().setLongitude("9044").build();
    public static void main(String... argv) throws Exception {
        if(argv.length < 3) {
            System.out.println("Usage: java -jar kafka-org.traccar.kafka.serialization-example<version>.jar <consume|produce> <string|json|smile|kryo> <topic> [kafkahost:port]");
            System.out.println("Example: ");
            System.out.println("    java -jar kafka-org.traccar.kafka.serialization-example<version>.jar produce kryo test-kryo");
            System.out.println("       Runs a producer o");
            return;
        }
        Properties properties = new Properties();
        if(argv.length == 4) {
            properties.put("bootstrap.servers", argv[3]);
        }
        else {
            properties.put("bootstrap.servers", "localhost:9092");
        }

        System.out.printf("Connecting to Kafka on %s\n", properties.getProperty("bootstrap.servers"));

        String serializer;

        switch(argv[1]) {
            case "string":
                serializer = "org.apache.kafka.common.serialization.StringSerializer";//StringReadingSerializer.class.getName();break;
            case "json":
                serializer = JacksonReadingSerializer.class.getName();break;
            case "smile":
                properties.put("value.serializer.jackson.smile", "true");
                serializer = JacksonReadingSerializer.class.getName();break;

            default: throw new IllegalArgumentException("Unknown serializer: " + argv[1]);
        }

        if("produce".equals(argv[0])) {
            properties.put("value.serializer", serializer);
            runProducer(properties, argv[2]);
        }
        else {
            properties.put("value.deserializer", Position.class.getName());
            runConsumer(properties, argv[2]);
        }

    }

    public static void runConsumer(Properties properties, String topic) throws Exception {
        properties.put("group.id", "test");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.traccar.kafka.serialization.PositionSerializer");

        System.out.printf("Running consumer with serializer %s on topic %s\n", properties.getProperty("value.deserializer"), topic);

        KafkaConsumer<String, Position> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList( topic));
        while (true) {
            ConsumerRecords<String , Position> records = consumer.poll(100);
            for (ConsumerRecord<String, Position> record : records)
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value().getLatitude());
        }
    }

    public static void runProducer(Properties properties, String topic) throws Exception {
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("schema.registry.url", "http://35.185.162.205:8081");

        System.out.printf("Running producer with serializer %s on topic %s\n", properties.getProperty("value.serializer"), topic);

        Producer<String, SensorReading> producer = new KafkaProducer<>(properties);

        for(int i = 0; i < Integer.MAX_VALUE; i++) {
            producer.send(new ProducerRecord<>(topic, Integer.toString(i), randomReading()));
            //Thread.sleep(500);
        }


        producer.close();
    }

    private static SensorReading randomReading() {
        Sensor.Type type = Sensor.Type.values()[RANDOM.nextInt(Sensor.Type.values().length)];
        String id = type.toString().toLowerCase() + "-" + RANDOM.nextInt(10);
        double value = 0;
        switch(type) {
            case HUMIDITY:
                value = RANDOM.nextDouble() * 50.0 + 50.0;
                break;
            case TEMP:
                value = RANDOM.nextDouble() * 15.0 + 15.0;
                break;
            case LIGHT:
                value = RANDOM.nextDouble() * 12000.0; // candelas/m2
                break;
        }
        return new SensorReading(new Sensor(id, type), System.currentTimeMillis(), value);
    }
}