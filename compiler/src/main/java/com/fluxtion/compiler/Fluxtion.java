package com.fluxtion.compiler;

import com.fluxtion.compiler.builder.node.SEPConfig;
import com.fluxtion.compiler.generation.compiler.InProcessSepCompiler;
import com.fluxtion.runtim.StaticEventProcessor;
import com.fluxtion.runtim.lifecycle.Lifecycle;
import com.fluxtion.runtim.partition.LambdaReflection.SerializableConsumer;
import lombok.SneakyThrows;

/**
 * Entry point for generating a {@link StaticEventProcessor}
 */
public interface Fluxtion {

    /**
     * Generates and compiles Java source code for a {@link StaticEventProcessor}. The compiled version only requires
     * the Fluxtion runtime dependencies to operate and process events.
     *
     * {@link Lifecycle#init()} has not been called on the returned instance. The caller must invoke init before
     * sending events to the processor using {@link StaticEventProcessor#onEvent(Object)}
     *
     * @see SEPConfig
     * @param sepConfig the configuration used to build this {@link StaticEventProcessor}
     * @return An uninitialized instance of a {@link StaticEventProcessor}
     */
    @SneakyThrows
    static StaticEventProcessor compile(SerializableConsumer<SEPConfig> sepConfig){
       return InProcessSepCompiler.compile(sepConfig);
    }

    /**
     * Generates an in memory version of a {@link StaticEventProcessor}. The in memory version is transient and requires
     * the runtime and compiler Fluxtion libraries to operate.
     *
     * {@link Lifecycle#init()} has not been called on the returned instance. The caller must invoke init before
     * sending events to the processor using {@link StaticEventProcessor#onEvent(Object)}
     *
     * @see SEPConfig
     * @param sepConfig the configuration used to build this {@link StaticEventProcessor}
     * @return An uninitialized instance of a {@link StaticEventProcessor}
     */
    static StaticEventProcessor interpret(SerializableConsumer<SEPConfig> sepConfig){
        return InProcessSepCompiler.interpreted(sepConfig);
    }
}