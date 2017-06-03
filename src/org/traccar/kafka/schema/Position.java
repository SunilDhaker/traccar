/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package org.traccar.kafka.schema;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Position extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Position\",\"namespace\":\"org.traccar.kafka.schema\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"timestamp\",\"type\":\"long\"},{\"name\":\"latitude\",\"type\":\"string\"},{\"name\":\"longitude\",\"type\":\"string\"},{\"name\":\"speed\",\"type\":\"string\"},{\"name\":\"accuracy\",\"type\":[\"null\",\"int\"],\"default\":null}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence id;
  @Deprecated public long timestamp;
  @Deprecated public java.lang.CharSequence latitude;
  @Deprecated public java.lang.CharSequence longitude;
  @Deprecated public java.lang.CharSequence speed;
  @Deprecated public java.lang.Integer accuracy;

  /**
   * Default constructor.
   */
  public Position() {}

  /**
   * All-args constructor.
   */
  public Position(java.lang.CharSequence id, java.lang.Long timestamp, java.lang.CharSequence latitude, java.lang.CharSequence longitude, java.lang.CharSequence speed, java.lang.Integer accuracy) {
    this.id = id;
    this.timestamp = timestamp;
    this.latitude = latitude;
    this.longitude = longitude;
    this.speed = speed;
    this.accuracy = accuracy;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return timestamp;
    case 2: return latitude;
    case 3: return longitude;
    case 4: return speed;
    case 5: return accuracy;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.CharSequence)value$; break;
    case 1: timestamp = (java.lang.Long)value$; break;
    case 2: latitude = (java.lang.CharSequence)value$; break;
    case 3: longitude = (java.lang.CharSequence)value$; break;
    case 4: speed = (java.lang.CharSequence)value$; break;
    case 5: accuracy = (java.lang.Integer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'id' field.
   */
  public java.lang.CharSequence getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.CharSequence value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'timestamp' field.
   */
  public java.lang.Long getTimestamp() {
    return timestamp;
  }

  /**
   * Sets the value of the 'timestamp' field.
   * @param value the value to set.
   */
  public void setTimestamp(java.lang.Long value) {
    this.timestamp = value;
  }

  /**
   * Gets the value of the 'latitude' field.
   */
  public java.lang.CharSequence getLatitude() {
    return latitude;
  }

  /**
   * Sets the value of the 'latitude' field.
   * @param value the value to set.
   */
  public void setLatitude(java.lang.CharSequence value) {
    this.latitude = value;
  }

  /**
   * Gets the value of the 'longitude' field.
   */
  public java.lang.CharSequence getLongitude() {
    return longitude;
  }

  /**
   * Sets the value of the 'longitude' field.
   * @param value the value to set.
   */
  public void setLongitude(java.lang.CharSequence value) {
    this.longitude = value;
  }

  /**
   * Gets the value of the 'speed' field.
   */
  public java.lang.CharSequence getSpeed() {
    return speed;
  }

  /**
   * Sets the value of the 'speed' field.
   * @param value the value to set.
   */
  public void setSpeed(java.lang.CharSequence value) {
    this.speed = value;
  }

  /**
   * Gets the value of the 'accuracy' field.
   */
  public java.lang.Integer getAccuracy() {
    return accuracy;
  }

  /**
   * Sets the value of the 'accuracy' field.
   * @param value the value to set.
   */
  public void setAccuracy(java.lang.Integer value) {
    this.accuracy = value;
  }

  /** Creates a new Position RecordBuilder */
  public static org.traccar.kafka.schema.Position.Builder newBuilder() {
    return new org.traccar.kafka.schema.Position.Builder();
  }
  
  /** Creates a new Position RecordBuilder by copying an existing Builder */
  public static org.traccar.kafka.schema.Position.Builder newBuilder(org.traccar.kafka.schema.Position.Builder other) {
    return new org.traccar.kafka.schema.Position.Builder(other);
  }
  
  /** Creates a new Position RecordBuilder by copying an existing Position instance */
  public static org.traccar.kafka.schema.Position.Builder newBuilder(org.traccar.kafka.schema.Position other) {
    return new org.traccar.kafka.schema.Position.Builder(other);
  }
  
  /**
   * RecordBuilder for Position instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Position>
    implements org.apache.avro.data.RecordBuilder<Position> {

    private java.lang.CharSequence id;
    private long timestamp;
    private java.lang.CharSequence latitude;
    private java.lang.CharSequence longitude;
    private java.lang.CharSequence speed;
    private java.lang.Integer accuracy;

    /** Creates a new Builder */
    private Builder() {
      super(org.traccar.kafka.schema.Position.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(org.traccar.kafka.schema.Position.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing Position instance */
    private Builder(org.traccar.kafka.schema.Position other) {
            super(org.traccar.kafka.schema.Position.SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[1].schema(), other.timestamp);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.latitude)) {
        this.latitude = data().deepCopy(fields()[2].schema(), other.latitude);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.longitude)) {
        this.longitude = data().deepCopy(fields()[3].schema(), other.longitude);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.speed)) {
        this.speed = data().deepCopy(fields()[4].schema(), other.speed);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.accuracy)) {
        this.accuracy = data().deepCopy(fields()[5].schema(), other.accuracy);
        fieldSetFlags()[5] = true;
      }
    }

    /** Gets the value of the 'id' field */
    public java.lang.CharSequence getId() {
      return id;
    }
    
    /** Sets the value of the 'id' field */
    public org.traccar.kafka.schema.Position.Builder setId(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'id' field has been set */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'id' field */
    public org.traccar.kafka.schema.Position.Builder clearId() {
      id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'timestamp' field */
    public java.lang.Long getTimestamp() {
      return timestamp;
    }
    
    /** Sets the value of the 'timestamp' field */
    public org.traccar.kafka.schema.Position.Builder setTimestamp(long value) {
      validate(fields()[1], value);
      this.timestamp = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'timestamp' field has been set */
    public boolean hasTimestamp() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'timestamp' field */
    public org.traccar.kafka.schema.Position.Builder clearTimestamp() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'latitude' field */
    public java.lang.CharSequence getLatitude() {
      return latitude;
    }
    
    /** Sets the value of the 'latitude' field */
    public org.traccar.kafka.schema.Position.Builder setLatitude(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.latitude = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'latitude' field has been set */
    public boolean hasLatitude() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'latitude' field */
    public org.traccar.kafka.schema.Position.Builder clearLatitude() {
      latitude = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'longitude' field */
    public java.lang.CharSequence getLongitude() {
      return longitude;
    }
    
    /** Sets the value of the 'longitude' field */
    public org.traccar.kafka.schema.Position.Builder setLongitude(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.longitude = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'longitude' field has been set */
    public boolean hasLongitude() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'longitude' field */
    public org.traccar.kafka.schema.Position.Builder clearLongitude() {
      longitude = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'speed' field */
    public java.lang.CharSequence getSpeed() {
      return speed;
    }
    
    /** Sets the value of the 'speed' field */
    public org.traccar.kafka.schema.Position.Builder setSpeed(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.speed = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'speed' field has been set */
    public boolean hasSpeed() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'speed' field */
    public org.traccar.kafka.schema.Position.Builder clearSpeed() {
      speed = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'accuracy' field */
    public java.lang.Integer getAccuracy() {
      return accuracy;
    }
    
    /** Sets the value of the 'accuracy' field */
    public org.traccar.kafka.schema.Position.Builder setAccuracy(java.lang.Integer value) {
      validate(fields()[5], value);
      this.accuracy = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'accuracy' field has been set */
    public boolean hasAccuracy() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'accuracy' field */
    public org.traccar.kafka.schema.Position.Builder clearAccuracy() {
      accuracy = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    @Override
    public Position build() {
      try {
        Position record = new Position();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.timestamp = fieldSetFlags()[1] ? this.timestamp : (java.lang.Long) defaultValue(fields()[1]);
        record.latitude = fieldSetFlags()[2] ? this.latitude : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.longitude = fieldSetFlags()[3] ? this.longitude : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.speed = fieldSetFlags()[4] ? this.speed : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.accuracy = fieldSetFlags()[5] ? this.accuracy : (java.lang.Integer) defaultValue(fields()[5]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
