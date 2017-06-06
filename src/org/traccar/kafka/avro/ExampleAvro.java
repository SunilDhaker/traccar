package org.traccar.kafka.avro;

import java.util.Properties;
import java.util.Random;


public class ExampleAvro {

    private static final Random RANDOM = new Random();

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
            properties.put("bootstrap.servers", "35.185.162.205:9092");
        }

        System.out.printf("Connecting to Kafka on %s\n", properties.getProperty("bootstrap.servers"));

        String serializer;

        switch(argv[1]) {
            case "string":
                serializer = "org.apache.kafka.common.serialization.StringSerializer";//StringReadingSerializer.class.getName();break;
            case "json":
//                serializer = JacksonReadingSerializer.class.getName();break;
            case "smile":
                properties.put("value.serializer.jackson.smile", "true");
//                serializer = JacksonReadingSerializer.class.getName();break;

            default: throw new IllegalArgumentException("Unknown serializer: " + argv[1]);
        }



    }

}
