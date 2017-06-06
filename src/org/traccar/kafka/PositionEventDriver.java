package org.traccar.kafka;

import javafx.geometry.Pos;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.traccar.helper.DistanceCalculator;
import org.traccar.kafka.serialization.PositionSerializer;
import org.traccar.model.Position;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Created by sdhaker on 30/05/17.
 */
public class PositionEventDriver {

    public static void main(final String[] args) throws Exception {
        final String bootstrapServers = args.length > 0 ? args[0] : "35.185.162.205:9092";
        final Properties streamsConfiguration = new Properties();
        // Give the Streams application a unique name.  The name must be unique in the Kafka cluster
        // against which the application is run.
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "position-stream");
        // Where to find Kafka broker(s).
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Specify default (de)serializers for record keys and for record values.
        streamsConfiguration.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        // Records should be flushed every 10 seconds. This is less than the default
        // in order to keep this example interactive.
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);
        // For illustrative purposes we disable record caches
        streamsConfiguration.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);

        // Set up serializers and deserializers, which we will use for overriding the default serdes
        // specified above.
        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();
        final Serde<org.traccar.model.Position> positionSerde = Serdes.serdeFrom(new PositionSerializer() ,new  PositionSerializer());

        // In the subsequent lines we define the processing topology of the Streams application.
        final KStreamBuilder builder = new KStreamBuilder();

        // Construct a `KStream` from the input topic "TextLinesTopic", where message values
        // represent lines of text (for the sake of this example, we ignore whatever may be stored
        // in the message keys).
        //
        // Note: We could also just call `builder.stream("TextLinesTopic")` if we wanted to leverage
        // the default serdes specified in the Streams configuration above, because these defaults
        // match what's in the actual topic.  However we explicitly set the deserializers in the
        // call to `stream()` below in order to show how that's done, too.
        final KStream<String, Position> positionStream = builder.stream(stringSerde, positionSerde, "positionsTopic");

        KTable<String , VehicleAggrigate>  kt  = positionStream.map((key , value) -> {return  new KeyValue<String , Position>("" + value.getDeviceId() , value) ;})
                .groupByKey(stringSerde , positionSerde)
                .aggregate(VehicleAggrigate::new,
                           (aggKey, value, aggregate) -> {
                            aggregate.add(value);
                            return aggregate;
                        },
                      //  TimeWindows.of(5 * 60 * 1000).advanceBy(1 * 60 * 100),
                      new VehicleAggrigateSerde(),

                "xyz"
                );

                kt.print();


                kt.toStream().print();





        final KafkaStreams streams = new KafkaStreams(builder, streamsConfiguration);
        // Always (and unconditionally) clean local state prior to starting the processing topology.
        // We opt for this unconditional call here because this will make it easier for you to play around with the example
        // when resetting the application for doing a re-run (via the Application Reset Tool,
        // http://docs.confluent.io/current/streams/developer-guide.html#application-reset-tool).
        //
        // The drawback of cleaning up local state prior is that your app must rebuilt its local state from scratch, which
        // will take time and will require reading all the state-relevant data from the Kafka cluster over the network.
        // Thus in a production scenario you typically do not want to clean up always as we do here but rather only when it
        // is truly needed, i.e., only under certain conditions (e.g., the presence of a command line flag for your app).
        // See `ApplicationResetExample.java` for a production-like example.
        streams.cleanUp();
        streams.start();

        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }





    public static class VehicleAggrigateSerde implements Serde<VehicleAggrigate>{

        @Override
        public void configure(Map<String, ?> map, boolean b) {

        }

        @Override
        public void close() {

        }

        @Override
        public Serializer<VehicleAggrigate> serializer() {
            return new Serializer<VehicleAggrigate>() {
                @Override
                public void configure(Map<String, ?> map, boolean b) {

                }

                @Override
                public byte[] serialize(String s, VehicleAggrigate vehicleAggrigate) {
                    final ByteArrayOutputStream out = new ByteArrayOutputStream();
                    final DataOutputStream
                            dataOutputStream =
                            new DataOutputStream(out);
                    try {
                            dataOutputStream.writeInt(vehicleAggrigate.totalDistance);
                            dataOutputStream.writeDouble(vehicleAggrigate.lastPosition.getLatitude());
                            dataOutputStream.writeDouble(vehicleAggrigate.lastPosition.getLongitude());
                        dataOutputStream.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return out.toByteArray();
                }

                @Override
                public void close() {

                }
            };
        }

        @Override
        public Deserializer<VehicleAggrigate> deserializer() {
            return new Deserializer<VehicleAggrigate>() {
                @Override
                public void configure(Map<String, ?> map, boolean b) {

                }

                @Override
                public VehicleAggrigate deserialize(String s, byte[] bytes) {

                    VehicleAggrigate va = new VehicleAggrigate();
                    if (bytes != null){
                        final DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
                    try {

                        va.totalDistance = dataInputStream.readInt();
                        Position lp = new Position();
                        lp.setLatitude(dataInputStream.readDouble());
                        lp.setLongitude(dataInputStream.readDouble());
                        va.lastPosition = lp;
                        //va.lastPosition = (new PositionSerializer()).deserialize(null , IOUtils.toByteArray(dataInputStream));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                     }
                    return va;
                }

                @Override
                public void close() {

                }
            };
        }
    }

    public static class VehicleAggrigate {
        ArrayList<Position> lastFiveLocations  = new ArrayList<Position>();

        public Position  lastPosition ;
        public int totalDistance = 0 ;
        public void add(Position positions){

            lastFiveLocations.add(positions);
            if(lastFiveLocations.size() == 5){
                lastFiveLocations.remove(4);
            }
            if(lastPosition == null){
                lastPosition = positions;
            }
            else {
                totalDistance += DistanceCalculator.distance(lastPosition.getLatitude() , lastPosition.getLongitude() , positions.getLatitude() , positions.getLongitude());
                lastPosition = positions;
            }
        }

        @Override
        public String toString() {
            return "\n" + totalDistance;
        }


        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }

}