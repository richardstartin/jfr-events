package io.github.richardstartin.jfrevents;

import jdk.jfr.*;

@Name("EmptyEvent")
@Label("EmptyEvent")
@Description("Empty Event")
@Category("Custom")
@StackTrace(false)
public class EmptyEvent extends Event {
}
