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

import com.fluxtion.api.lifecycle.EventHandler;
import com.fluxtion.builder.node.SEPConfig;
import com.fluxtion.ext.futext.api.util.marshaller.DispatchingCsvMarshaller;
import static com.fluxtion.ext.futext.builder.csv.CsvMarshallerBuilder.csvMarshaller;
import com.fluxtion.generator.compiler.InprocessSepCompiler;
import static com.fluxtion.generator.compiler.InprocessSepCompiler.sepInstance;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Creates multiple SEP processors marshalling CSV to bean instances of multiple
 * types. The bean classes configured will all push their marshalled instances
 * to a single {@link DispatchingCsvMarshaller} for multiplexed dispatch. A
 * single instance of this class can be used to generate multiple SEP
 * processors, compared to using multiple {@link SEPConfig} classes.
 *
 * @author V12 Technology Ltd.
 */
public class CsvToBeanBuilder {

    private final String pckg;
    private HashMap<Class, EventHandler> clazz2Handler;
    private String resorcesDir;
    private String generatedDir;

    public CsvToBeanBuilder(String pckg) {
        this.pckg = pckg;
        this.resorcesDir = InprocessSepCompiler.RESOURCE_DIR;
        this.generatedDir = InprocessSepCompiler.JAVA_GEN_DIR;
        clazz2Handler = new HashMap<>();
    }

    /**
     * Create a generation context for a CSV to bean marshaller context.
     *
     * @param pkg unique namespace for the generated marshallers.
     * @return CsvToBeanBuilder for fluent api calls.
     */
    public static CsvToBeanBuilder nameSpace(String pkg) {
        return new CsvToBeanBuilder(pkg);
    }

    public CsvToBeanBuilder testOutpur(boolean deployAsTest) {
        if (deployAsTest) {
            this.resorcesDir = InprocessSepCompiler.RESOURCE_TEST_DIR;
            this.generatedDir = InprocessSepCompiler.JAVA_TESTGEN_DIR;
        } else {
            this.resorcesDir = InprocessSepCompiler.RESOURCE_DIR;
            this.generatedDir = InprocessSepCompiler.JAVA_GEN_DIR;
        }
        return this;
    }

    public CsvToBeanBuilder setOutputDirs(String generatedDir, String resorcesDir) {
        this.resorcesDir = resorcesDir;
        this.generatedDir = generatedDir;
        return this;
    }

    /**
     * Map a bean with no customisation or validation. The namespace of the
     * CsvToBeanBuilder is the union of {@link #nameSpace(java.lang.String) ) and
     * the marshallerId.
     *
     * @param marshallerId a unique identifier for the generated marshaller
     * @param clazz
     * @return
     */
    public CsvToBeanBuilder mapBean(String marshallerId, Class clazz) {
        try {
            String cap = marshallerId.substring(0, 1).toUpperCase() + marshallerId.substring(1) + "CsvBean";
            EventHandler sep = sepInstance((cfg) -> csvMarshaller(clazz).build(), pckg + "." + marshallerId, cap, generatedDir, resorcesDir, true);
            clazz2Handler.put(clazz, sep);
            return this;
        } catch (Exception ex) {
            throw new RuntimeException("failed to map bean class:" + clazz, ex);
        }
    }

    /**
     * Map a bean with a set of validation rules added to the provided
     * {@link RulesEvaluatorBuilder.BuilderRowProcessor} by the caller.
     *
     * @param <T>
     * @param marshallerId
     * @param clazz
     * @param ruleGenerator
     * @return
     */
    public <T> CsvToBeanBuilder mapBean(String marshallerId, Class<T> clazz, Consumer<RulesEvaluatorBuilder.BuilderRowProcessor<T>> ruleGenerator) {
        try {
            String cap = marshallerId.substring(0, 1).toUpperCase() + marshallerId.substring(1) + "CsvBean";
            EventHandler sep = sepInstance(new Consumer<SEPConfig>() {
                @Override
                public void accept(SEPConfig cfg) {
                    RulesEvaluatorBuilder.BuilderRowProcessor<T> validator1 = RulesEvaluatorBuilder.validator(csvMarshaller(clazz).build());
                    ruleGenerator.accept(validator1);
                    validator1.build();
                }
            }, pckg + "." + marshallerId, cap, generatedDir, resorcesDir, true);
            clazz2Handler.put(clazz, sep);
            return this;
        } catch (Exception ex) {
            throw new RuntimeException("failed to map bean class:" + clazz, ex);
        }
    }

    public <T> CsvToBeanBuilder mapCustomBean(String marshallerId, Class<T> clazz, Consumer<CsvMarshallerBuilder<T>> ruleGenerator) {
        try {
            String cap = marshallerId.substring(0, 1).toUpperCase() + marshallerId.substring(1) + "CsvBean";
            EventHandler sep = sepInstance(new Consumer<SEPConfig>() {
                @Override
                public void accept(SEPConfig cfg) {
                    CsvMarshallerBuilder<T> builder = csvMarshaller(clazz);
                    ruleGenerator.accept(builder);
                    builder.build();
                }
            }, pckg + "." + marshallerId, cap, generatedDir, resorcesDir, true);
            clazz2Handler.put(clazz, sep);
            return this;
        } catch (Exception ex) {
            throw new RuntimeException("failed to map bean class:" + clazz, ex);
        }
    }

    public DispatchingCsvMarshaller build() {
        DispatchingCsvMarshaller dispatcher = new DispatchingCsvMarshaller();
        clazz2Handler.forEach(dispatcher::addMarshaller);
        return dispatcher;
    }

    public DispatchingCsvMarshaller build(EventHandler sink) {
        DispatchingCsvMarshaller dispatcher = new DispatchingCsvMarshaller();
        clazz2Handler.forEach(dispatcher::addMarshaller);
        dispatcher.addSink(sink);
        return dispatcher;
    }
}
