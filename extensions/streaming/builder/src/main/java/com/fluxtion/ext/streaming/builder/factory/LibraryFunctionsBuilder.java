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
package com.fluxtion.ext.streaming.builder.factory;
import com.fluxtion.api.event.Event;
import com.fluxtion.api.partition.LambdaReflection.SerializableBiFunction;
import com.fluxtion.api.partition.LambdaReflection.SerializableFunction;
import com.fluxtion.api.partition.LambdaReflection.SerializableSupplier;
import com.fluxtion.ext.streaming.api.Wrapper;
import com.fluxtion.ext.streaming.api.stream.Argument;
import com.fluxtion.ext.streaming.api.stream.StreamFunctions;
import com.fluxtion.ext.streaming.api.stream.StreamFunctions.Average;
import com.fluxtion.ext.streaming.api.stream.StreamFunctions.Count;
import com.fluxtion.ext.streaming.api.stream.StreamFunctions.Delta;
import com.fluxtion.ext.streaming.api.stream.StreamFunctions.Max;
import com.fluxtion.ext.streaming.api.stream.StreamFunctions.Min;
import com.fluxtion.ext.streaming.api.stream.StreamFunctions.PercentDelta;
import com.fluxtion.ext.streaming.api.stream.StreamFunctions.Sum;
import com.fluxtion.ext.streaming.builder.util.StreamFunctionGenerator;
import static com.fluxtion.ext.streaming.api.stream.Argument.*;
import static com.fluxtion.ext.streaming.builder.factory.EventSelect.*;
import static com.fluxtion.ext.streaming.builder.factory.MappingBuilder.*;
import static com.fluxtion.ext.streaming.builder.stream.StreamOperatorService.*;

/**
 * Utility class providing static helper methods to create mapping operations
 * in streams from a set of wrapped functions.
 *
 * This class is autogenerated from executing {@link StreamFunctionGenerator}
 *
 * @author Greg Higgins
 */
public class LibraryFunctionsBuilder  {


    public static <T extends Double, S extends Double> SerializableBiFunction<T, S, Number> add() {
        return StreamFunctions::add;
    }

    public static <T, S> Wrapper<Number> add(SerializableFunction<T, Number> supplier1, SerializableFunction<S, Number> supplier2) {
        return map(add(), arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> add(SerializableSupplier<T> supplier1, SerializableSupplier<T> supplier2) {
        return map(add(), arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> add(Argument<T> arg1, Argument<S> arg2) {
        return map(add(), arg1, arg2);
    }

    public static <T extends Number, S extends Number> Wrapper<Number> add(Wrapper<T> wrapper1, Wrapper<S> wrapper2) {
        return map(add(), arg(wrapper1), arg(wrapper2));
    }

    public static <T, S extends Number> Wrapper<Number> add(SerializableFunction<T, Number> supplier1, Wrapper<S> supplier2) {
        return map(add(), arg(supplier1), arg(supplier2));
    }
    
    public static <T extends Number, S> Wrapper<Number>  add( Wrapper<T> supplier1, SerializableFunction<S, Number> supplier2) {
        return map(add(), arg(supplier1), arg(supplier2));
    }


    public static <T extends Double, S extends Double> SerializableBiFunction<T, S, Number> subtract() {
        return StreamFunctions::subtract;
    }

    public static <T, S> Wrapper<Number> subtract(SerializableFunction<T, Number> supplier1, SerializableFunction<S, Number> supplier2) {
        return map(subtract(), arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> subtract(SerializableSupplier<T> supplier1, SerializableSupplier<T> supplier2) {
        return map(subtract(), arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> subtract(Argument<T> arg1, Argument<S> arg2) {
        return map(subtract(), arg1, arg2);
    }

    public static <T extends Number, S extends Number> Wrapper<Number> subtract(Wrapper<T> wrapper1, Wrapper<S> wrapper2) {
        return map(subtract(), arg(wrapper1), arg(wrapper2));
    }

    public static <T, S extends Number> Wrapper<Number> subtract(SerializableFunction<T, Number> supplier1, Wrapper<S> supplier2) {
        return map(subtract(), arg(supplier1), arg(supplier2));
    }
    
    public static <T extends Number, S> Wrapper<Number>  subtract( Wrapper<T> supplier1, SerializableFunction<S, Number> supplier2) {
        return map(subtract(), arg(supplier1), arg(supplier2));
    }


    public static <T extends Double, S extends Double> SerializableBiFunction<T, S, Number> multiply() {
        return StreamFunctions::multiply;
    }

    public static <T, S> Wrapper<Number> multiply(SerializableFunction<T, Number> supplier1, SerializableFunction<S, Number> supplier2) {
        return map(multiply(), arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> multiply(SerializableSupplier<T> supplier1, SerializableSupplier<T> supplier2) {
        return map(multiply(), arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> multiply(Argument<T> arg1, Argument<S> arg2) {
        return map(multiply(), arg1, arg2);
    }

    public static <T extends Number, S extends Number> Wrapper<Number> multiply(Wrapper<T> wrapper1, Wrapper<S> wrapper2) {
        return map(multiply(), arg(wrapper1), arg(wrapper2));
    }

    public static <T, S extends Number> Wrapper<Number> multiply(SerializableFunction<T, Number> supplier1, Wrapper<S> supplier2) {
        return map(multiply(), arg(supplier1), arg(supplier2));
    }
    
    public static <T extends Number, S> Wrapper<Number>  multiply( Wrapper<T> supplier1, SerializableFunction<S, Number> supplier2) {
        return map(multiply(), arg(supplier1), arg(supplier2));
    }


    public static <T extends Double, S extends Double> SerializableBiFunction<T, S, Number> divide() {
        return StreamFunctions::divide;
    }

    public static <T, S> Wrapper<Number> divide(SerializableFunction<T, Number> supplier1, SerializableFunction<S, Number> supplier2) {
        return map(divide(), arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> divide(SerializableSupplier<T> supplier1, SerializableSupplier<T> supplier2) {
        return map(divide(), arg(supplier1), arg(supplier2));
    }

    public static <T extends Number, S extends Number> Wrapper<Number> divide(Argument<T> arg1, Argument<S> arg2) {
        return map(divide(), arg1, arg2);
    }

    public static <T extends Number, S extends Number> Wrapper<Number> divide(Wrapper<T> wrapper1, Wrapper<S> wrapper2) {
        return map(divide(), arg(wrapper1), arg(wrapper2));
    }

    public static <T, S extends Number> Wrapper<Number> divide(SerializableFunction<T, Number> supplier1, Wrapper<S> supplier2) {
        return map(divide(), arg(supplier1), arg(supplier2));
    }
    
    public static <T extends Number, S> Wrapper<Number>  divide( Wrapper<T> supplier1, SerializableFunction<S, Number> supplier2) {
        return map(divide(), arg(supplier1), arg(supplier2));
    }


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
     * @param <S> The function return type
     * @param supplier The input value to the function {@link Sum#addValue
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Sum#addValue}
     */
    public static <T, S extends Number> Wrapper<Number> cumSum(SerializableFunction<T, S> supplier) {
        return map(cumSum(), arg(supplier));
    }

    public static <T extends Number> Wrapper<Number> cumSum(Argument<T> arg) {
        return map(cumSum(), arg);
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
    public static <T extends Number> Wrapper<Number> cumSum(SerializableSupplier<T> supplier) {
        return map(cumSum(), arg(supplier));
    }

    public static <T, S extends Number> Wrapper<Number> cumSum(Wrapper<T> wrapper, SerializableFunction<T, S> supplier) {
        return map(cumSum(),  arg(wrapper, supplier));
    }

    public static <T extends Number> Wrapper<Number> cumSum(Wrapper<T> wrapper) {
        return map(cumSum(),  arg(wrapper));
    }

    /**
     * Wrap {@link Average#addValue } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Average#addValue }
     * @return {@link SerializableFunction} of {@link Average#addValue }
     */
    public static <T extends Double> SerializableFunction<T, Number> avg() {
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
     * @param <S> The function return type
     * @param supplier The input value to the function {@link Average#addValue
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Average#addValue}
     */
    public static <T, S extends Number> Wrapper<Number> avg(SerializableFunction<T, S> supplier) {
        return map(avg(), arg(supplier));
    }

    public static <T extends Number> Wrapper<Number> avg(Argument<T> arg) {
        return map(avg(), arg);
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
    public static <T extends Number> Wrapper<Number> avg(SerializableSupplier<T> supplier) {
        return map(avg(), arg(supplier));
    }

    public static <T, S extends Number> Wrapper<Number> avg(Wrapper<T> wrapper, SerializableFunction<T, S> supplier) {
        return map(avg(),  arg(wrapper, supplier));
    }

    public static <T extends Number> Wrapper<Number> avg(Wrapper<T> wrapper) {
        return map(avg(),  arg(wrapper));
    }

    /**
     * Wrap {@link Max#max } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Max#max }
     * @return {@link SerializableFunction} of {@link Max#max }
     */
    public static <T extends Double> SerializableFunction<T, Number> max() {
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
     * @param <S> The function return type
     * @param supplier The input value to the function {@link Max#max
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Max#max}
     */
    public static <T, S extends Number> Wrapper<Number> max(SerializableFunction<T, S> supplier) {
        return map(max(), arg(supplier));
    }

    public static <T extends Number> Wrapper<Number> max(Argument<T> arg) {
        return map(max(), arg);
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
    public static <T extends Number> Wrapper<Number> max(SerializableSupplier<T> supplier) {
        return map(max(), arg(supplier));
    }

    public static <T, S extends Number> Wrapper<Number> max(Wrapper<T> wrapper, SerializableFunction<T, S> supplier) {
        return map(max(),  arg(wrapper, supplier));
    }

    public static <T extends Number> Wrapper<Number> max(Wrapper<T> wrapper) {
        return map(max(),  arg(wrapper));
    }

    /**
     * Wrap {@link Min#min } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Min#min }
     * @return {@link SerializableFunction} of {@link Min#min }
     */
    public static <T extends Double> SerializableFunction<T, Number> min() {
        return new Min()::min;
    }

    /**
     * Performs a {@link Min#min} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Number&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param <S> The function return type
     * @param supplier The input value to the function {@link Min#min
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Min#min}
     */
    public static <T, S extends Number> Wrapper<Number> min(SerializableFunction<T, S> supplier) {
        return map(min(), arg(supplier));
    }

    public static <T extends Number> Wrapper<Number> min(Argument<T> arg) {
        return map(min(), arg);
    }

    /**
     * Performs a {@link Min#min} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Number&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Min#min}
     * @param supplier The wrapped instance supplying values to the function {@link Min#min
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Min#min}
     */
    public static <T extends Number> Wrapper<Number> min(SerializableSupplier<T> supplier) {
        return map(min(), arg(supplier));
    }

    public static <T, S extends Number> Wrapper<Number> min(Wrapper<T> wrapper, SerializableFunction<T, S> supplier) {
        return map(min(),  arg(wrapper, supplier));
    }

    public static <T extends Number> Wrapper<Number> min(Wrapper<T> wrapper) {
        return map(min(),  arg(wrapper));
    }

    /**
     * Wrap {@link PercentDelta#value } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link PercentDelta#value }
     * @return {@link SerializableFunction} of {@link PercentDelta#value }
     */
    public static <T extends Double> SerializableFunction<T, Number> percentChange() {
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
     * @param <S> The function return type
     * @param supplier The input value to the function {@link PercentDelta#value
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link PercentDelta#value}
     */
    public static <T, S extends Number> Wrapper<Number> percentChange(SerializableFunction<T, S> supplier) {
        return map(percentChange(), arg(supplier));
    }

    public static <T extends Number> Wrapper<Number> percentChange(Argument<T> arg) {
        return map(percentChange(), arg);
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
    public static <T extends Number> Wrapper<Number> percentChange(SerializableSupplier<T> supplier) {
        return map(percentChange(), arg(supplier));
    }

    public static <T, S extends Number> Wrapper<Number> percentChange(Wrapper<T> wrapper, SerializableFunction<T, S> supplier) {
        return map(percentChange(),  arg(wrapper, supplier));
    }

    public static <T extends Number> Wrapper<Number> percentChange(Wrapper<T> wrapper) {
        return map(percentChange(),  arg(wrapper));
    }

    /**
     * Wrap {@link Delta#value } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Delta#value }
     * @return {@link SerializableFunction} of {@link Delta#value }
     */
    public static <T extends Double> SerializableFunction<T, Number> delta() {
        return new Delta()::value;
    }

    /**
     * Performs a {@link Delta#value} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Number&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param <S> The function return type
     * @param supplier The input value to the function {@link Delta#value
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Delta#value}
     */
    public static <T, S extends Number> Wrapper<Number> delta(SerializableFunction<T, S> supplier) {
        return map(delta(), arg(supplier));
    }

    public static <T extends Number> Wrapper<Number> delta(Argument<T> arg) {
        return map(delta(), arg);
    }

    /**
     * Performs a {@link Delta#value} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Number&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Delta#value}
     * @param supplier The wrapped instance supplying values to the function {@link Delta#value
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Delta#value}
     */
    public static <T extends Number> Wrapper<Number> delta(SerializableSupplier<T> supplier) {
        return map(delta(), arg(supplier));
    }

    public static <T, S extends Number> Wrapper<Number> delta(Wrapper<T> wrapper, SerializableFunction<T, S> supplier) {
        return map(delta(),  arg(wrapper, supplier));
    }

    public static <T extends Number> Wrapper<Number> delta(Wrapper<T> wrapper) {
        return map(delta(),  arg(wrapper));
    }

    /**
     * Wrap {@link StreamFunctions#asDouble } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link StreamFunctions#asDouble }
     * @return {@link SerializableFunction} of {@link StreamFunctions#asDouble }
     */
    public static <T extends Double> SerializableFunction<T, Number> double2Num() {
        return StreamFunctions::asDouble;
    }

    /**
     * Wrap {@link Math#ceil } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Math#ceil }
     * @return {@link SerializableFunction} of {@link Math#ceil }
     */
    public static <T extends Double> SerializableFunction<T, Number> ceil() {
        return Math::ceil;
    }

    /**
     * Performs a {@link Math#ceil} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Number&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param <S> The function return type
     * @param supplier The input value to the function {@link Math#ceil
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Math#ceil}
     */
    public static <T, S extends Number> Wrapper<Number> ceil(SerializableFunction<T, S> supplier) {
        return map(ceil(), arg(supplier));
    }

    public static <T extends Number> Wrapper<Number> ceil(Argument<T> arg) {
        return map(ceil(), arg);
    }

    /**
     * Performs a {@link Math#ceil} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Number&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Math#ceil}
     * @param supplier The wrapped instance supplying values to the function {@link Math#ceil
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Math#ceil}
     */
    public static <T extends Number> Wrapper<Number> ceil(SerializableSupplier<T> supplier) {
        return map(ceil(), arg(supplier));
    }

    public static <T, S extends Number> Wrapper<Number> ceil(Wrapper<T> wrapper, SerializableFunction<T, S> supplier) {
        return map(ceil(),  arg(wrapper, supplier));
    }

    public static <T extends Number> Wrapper<Number> ceil(Wrapper<T> wrapper) {
        return map(ceil(),  arg(wrapper));
    }

    /**
     * Wrap {@link Math#floor } function for use as a map operation in an existing
     * stream. {@link Wrapper#map(SerializableFunction) }
     * requires a {@link SerializableFunction} to map input values.
     *
     * @param <T> input to {@link Math#floor }
     * @return {@link SerializableFunction} of {@link Math#floor }
     */
    public static <T extends Double> SerializableFunction<T, Number> floor() {
        return Math::floor;
    }

    /**
     * Performs a {@link Math#floor} function as a map operation on a stream.
     * The stream is automatically created by subscribing to the {@link Event}
     * and wrapping the supplier function with {@link Wrapper&lt;Number&gt;}. 
     * The wrapper is the input to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input event stream
     * @param <S> The function return type
     * @param supplier The input value to the function {@link Math#floor
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Math#floor}
     */
    public static <T, S extends Number> Wrapper<Number> floor(SerializableFunction<T, S> supplier) {
        return map(floor(), arg(supplier));
    }

    public static <T extends Number> Wrapper<Number> floor(Argument<T> arg) {
        return map(floor(), arg);
    }

    /**
     * Performs a {@link Math#floor} function as a map operation on a stream.
     * The stream is automatically created by wrapping the supplier instance function in a
     * {@link Wrapper&lt;Number&gt;}, the wrapper is the input 
     * to the mapping function. The mapped value is available as
     * a {@link Wrapper&lt;Number&gt;} instance for further stream operations.
     *
     * @param <T> The input type required by {@link Math#floor}
     * @param supplier The wrapped instance supplying values to the function {@link Math#floor
     * @return {@link  Wrapper&lt;Number&gt;} wrapping the result of {@link Math#floor}
     */
    public static <T extends Number> Wrapper<Number> floor(SerializableSupplier<T> supplier) {
        return map(floor(), arg(supplier));
    }

    public static <T, S extends Number> Wrapper<Number> floor(Wrapper<T> wrapper, SerializableFunction<T, S> supplier) {
        return map(floor(),  arg(wrapper, supplier));
    }

    public static <T extends Number> Wrapper<Number> floor(Wrapper<T> wrapper) {
        return map(floor(),  arg(wrapper));
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
    public static <T> Wrapper<Number> count(Class<T> eventClass) {
        return select(eventClass).map(count());
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
        return stream(supplier).map(count());
    }


}

