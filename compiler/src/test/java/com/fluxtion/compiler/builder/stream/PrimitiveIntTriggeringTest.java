package com.fluxtion.compiler.builder.stream;

import com.fluxtion.compiler.generation.util.MultipleSepTargetInProcessTest;
import com.fluxtion.runtime.stream.aggregate.functions.AggregateIntSum;
import com.fluxtion.runtime.stream.helpers.Aggregates;
import com.fluxtion.runtime.stream.helpers.Mappers;
import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Assert;
import org.junit.Test;

public class PrimitiveIntTriggeringTest extends MultipleSepTargetInProcessTest {

    public PrimitiveIntTriggeringTest(boolean compiledSep) {
        super(compiledSep);
    }

    //MAPPING TESTS
    @Test
    public void resetMapTest() {
        sep(c -> EventFlow.subscribeToIntSignal("in")
                .map(Mappers.cumSumInt())
                .resetTrigger(EventFlow.subscribeToSignal("reset"))
                .sink("out"));

        MutableInt result = new MutableInt();
        addIntSink("out", result::setValue);

        publishSignal("in", 20);
        publishSignal("in", 50);
        Assert.assertEquals(70, result.intValue());

        publishSignal("reset");
        Assert.assertEquals(0, result.intValue());

        publishSignal("in", 90);
        publishSignal("in", 50);
        Assert.assertEquals(140, result.intValue());
    }

    @Test
    public void additionalPublishMapTest() {
        sep(c -> EventFlow.subscribeToIntSignal("in")
                .map(Mappers.cumSumInt())
                .publishTrigger(EventFlow.subscribeToSignal("publish"))
                .sink("out"));

        MutableInt result = new MutableInt();
        addIntSink("out", result::setValue);

        publishSignal("in", 20);
        publishSignal("in", 50);
        Assert.assertEquals(70, result.intValue());

        result.setValue(0);
        publishSignal("publish");
        Assert.assertEquals(70, result.intValue());
    }

    @Test
    public void overridePublishMapTest() {
        sep(c -> EventFlow.subscribeToIntSignal("in")
                .map(Mappers.cumSumInt())
                .publishTriggerOverride(EventFlow.subscribeToSignal("publish"))
                .sink("out"));

        MutableInt result = new MutableInt();
        addIntSink("out", result::setValue);

        publishSignal("in", 20);
        publishSignal("in", 50);
        Assert.assertEquals(0, result.intValue());

        publishSignal("publish");
        Assert.assertEquals(70, result.intValue());
    }

    @Test
    public void updateMapOnTriggerTest() {
        sep(c -> EventFlow.subscribeToIntSignal("in")
                .map(Mappers.cumSumInt())
                .updateTrigger(EventFlow.subscribeToSignal("update"))
                .sink("out"));

        MutableInt result = new MutableInt();
        addIntSink("out", result::setValue);

        publishSignal("in", 20);
        publishSignal("in", 50);
        Assert.assertEquals(0, result.intValue());

        publishSignal("update");
        publishSignal("update");
        publishSignal("update");
        Assert.assertEquals(150, result.intValue());
    }

    //AGGREGATE TESTS
    @Test
    public void resetAggregateTest() {
        sep(c -> EventFlow.subscribeToIntSignal("in")
                .aggregate(Aggregates.intSum())
                .resetTrigger(EventFlow.subscribeToSignal("reset"))
                .sink("out"));

        MutableInt result = new MutableInt();
        addIntSink("out", result::setValue);

        publishSignal("in", 20);
        publishSignal("in", 50);
        Assert.assertEquals(70, result.intValue());

        publishSignal("reset");
        Assert.assertEquals(0, result.intValue());

        publishSignal("in", 90);
        publishSignal("in", 50);
        Assert.assertEquals(140, result.intValue());
    }

    @Test
    public void additionalPublishAggregateTest() {
        sep(c -> EventFlow.subscribeToIntSignal("in")
                .aggregate(Aggregates.intSum())
                .publishTrigger(EventFlow.subscribeToSignal("publish"))
                .sink("out"));

        MutableInt result = new MutableInt();
        addIntSink("out", result::setValue);

        publishSignal("in", 20);
        publishSignal("in", 50);
        Assert.assertEquals(70, result.intValue());

        result.setValue(0);
        publishSignal("publish");
        Assert.assertEquals(70, result.intValue());
    }

    @Test
    public void overridePublishAggregateTest() {
        sep(c -> EventFlow.subscribeToIntSignal("in")
                .aggregate(Aggregates.intSum())
                .publishTriggerOverride(EventFlow.subscribeToSignal("publish"))
                .sink("out"));

        MutableInt result = new MutableInt();
        addIntSink("out", result::setValue);

        publishSignal("in", 20);
        publishSignal("in", 50);
        Assert.assertEquals(0, result.intValue());

        publishSignal("publish");
        Assert.assertEquals(70, result.intValue());
    }

    @Test
    public void updateAggregateOnTriggerTest() {
        sep(c -> EventFlow.subscribeToIntSignal("in")
                .aggregate(Aggregates.intSum())
                .updateTrigger(EventFlow.subscribeToSignal("update"))
                .sink("out"));

        MutableInt result = new MutableInt();
        addIntSink("out", result::setValue);

        publishSignal("in", 20);
        publishSignal("in", 50);
        Assert.assertEquals(0, result.intValue());

        publishSignal("update");
        publishSignal("update");
        publishSignal("update");
        Assert.assertEquals(150, result.intValue());
    }

    //TUMBLING
    @Test
    public void resetTumblingMapTest() {
        sep(c -> EventFlow.subscribeToIntSignal("in")
                .tumblingAggregate(AggregateIntSum::new, 100).id("sum")
                .resetTrigger(EventFlow.subscribeToSignal("reset"))
                .sink("out"));

        MutableInt result = new MutableInt();
        addIntSink("out", result::setValue);

        setTime(0);
        publishSignal("in", 20);
        publishSignal("in", 20);
        publishSignal("in", 20);
        tickDelta(100);
        Assert.assertEquals(60, result.intValue());

        publishSignal("in", 20);
        publishSignal("reset");
        tickDelta(100);
        Assert.assertEquals(0, result.intValue());

        publishSignal("in", 40);
        tickDelta(100);
        Assert.assertEquals(40, result.intValue());
    }

    @Test
    public void additionalPublishTumblingMapTest() {
        sep(c -> EventFlow.subscribeToIntSignal("in")
                .tumblingAggregate(AggregateIntSum::new, 100).id("sum")
                .publishTrigger(EventFlow.subscribeToSignal("publish"))
                .sink("out"));

        MutableInt result = new MutableInt();
        addIntSink("out", result::setValue);

        setTime(0);
        publishSignal("in", 20);
        publishSignal("in", 20);
        publishSignal("in", 20);
        tickDelta(100);
        Assert.assertEquals(60, result.intValue());

        result.setValue(0);
        publishSignal("in", 20);
        tickDelta(20);
        Assert.assertEquals(0, result.intValue());
        publishSignal("publish");
        Assert.assertEquals(60, result.intValue());

        tickDelta(120);
        Assert.assertEquals(20, result.intValue());
    }

    @Test
    public void overridePublishTumblingMapTest() {
        sep(c -> EventFlow.subscribeToIntSignal("in")
                .tumblingAggregate(AggregateIntSum::new, 100).id("sum")
                .publishTriggerOverride(EventFlow.subscribeToSignal("publish"))
                .sink("out"));

        MutableInt result = new MutableInt();
        addIntSink("out", result::setValue);

        setTime(0);
        publishSignal("in", 20);
        publishSignal("in", 20);
        publishSignal("in", 20);
        tickDelta(100);
        Assert.assertEquals(0, result.intValue());

        publishSignal("in", 20);
        tickDelta(20);
        publishSignal("publish");
        Assert.assertEquals(60, result.intValue());
    }

    @Test
    public void updateTriggerTumblingMapTest() {
        writeSourceFile = true;
        generateMetaInformation = true;
        sep(c -> EventFlow.subscribeToIntSignal("in")
                .tumblingAggregate(AggregateIntSum::new, 100).id("sum")
                .updateTrigger(EventFlow.subscribeToSignal("update"))
                .sink("out"));

        MutableInt result = new MutableInt();
        addIntSink("out", result::setValue);

        setTime(0);
        publishSignal("in", 20);
        Assert.assertEquals(0, result.intValue());

        tickDelta(30);
        Assert.assertEquals(0, result.intValue());

        publishSignal("update");
        Assert.assertEquals(20, result.intValue());

        tickDelta(30);
        publishSignal("in", 20);
        publishSignal("in", 50);
        Assert.assertEquals(20, result.intValue());

        publishSignal("update");
        Assert.assertEquals(90, result.intValue());

        publishSignal("in", 50);
        result.setValue(0);
        tickDelta(100);
        Assert.assertEquals(0, result.intValue());

        publishSignal("in", 50);
        publishSignal("update");
        Assert.assertEquals(50, result.intValue());
    }

    //SLIDING
}
