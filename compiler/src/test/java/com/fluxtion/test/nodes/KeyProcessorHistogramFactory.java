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
package com.fluxtion.test.nodes;

import com.fluxtion.compiler.builder.factory.NodeFactory;
import com.fluxtion.compiler.builder.factory.NodeRegistry;
import java.util.Map;

/**
 *
 * @author Greg Higgins
 */
public class KeyProcessorHistogramFactory implements NodeFactory<KeyProcessorHistogram>{

    public static final String KEY_NUMERIC = KeyProcessorHistogramFactory.class.getName() + ".KEY_NUMERIC";
    public static final String KEY_ALPHA = KeyProcessorHistogramFactory.class.getName() + ".KEY_NUMERIC";
    private KeyProcessorHistogram histo;
    
    @Override
    public KeyProcessorHistogram createNode(Map config, NodeRegistry registry) {
        if(histo==null){
            histo = new KeyProcessorHistogram();
            int count = 10;
            histo.monitoredKeys = new KeyProcessor[count];
            
            for (int i = 0; i < count; i++) {
                config.put(KeyProcessorFactory.KEY_CHAR, (char)(i + '0'));
                histo.monitoredKeys[i] = registry.findOrCreateNode(KeyProcessor.class, config, null);
                registry.findOrCreateNode(KeyProcessor.class, config, null);
            }
            
            histo.accumulator = registry.findOrCreateNode(Accumulator.class, config, null);
        }
        return histo;
    }
    
}
