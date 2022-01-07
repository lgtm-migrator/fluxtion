/* 
 * Copyright (C) 2018 V12 Technology Ltd.
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
package com.fluxtion.compiler.builder.node;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Provides data driven SEP generation. 
 * 
 * <h2>Doc to be completed</h2>
 * @author Greg Higgins
 */
public final class DeclarativeNodeConfiguration {

    /**
     * The root nodes to create and the variable names they should be mapped to.
     * 
     */
    public final Map<Class<?>, String> rootNodeMappings;

    /**
     * The set of factory classes used for node creation, each factory must
     * have default constructor so the SEP can instantiate the factory. 
     * The classes in factoryClassSet are instantiated and merged into the 
     * factorySet instances.
     */
    public final Set<Class<? extends NodeFactory<?>>> factoryClassSet;

    /**
     * The factory instances registered that can create new instances of
     * nodes.
     */
    public final Set<NodeFactory<?>> factorySet;


    /**
     * the configuration used to generate the nodes
     */
    public final Map config;

    public DeclarativeNodeConfiguration(Map<Class<?>, String> rootNodeMappings, Set<Class<? extends NodeFactory<?>>> factoryList, Map config) {
        this(rootNodeMappings, factoryList, config, null);
    }

    /**
     * 
     * @param rootNodeMappings
     * @param factoryList
     * @param config
     * @param factorySet 
     */
    @SuppressWarnings("unchecked")
    public DeclarativeNodeConfiguration(Map<Class<?>, String> rootNodeMappings, Set<Class<? extends NodeFactory<?>>> factoryList, Map config, Set<NodeFactory<?>> factorySet) {
        this.rootNodeMappings = rootNodeMappings == null ? Collections.EMPTY_MAP : rootNodeMappings;
        this.factoryClassSet = factoryList == null ? new HashSet<>() : factoryList;
        this.factorySet = factorySet == null ? new HashSet<>() : factorySet;
        this.config = config == null ? Collections.EMPTY_MAP : config;
        //this.proxyClassMap = new HashMap<>();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.rootNodeMappings);
        hash = 89 * hash + Objects.hashCode(this.factoryClassSet);
        hash = 89 * hash + Objects.hashCode(this.factorySet);
        hash = 89 * hash + Objects.hashCode(this.config);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeclarativeNodeConfiguration other = (DeclarativeNodeConfiguration) obj;
        if (!Objects.equals(this.rootNodeMappings, other.rootNodeMappings)) {
            return false;
        }
        if (!Objects.equals(this.factoryClassSet, other.factoryClassSet)) {
            return false;
        }
        if (!Objects.equals(this.factorySet, other.factorySet)) {
            return false;
        }
        if (!Objects.equals(this.config, other.config)) {
            return false;
        }
        return true;
    }

    
}
