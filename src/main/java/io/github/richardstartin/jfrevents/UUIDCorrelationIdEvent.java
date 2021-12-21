package io.github.richardstartin.jfrevents;

import jdk.jfr.*;

import java.util.UUID;
import java.util.stream.IntStream;

@Name("UUIDCorrelationIdsEvent")
@Label("One UUID Correlation Id Event")
@Description("One UUID Correlation Id Event")
@Category("Custom")
@StackTrace(false)
public class UUIDCorrelationIdEvent extends Event {

  private static final String[] UUIDS = IntStream.range(0, 1 << 21).mapToObj(i -> UUID.randomUUID().toString()).toArray(String[]::new);

  @Label("uuid")
  private final String uuid;

  public UUIDCorrelationIdEvent(long id) {
    this.uuid = UUIDS[(int)(id & (UUIDS.length - 1))];
  }
}
