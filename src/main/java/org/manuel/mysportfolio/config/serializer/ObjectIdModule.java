package org.manuel.mysportfolio.config.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import org.bson.types.ObjectId;

public class ObjectIdModule {

  public static class ObjectIdSerializer extends StdSerializer<ObjectId> {

    private static final long serialVersionUID = -42L;

    public ObjectIdSerializer() {
      this(null);
    }

    public ObjectIdSerializer(Class<ObjectId> t) {
      super(t);
    }

    @Override
    public void serialize(final ObjectId value, final JsonGenerator jgen, final SerializerProvider provider)
        throws IOException {
      jgen.writeString(value.toString());
    }
  }

  public static class ObjectIdDeserializer extends StdDeserializer<ObjectId> {

    private static final long serialVersionUID = -42L;

    public ObjectIdDeserializer() {
      this(null);
    }

    public ObjectIdDeserializer(Class<?> vc) {
      super(vc);
    }

    @Override
    public ObjectId deserialize(final JsonParser parser, final DeserializationContext ctxt)
        throws IOException {
      final var codec = parser.getCodec();
      final TextNode node = codec.readTree(parser);

      // try catch block
      final var value = node.textValue();
      return value != null ? new ObjectId(value) : null;
    }

  }
}


