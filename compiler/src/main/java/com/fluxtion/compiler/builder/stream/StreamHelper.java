package com.fluxtion.compiler.builder.stream;

class StreamHelper {
    static Object getSource(Object input) {
        Object returnValue = input;
        if (input instanceof EventStreamBuilder<?>) {
            EventStreamBuilder<?> eventStreamBuilder = (EventStreamBuilder<?>) input;
            returnValue = eventStreamBuilder.eventStream;
        } else if (input instanceof IntStreamBuilder) {
            IntStreamBuilder eventStreamBuilder = (IntStreamBuilder) input;
            returnValue = eventStreamBuilder.eventStream;
        } else if (input instanceof DoubleStreamBuilder) {
            DoubleStreamBuilder eventStreamBuilder = (DoubleStreamBuilder) input;
            returnValue = eventStreamBuilder.eventStream;
        } else if (input instanceof LongStreamBuilder) {
            LongStreamBuilder eventStreamBuilder = (LongStreamBuilder) input;
            returnValue = eventStreamBuilder.eventStream;
        }
        return returnValue;
    }

}