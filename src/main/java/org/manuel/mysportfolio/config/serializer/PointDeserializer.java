package org.manuel.mysportfolio.config.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import java.io.IOException;
import org.springframework.data.geo.Point;

public class PointDeserializer extends StdDeserializer<Point> {

  public PointDeserializer() {
    this(null);
  }

  protected PointDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Point deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    final var node = jp.getCodec().readTree(jp);
    final double x = ((DoubleNode) node.get("x")).doubleValue();
    final double y = ((DoubleNode) node.get("y")).doubleValue();

    return new Point(x, y);
  }
}
