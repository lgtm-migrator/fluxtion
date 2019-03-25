/*
 * Copyright (C) 2019 V12 Technology Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Server Side Public License, version 1,
 * as published by MongoDB, Inc.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Server Side License for more details.
 *
 * You should have received a copy of the Server Side Public License
 * along with this program.  If not, see 
 * <http://www.mongodb.com/licensing/server-side-public-license>.
 */
package com.fluxtion.ext.declarative.builder.stream;
import com.fluxtion.api.event.Event;
import com.fluxtion.api.partition.LambdaReflection.SerializableFunction;
import com.fluxtion.api.partition.LambdaReflection.SerializableSupplier;
import com.fluxtion.ext.declarative.api.Wrapper;
import com.fluxtion.ext.declarative.api.stream.StreamFunctions.Average;
import com.fluxtion.ext.declarative.api.stream.StreamFunctions.Count;
import com.fluxtion.ext.declarative.api.stream.StreamFunctions.Max;
import com.fluxtion.ext.declarative.api.stream.StreamFunctions.PercentDelta;
import com.fluxtion.ext.declarative.api.stream.StreamFunctions.Sum;
import static com.fluxtion.ext.declarative.builder.event.EventSelect.*;
import static com.fluxtion.ext.declarative.builder.stream.FunctionBuilder.*;
import static com.fluxtion.ext.declarative.builder.stream.StreamBuilder.*;

/**
 * Utility class providing static helper methods to create mapping operations
 * in streams from a set of wrapped functions.
 *
 * This class is autogenerated from executing {@link StreamFunctionGenerator}
 *
 * @author Greg Higgins
 */
public class StreamFunctionsBuilder  {

    /**
     * Wrap {@link Sum#addValue } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Sum#addValue }
     * @return {@link SerializableFunction} of {@link Sum#addValue }
     */
    public static <T extends Number> SerializableFunction<T, Number> cumSum() {
        return new Sum()::addValue;
    }

    /**
     * Performs a {@link Sum#addValue} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Number&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param supplier The input value to the function {@link Sum#addValue
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Sum#addValue}
     */
    public static <T extends Event> Wrapper<Number> cumSum(SerializableFunction<T, Number> supplier) {
        return map(new Sum()::addValue, supplier);
    }

    /**
     * Performs a {@link Sum#addValue} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Number&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Sum#addValue}
     * @param supplier The wrapped instance supplying values to the function {@link Sum#addValue
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Sum#addValue}
     */
    public static <T extends Number> Wrapper<Number>  cumSum(SerializableSupplier<T> supplier) {
        return map(new Sum()::addValue, supplier);
    }

    /**
     * Wrap {@link Average#addValue } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Average#addValue }
     * @return {@link SerializableFunction} of {@link Average#addValue }
     */
    public static <T extends Number> SerializableFunction<T, Number> avg() {
        return new Average()::addValue;
    }

    /**
     * Performs a {@link Average#addValue} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Number&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param supplier The input value to the function {@link Average#addValue
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Average#addValue}
     */
    public static <T extends Event> Wrapper<Number> avg(SerializableFunction<T, Number> supplier) {
        return map(new Average()::addValue, supplier);
    }

    /**
     * Performs a {@link Average#addValue} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Number&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Average#addValue}
     * @param supplier The wrapped instance supplying values to the function {@link Average#addValue
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Average#addValue}
     */
    public static <T extends Number> Wrapper<Number>  avg(SerializableSupplier<T> supplier) {
        return map(new Average()::addValue, supplier);
    }

    /**
     * Wrap {@link Max#max } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Max#max }
     * @return {@link SerializableFunction} of {@link Max#max }
     */
    public static <T extends Number> SerializableFunction<T, Number> max() {
        return new Max()::max;
    }

    /**
     * Performs a {@link Max#max} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Number&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param supplier The input value to the function {@link Max#max
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Max#max}
     */
    public static <T extends Event> Wrapper<Number> max(SerializableFunction<T, Number> supplier) {
        return map(new Max()::max, supplier);
    }

    /**
     * Performs a {@link Max#max} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Number&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Max#max}
     * @param supplier The wrapped instance supplying values to the function {@link Max#max
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Max#max}
     */
    public static <T extends Number> Wrapper<Number>  max(SerializableSupplier<T> supplier) {
        return map(new Max()::max, supplier);
    }

    /**
     * Wrap {@link PercentDelta#value } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link PercentDelta#value }
     * @return {@link SerializableFunction} of {@link PercentDelta#value }
     */
    public static <T extends Number> SerializableFunction<T, Number> percentChange() {
        return new PercentDelta()::value;
    }

    /**
     * Performs a {@link PercentDelta#value} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Number&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param supplier The input value to the function {@link PercentDelta#value
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link PercentDelta#value}
     */
    public static <T extends Event> Wrapper<Number> percentChange(SerializableFunction<T, Number> supplier) {
        return map(new PercentDelta()::value, supplier);
    }

    /**
     * Performs a {@link PercentDelta#value} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Number&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link PercentDelta#value}
     * @param supplier The wrapped instance supplying values to the function {@link PercentDelta#value
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link PercentDelta#value}
     */
    public static <T extends Number> Wrapper<Number>  percentChange(SerializableSupplier<T> supplier) {
        return map(new PercentDelta()::value, supplier);
    }

    /**
     * Wrap {@link Math#ceil } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Math#ceil }
     * @return {@link SerializableFunction} of {@link Math#ceil }
     */
    public static <T extends Double> SerializableFunction<T, Double> ceil() {
        return Math::ceil;
    }

    /**
     * Performs a {@link Math#ceil} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Double&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Double&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param supplier The input value to the function {@link Math#ceil
     * @return {@link  Wrapper&lt;Double&gt;} wrapping the result of {@link Math#ceil}
     */
    public static <T extends Event> Wrapper<Double> ceil(SerializableFunction<T, Double> supplier) {
        return map(Math::ceil, supplier);
    }

    /**
     * Performs a {@link Math#ceil} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Double&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Double&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Math#ceil}
     * @param supplier The wrapped instance supplying values to the function {@link Math#ceil
     * @return {@link  Wrapper&lt;Double&gt;} wrapping the result of {@link Math#ceil}
     */
    public static <T extends Double> Wrapper<Double>  ceil(SerializableSupplier<T> supplier) {
        return map(Math::ceil, supplier);
    }

    /**
     * Wrap {@link Math#floor } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Math#floor }
     * @return {@link SerializableFunction} of {@link Math#floor }
     */
    public static <T extends Double> SerializableFunction<T, Double> floor() {
        return Math::floor;
    }

    /**
     * Performs a {@link Math#floor} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Double&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Double&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param supplier The input value to the function {@link Math#floor
     * @return {@link  Wrapper&lt;Double&gt;} wrapping the result of {@link Math#floor}
     */
    public static <T extends Event> Wrapper<Double> floor(SerializableFunction<T, Double> supplier) {
        return map(Math::floor, supplier);
    }

    /**
     * Performs a {@link Math#floor} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Double&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Double&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Math#floor}
     * @param supplier The wrapped instance supplying values to the function {@link Math#floor
     * @return {@link  Wrapper&lt;Double&gt;} wrapping the result of {@link Math#floor}
     */
    public static <T extends Double> Wrapper<Double>  floor(SerializableSupplier<T> supplier) {
        return map(Math::floor, supplier);
    }


    /**
     * Wrap {@link Count#increment } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Count#increment }
     * @return {@link SerializableFunction} of {@link Count#increment }
     */
    public static <T> SerializableFunction<T, Number> count() {
        return new Count()::increment;
    }

    /**
     * Performs a {@link Count#increment} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;T&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Count#increment}
     */
    public static <T extends Event> Wrapper<Number> count(Class<T> eventClass) {
        return select(eventClass).map(new Count()::increment);
    }

    /**
     * Performs a {@link Count#increment} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;T&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Count#increment}
     * @param supplier The wrapped instance supplying values to the function {@link Count#increment
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Count#increment}
     */
    public static <T> Wrapper<Number> count(T supplier) {
        return stream(supplier).map(new Count()::increment);
    }


}
