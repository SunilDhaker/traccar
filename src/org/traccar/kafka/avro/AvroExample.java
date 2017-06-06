package org.traccar.kafka.avro;

import java.io.File;
import java.io.IOException;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.traccar.kafka.Sensor;
import org.traccar.kafka.SensorReading;
public class AvroExample {

    public static void main(String[] args) throws InterruptedException, IOException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "35.185.162.205:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");

        Schema.Parser parser = new Schema.Parser();
        Schema schema = new Schema.Parser().parse(new File("D:\\Projects\\traccar-java\\traccar\\src\\org\\traccar\\kafka\\avro\\user.avsc"));


        GenericRecord e1 = new GenericData.Record(schema);

        e1.put("name", "ramu");
        e1.put("favorite_number", 001);
        e1.put("favorite_color","red");


        GenericRecord e2 = new GenericData.Record(schema);

        e2.put("name", "rahman");
        e2.put("favorite_number", 002);
        e2.put("favorite_color", "blue");


        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);

        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
        dataFileWriter.create(schema, new File("D:\\Projects\\traccar-java\\traccar\\src\\org\\traccar\\kafka\\avro\\testData.t xt"));

        dataFileWriter.append(e1);
        dataFileWriter.append(e2);
        dataFileWriter.close();

        System.out.println("data successfully serialized");

        KafkaProducer<String, byte[]> producer = new KafkaProducer<>(props);


        producer.close();
    }
}
