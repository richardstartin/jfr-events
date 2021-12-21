package io.github.richardstartin.jfrevents;

import jdk.jfr.*;

@Name("TwoDoubleCorrelationIdsEvent")
@Label("Two Double Correlation Ids Event")
@Description("Two Correlation Ids Event")
@Category("Custom")
@StackTrace(false)
public class TwoDoubleCorrelationIdsEvent extends Event {

  @Label("id 1")
  private final double id1;
  @Label("id 2")
  private final double id2;

  public TwoDoubleCorrelationIdsEvent(long id1, long id2) {
    this.id1 = Double.longBitsToDouble(id1);
    this.id2 = Double.longBitsToDouble(id2);
  }
}
