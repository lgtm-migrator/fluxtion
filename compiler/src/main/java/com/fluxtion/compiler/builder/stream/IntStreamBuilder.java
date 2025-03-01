package com.fluxtion.compiler.builder.stream;

import com.fluxtion.runtime.EventProcessorConfigService;
import com.fluxtion.runtime.stream.BinaryMapEventStream;
import com.fluxtion.runtime.stream.EventStream.EventSupplierAccessor;
import com.fluxtion.runtime.stream.EventStream.IntEventStream;
import com.fluxtion.runtime.stream.EventStream.IntEventSupplier;
import com.fluxtion.runtime.stream.FilterDynamicEventStream;
import com.fluxtion.runtime.stream.FilterEventStream;
import com.fluxtion.runtime.stream.MapEventStream;
import com.fluxtion.runtime.stream.MapOnNotifyEventStream;
import com.fluxtion.runtime.stream.NotifyEventStream;
import com.fluxtion.runtime.stream.PeekEventStream;
import com.fluxtion.runtime.stream.PushEventStream;
import com.fluxtion.runtime.stream.SinkPublisher;
import com.fluxtion.runtime.stream.TriggeredEventStream;
import com.fluxtion.runtime.stream.WrappingEventSupplier.WrappingIntEventSupplier;
import com.fluxtion.runtime.stream.aggregate.AggregateIntStream;
import com.fluxtion.runtime.stream.aggregate.IntAggregateFunction;
import com.fluxtion.runtime.stream.aggregate.TimedSlidingWindowStream;
import com.fluxtion.runtime.stream.aggregate.TumblingWindowStream.TumblingIntWindowStream;
import com.fluxtion.runtime.stream.helpers.DefaultValue;
import com.fluxtion.runtime.stream.helpers.Peekers;

import static com.fluxtion.runtime.partition.LambdaReflection.*;

public class IntStreamBuilder implements EventSupplierAccessor<IntEventSupplier> {

    final IntEventStream eventStream;

    IntStreamBuilder(IntEventStream eventStream) {
        EventProcessorConfigService.service().add(eventStream);
        this.eventStream = eventStream;
    }

    @Override
    public IntEventSupplier getEventSupplier() {
        return EventProcessorConfigService.service().add(new WrappingIntEventSupplier(eventStream));
    }

    //TRIGGERS - START
    public IntStreamBuilder updateTrigger(Object updateTrigger) {
        Object source = StreamHelper.getSource(updateTrigger);
        if (eventStream instanceof TriggeredEventStream) {
            TriggeredEventStream triggeredEventStream = (TriggeredEventStream) eventStream;
            triggeredEventStream.setUpdateTriggerNode(source);
        }
        return this;
    }

    public IntStreamBuilder publishTrigger(Object publishTrigger) {
        Object source = StreamHelper.getSource(publishTrigger);
        if (eventStream instanceof TriggeredEventStream) {
            TriggeredEventStream triggeredEventStream = (TriggeredEventStream) eventStream;
            triggeredEventStream.setPublishTriggerNode(source);
        }
        return this;
    }

    public IntStreamBuilder publishTriggerOverride(Object publishTrigger) {
        Object source = StreamHelper.getSource(publishTrigger);
        if (eventStream instanceof TriggeredEventStream) {
            TriggeredEventStream triggeredEventStream = (TriggeredEventStream) eventStream;
            triggeredEventStream.setPublishTriggerOverrideNode(source);
        }
        return this;
    }

    public IntStreamBuilder resetTrigger(Object resetTrigger) {
        Object source = StreamHelper.getSource(resetTrigger);
        if (eventStream instanceof TriggeredEventStream) {
            TriggeredEventStream triggeredEventStream = (TriggeredEventStream) eventStream;
            triggeredEventStream.setResetTriggerNode(source);
        }
        return this;
    }

    public IntStreamBuilder filter(SerializableIntFunction<Boolean> filterFunction) {
        return new IntStreamBuilder(new FilterEventStream.IntFilterEventStream(eventStream, filterFunction));
    }

    public <S> IntStreamBuilder filter(
            SerializableBiIntPredicate predicate,
            IntStreamBuilder secondArgument) {
        return new IntStreamBuilder(
                new FilterDynamicEventStream.IntFilterDynamicEventStream(eventStream, secondArgument.eventStream, predicate));
    }

    public IntStreamBuilder defaultValue(int defaultValue) {
        return map(new DefaultValue.DefaultInt(defaultValue)::getOrDefault);
    }

    //PROCESSING - START
    public IntStreamBuilder map(SerializableIntUnaryOperator int2IntFunction) {
        return new IntStreamBuilder(new MapEventStream.MapInt2ToIntEventStream(eventStream, int2IntFunction));
    }

    public IntStreamBuilder mapBiFunction(SerializableBiIntFunction int2IntFunction, IntStreamBuilder stream2Builder) {
        return new IntStreamBuilder(
                new BinaryMapEventStream.BinaryMapToIntEventStream<>(
                        eventStream, stream2Builder.eventStream, int2IntFunction)
        );
    }

    public <F extends IntAggregateFunction<F>> IntStreamBuilder aggregate(
            SerializableSupplier<F> aggregateFunction) {
        return new IntStreamBuilder(new AggregateIntStream<>(eventStream, aggregateFunction));
    }

    public <F extends IntAggregateFunction<F>> IntStreamBuilder tumblingAggregate(
            SerializableSupplier<F> aggregateFunction, int bucketSizeMillis) {
        return new IntStreamBuilder(
                new TumblingIntWindowStream<>(eventStream, aggregateFunction, bucketSizeMillis));
    }

    public <F extends IntAggregateFunction<F>> IntStreamBuilder slidingAggregate(
            SerializableSupplier<F> aggregateFunction, int bucketSizeMillis, int numberOfBuckets) {
        return new IntStreamBuilder(
                new TimedSlidingWindowStream.TimedSlidingWindowIntStream<>(
                        eventStream,
                        aggregateFunction,
                        bucketSizeMillis,
                        numberOfBuckets));
    }

    public <T> EventStreamBuilder<T> mapOnNotify(T target) {
        return new EventStreamBuilder<>(new MapOnNotifyEventStream<>(eventStream, target));
    }

    public EventStreamBuilder<Integer> box() {
        return mapToObj(Integer::valueOf);
    }

    public <R> EventStreamBuilder<R> mapToObj(SerializableIntFunction<R> int2IntFunction) {
        return new EventStreamBuilder<>(new MapEventStream.MapInt2RefEventStream<>(eventStream, int2IntFunction));
    }

    public DoubleStreamBuilder mapToDouble(SerializableIntToDoubleFunction int2IntFunction) {
        return new DoubleStreamBuilder(new MapEventStream.MapInt2ToDoubleEventStream(eventStream, int2IntFunction));
    }

    public LongStreamBuilder mapToLong(SerializableIntToLongFunction int2IntFunction) {
        return new LongStreamBuilder(new MapEventStream.MapInt2ToLongEventStream(eventStream, int2IntFunction));
    }

    //OUTPUTS - START
    public IntStreamBuilder notify(Object target) {
        EventProcessorConfigService.service().add(target);
        return new IntStreamBuilder(new NotifyEventStream.IntNotifyEventStream(eventStream, target));
    }

    public IntStreamBuilder sink(String sinkId) {
        return push(new SinkPublisher<>(sinkId)::publishInt);
    }

    public IntStreamBuilder push(SerializableIntConsumer pushFunction) {
        if (pushFunction.captured().length > 0) {
            EventProcessorConfigService.service().add(pushFunction.captured()[0]);
        }
        return new IntStreamBuilder(new PushEventStream.IntPushEventStream(eventStream, pushFunction));
    }

    public IntStreamBuilder peek(SerializableConsumer<Integer> peekFunction) {
        return new IntStreamBuilder(new PeekEventStream.IntPeekEventStream(eventStream, peekFunction));
    }

    public IntStreamBuilder console(String in) {
        peek(Peekers.console(in));
        return this;
    }

    public IntStreamBuilder console() {
        return console("{}");
    }

    //META-DATA
    public IntStreamBuilder id(String nodeId) {
        EventProcessorConfigService.service().add(eventStream, nodeId);
        return this;
    }

}
