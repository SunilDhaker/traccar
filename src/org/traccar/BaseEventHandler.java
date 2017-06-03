/*
 * Copyright 2016 Anton Tananaev (anton@traccar.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.traccar;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.traccar.kafka.SensorReading;
import org.traccar.kafka.StringProducer;
import org.traccar.kafka.serialization.PositionSerializer;
import org.traccar.model.Event;
import org.traccar.model.Position;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.traccar.kafka.serialization.JacksonReadingSerializer;
import org.traccar.kafka.serialization.StringReadingSerializer;

public abstract class BaseEventHandler extends BaseDataHandler {

    Producer<String, String> producer;
    Producer<String, String> producer2;


    public BaseEventHandler(){

        String  serializer2 = PositionSerializer.class.getName();
        Properties properties = new Properties();
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("bootstrap.servers", "localhost:9092");


       // producer = new KafkaProducer<>(properties);
       // producer2 = new KafkaProducer<>(properties1);
    }
    @Override
    protected Position handlePosition(Position position) {

        System.out.println("In base event handler");

        Collection<Event> events = analyzePosition(position);
        if (events != null && Context.getNotificationManager() != null) {
            Context.getNotificationManager().updateEvents(events, position);
            System.out.println("size of event:"+events.size());


            for(Event event: events)
            {
                System.out.println(event);
                System.out.println(event.getType());
                System.out.println(event.getDeviceId());

                switch (event.getType()) {
//                    case "deviceOnline":
//                        producer.send(new ProducerRecord<>("deviceStateTopic", "01", "deviceOnline"));
//                        break;
//                    case "deviceOffline":
//                        producer.send(new ProducerRecord<>("deviceStateTopic", "02", "deviceOffline"));
//                        break;
//                    case "ignitionOn":
//                        producer.send(new ProducerRecord<>("deviceStateTopic", "03", "ignitionOn"));
//                        break;
//                    case "ignitionOff":
//                        producer.send(new ProducerRecord<>("deviceStateTopic", "04", "ignitionOff"));
//                        break;
//                    case "deviceStopped":
//                        producer.send(new ProducerRecord<>("deviceMovementTopic", "01", "deviceStopped"));
//                        break;
//                    case "deviceMoving":
//                        producer.send(new ProducerRecord<>("deviceMovementTopic", "02", "deviceMoving"));
//                        break;
//                    case "deviceOverspeed":
//                        producer.send(new ProducerRecord<>("deviceMovementTopic", "03", "deviceOverspeed"));
//                        break;
//                    case "geofenceEnter":
//                        producer.send(new ProducerRecord<>("deviceGeofenceTopic", "01", "geofenceEnter"));
//                        break;
//                    case "geofenceExit":
//                        producer.send(new ProducerRecord<>("deviceGeofenceTopic", "02", "geofenceExit"));
//                        break;
                    default: System.out.println("Unknown Event");
                        break;
                }
                //Thread.sleep(500);
            }
        }
        return position;
    }
    protected abstract Collection<Event> analyzePosition(Position position);
}
