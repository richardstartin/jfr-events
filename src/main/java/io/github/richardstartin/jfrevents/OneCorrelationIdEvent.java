package io.github.richardstartin.jfrevents;

import jdk.jfr.*;

@Name("OneCorrelationIdEvent")
@Label("One Correlation Id Event")
@Description("One Correlation Id Event")
@Category("Custom")
@StackTrace(false)
public class OneCorrelationIdEvent extends Event {

  @Label("id 1")
  private final long id1;

  public OneCorrelationIdEvent(long id1) {
    this.id1 = id1;
  }
}
