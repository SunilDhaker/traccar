package org.traccar.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.traccar.Context;
import org.traccar.model.Device;
import org.traccar.model.Position;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by sdhaker on 05/06/17.
 */
public class VTSConsumersThread extends Thread{

    public void run(){


        Properties properties ;

        properties = new Properties();
        properties.put("group.id", "vts");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("bootstrap.servers", Context.getConfig().getString("bootstrap.servers"));



        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList( "device"));
        while (true) {

            ConsumerRecords<String , String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){


                String deviveJsonString = record.value();
                JsonReader jsonReader = Json.createReader(new StringReader(deviveJsonString));
                JsonObject deviceJson = jsonReader.readObject();
                jsonReader.close();
                Device entity = new Device();


                try {
                    JsonObject dataJson = deviceJson.getJsonObject("data");
                    String id = dataJson.getString("imei");
                    String mn = dataJson.getString("mobileNumber");
                    entity.setUniqueId(id);
                    entity.setName(mn);

                    long userId = 1L;


                    Context.getPermissionsManager().checkReadonly(userId);
                    Context.getPermissionsManager().checkDeviceReadonly(userId);
                    Context.getPermissionsManager().checkDeviceLimit(userId);
                    Context.getDeviceManager().addDevice(entity);
                    Context.getDataManager().linkDevice(userId, entity.getId());
                    Context.getPermissionsManager().refreshPermissions();
                    if (Context.getGeofenceManager() != null) {
                        Context.getGeofenceManager().refresh();
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }
    }



}
