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
 * Server Side Public License for more details.
 *
 * You should have received a copy of the Server Side Public License
 * along with this program.  If not, see 
 * <http://www.mongodb.com/licensing/server-side-public-license>.
 */
package com.fluxtion.ext.futext.builder.csv;

import com.fluxtion.ext.declarative.api.Wrapper;
import static com.fluxtion.ext.declarative.builder.test.BooleanBuilder.and;
import static com.fluxtion.ext.declarative.builder.test.BooleanBuilder.filterMatch;
import static com.fluxtion.ext.declarative.builder.test.BooleanBuilder.nand;
import static com.fluxtion.ext.declarative.builder.test.BooleanBuilder.not;
import static com.fluxtion.ext.declarative.builder.test.TestBuilder.buildTest;
import com.fluxtion.ext.declarative.builder.util.LambdaReflection.SerializableConsumer;
import com.fluxtion.ext.declarative.builder.util.LambdaReflection.SerializableSupplier;
import com.fluxtion.ext.futext.api.csv.RowProcessor;
import com.fluxtion.ext.futext.api.csv.RulesEvaluator;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javafx.util.Pair;

/**
 * A RulesEvaluator aggregates a set of rules and reports success if all rules
 * are valid.
 *
 * @author V12 Technology Ltd.
 */
public class RulesEvaluatorBuilder<T> {

    public static <T> BuilderWrapper<T> validate(Wrapper<T> bean) {
        return new BuilderWrapper<T>(bean);
    }

    public static <T> Builder<T> validate(T bean) {
        return new Builder<T>(bean);
    }

    public static <T> BuilderRowProcessor<T> validate(RowProcessor<T> bean) {
        return new BuilderRowProcessor<T>(bean);
    }
    
    

    public static class BuilderRowProcessor<T> {

        private Wrapper<T> monitoredWrapped;
        private List<Pair<SerializableConsumer, Function<T, ?>>> ruleList;

        public BuilderRowProcessor(Wrapper<T> monitored) {
            this.monitoredWrapped = monitored;
            ruleList = new ArrayList<>();
        }

        public <R> BuilderRowProcessor<T> addRule(SerializableConsumer<? extends R> rule, Function<T, R> supplier) {
            ruleList.add(new Pair(rule, supplier));
            return this;
        }

        public <R> RulesEvaluator<T> build() {
            //and all rules and pass through boolean filter
            List testList = new ArrayList();
            for (Pair<SerializableConsumer, Function<T, ?>> pair : ruleList) {
                SerializableConsumer<? extends R> rule = pair.getKey();
                Function<T, R> supplier = (Function<T, R>) pair.getValue();
                testList.add(buildTest(rule, monitoredWrapped, supplier).build());
            }
            RulesEvaluator<T> evaluator = new RulesEvaluator<>(
                    filterMatch(monitoredWrapped, and(testList.toArray())),
                    filterMatch(monitoredWrapped, nand(testList.toArray()))
            );
            return evaluator;
        }
    }

    public static class BuilderWrapper<T> {

        private Wrapper<T> monitoredWrapped;
        private List<Pair<SerializableConsumer, Function<T, ?>>> ruleList;

        public BuilderWrapper(Wrapper<T> monitored) {
            this.monitoredWrapped = monitored;
            ruleList = new ArrayList<>();
        }

        public <R> BuilderWrapper<T> addRule(SerializableConsumer<? extends R> rule, Function<T, R> supplier) {
            ruleList.add(new Pair(rule, supplier));
            return this;
        }

        public <R> RulesEvaluator<T> build() {
            //and all rules and pass through boolean filter
            List testList = new ArrayList();
            for (Pair<SerializableConsumer, Function<T, ?>> pair : ruleList) {
                SerializableConsumer<? extends R> rule = pair.getKey();
                Function<T, R> supplier = (Function<T, R>) pair.getValue();
                testList.add(buildTest(rule, monitoredWrapped, supplier).build());
            }
            RulesEvaluator<T> evaluator = new RulesEvaluator<>(
                    filterMatch(monitoredWrapped, and(testList.toArray())),
                    filterMatch(monitoredWrapped, nand(testList.toArray()))
            );
            return evaluator;
        }
    }

    public static class Builder<T> {

        private final T monitored;
        private List<Pair<SerializableConsumer, SerializableSupplier<T, ?>>> ruleList;

        public Builder(T monitored) {
            this.monitored = monitored;
            ruleList = new ArrayList<>();
        }

        public <R> Builder<T> addRule(SerializableConsumer<? extends R> rule, SerializableSupplier<T, R> supplier) {
            ruleList.add(new Pair(rule, supplier));
            return this;
        }

        public <R> RulesEvaluator<T> build() {
            List testList = new ArrayList();
            for (Pair<SerializableConsumer, SerializableSupplier<T, ?>> pair : ruleList) {
                SerializableConsumer<? extends R> rule = pair.getKey();
                SerializableSupplier<T, R> supplier = (SerializableSupplier<T, R>) pair.getValue();
                testList.add(not(buildTest(rule, supplier).build()));
            }
            RulesEvaluator<T> evaluator = new RulesEvaluator<>(
                    filterMatch(monitored, and(testList.toArray())),
                    filterMatch(monitored, nand(testList.toArray()))
            );
            return evaluator;
        }
    }

}