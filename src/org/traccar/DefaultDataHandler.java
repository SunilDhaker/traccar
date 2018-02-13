/*
 * Copyright 2015 Anton Tananaev (anton@traccar.org)
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

import javafx.geometry.Pos;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.traccar.helper.Log;
import org.traccar.model.Position;

import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

public class DefaultDataHandler extends BaseDataHandler {

    public static Producer<String , Position> producer ;
    DefaultDataHandler() {

        if(producer == null) {

            Properties properties = new Properties();
            properties.put("acks", "all");
            properties.put("retries", 0);
            properties.put("batch.size", 16384);
            properties.put("linger.ms", 1);
            properties.put("buffer.memory", 33554432);
            properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            properties.put("value.serializer", "org.traccar.kafka.serialization.PositionSerializer");
            properties.put("bootstrap.servers", Context.getConfig().getString("bootstrap.servers"));

            producer = new KafkaProducer<>(properties);
        }
    }

    @Override
    protected Position handlePosition(Position position) {

        try {
            position.setImei(Context.getIdentityManager().getDeviceById(position.getDeviceId()).getUniqueId());
            position.setDecoder("Concox-traccar");

            if(position.getServerTime() ==null)position.setServerTime(new Date());
            try {
                Context.getDataManager().addPosition(position);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            producer.send(new ProducerRecord<>("positionsTopic", position.getImei(), position));
        } catch (Exception error) {
            Log.warning(error);
        }

        return position;
    }
}
