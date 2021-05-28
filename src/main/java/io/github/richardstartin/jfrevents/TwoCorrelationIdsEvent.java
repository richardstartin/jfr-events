package io.github.richardstartin.jfrevents;

import jdk.jfr.*;

@Name("TwoCorrelationIdsEvent")
@Label("Two Correlation Ids Event")
@Description("Two Correlation Ids Event")
@Category("Custom")
@StackTrace(false)
public class TwoCorrelationIdsEvent extends Event {

  @Label("id 1")
  private final long id1;
  @Label("id 2")
  private final long id2;

  public TwoCorrelationIdsEvent(long id1, long id2) {
    this.id1 = id1;
    this.id2 = id2;
  }
}
