package org.traccar.kafka.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.traccar.model.Position;


import java.io.Closeable;
import java.io.IOException;
import java.util.Map;



/**
 * (De)serializes SensorReadings using Jackson. Supports both JSON and Smile.
 */
public class PositionSerializer implements Closeable, AutoCloseable, Serializer<Position>, Deserializer<Position> {
    private ObjectMapper mapper;

    public PositionSerializer() {
        this.mapper = new ObjectMapper();
    }

    public PositionSerializer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public static PositionSerializer defaultConfig() {
        return new PositionSerializer(new ObjectMapper());
    }

    public static PositionSerializer smileConfig() {
        return new PositionSerializer(new ObjectMapper(new SmileFactory()));
    }

    @Override
    public void configure(Map<String, ?> map, boolean b) {
        if(mapper == null) {
            if("true".equals(map.get("value.serializer.jackson.smile"))) {
                mapper = new ObjectMapper(new SmileFactory());
            }
            else {
                mapper = new ObjectMapper();
            }
        }
    }

    @Override
    public byte[] serialize(String s, Position position) {
        try {
            return mapper.writeValueAsBytes(position);
        }
        catch(JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Position deserialize(String s, byte[] bytes) {
        try {
            return mapper.readValue(bytes, Position.class);
        }
        catch(IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


    @Override
    public void close() {
        mapper = null;
    }


}
