/* 
 * Copyright (c) 2019, V12 Technology Ltd.
 * All rights reserved.
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
package com.fluxtion.generator.compiler;

import com.fluxtion.builder.generation.GenerationContext;
import java.net.URL;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fluxtion.builder.annotation.ClassProcessor;
import java.io.File;
import java.util.function.BiConsumer;

/**
 * A utility function that dispatches a {@link URL} for {@link ClassProcessor}
 * to process. Uses {@link ServiceLoader> } facility to load user created
 * processors at run-time. The loaded ClassProcessor can examine and generate
 * artifacts as necessary. User
 *
 * @author V12 Technology Ltd.
 */
public class ClassProcessorDispatcher implements BiConsumer<URL, File> {

    static final Logger LOGGER = LoggerFactory.getLogger(ClassProcessorDispatcher.class);

    @Override
    public void accept(URL url, File baseDir) {
        LOGGER.debug("AnnotationProcessor locator");
        ServiceLoader<ClassProcessor> loadServices;
        Set<Class<? extends ClassProcessor>> subTypes = new HashSet<>();
        if (GenerationContext.SINGLETON != null && GenerationContext.SINGLETON.getClassLoader() != null) {
            LOGGER.debug("using custom class loader to search for factories");
            loadServices = ServiceLoader.load(ClassProcessor.class, GenerationContext.SINGLETON.getClassLoader());
        } else {
            LOGGER.debug("loading services through class loader for this class");
            loadServices = ServiceLoader.load(ClassProcessor.class, this.getClass().getClassLoader());
        }
        loadServices.forEach((t) -> {
            subTypes.add(t.getClass());
        });
        LOGGER.debug("loaded AnnotationProcessors: {}", subTypes);
        final File outDir;
        final File resDir;
        if (GenerationContext.SINGLETON != null && GenerationContext.SINGLETON.getSourceRootDirectory() != null) {
            outDir = GenerationContext.SINGLETON.getSourceRootDirectory();
        } else {
            outDir = new File(baseDir, "target/generated-sources/fluxtion");
        }
        if (GenerationContext.SINGLETON != null && GenerationContext.SINGLETON.getResourcesRootDirectory() != null) {
            resDir = GenerationContext.SINGLETON.getResourcesRootDirectory();
        } else {
            resDir = new File(baseDir, "src/main/resources");
        }
        loadServices.forEach(new Consumer<ClassProcessor>() {
            @Override
            public void accept(ClassProcessor t) {
                try {
                    t.outputDirectories(baseDir, outDir, resDir);
                    t.process(url);
                } catch (Exception e) {
                    LOGGER.warn("problem executing processor : '" + t + "'", e);
                }
            }
        });
    }

}
