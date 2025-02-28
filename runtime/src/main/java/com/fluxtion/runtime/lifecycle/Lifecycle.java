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
package com.fluxtion.runtime.lifecycle;

/**
 * Lifecycle events that are issued by a Static event processor. Any node in the
 * execution graph can implement this interface and will receive the relevant
 * callbacks.
 *
 * These notifications are generally used to intitialise and teardown a node in
 * the graph before any events have been received. The static event processor
 * guarantees:
 * <ul>
 * <li>the init method will be called before any events are processed by the SEP
 * <li>the teardown method will be called after the sep is closed down
 * <li>Init methods are invoked in topological order
 * <li>teardown methods are invoked in reverse-topological order
 * </ul>
 *
 * @author Greg Higgins
 */
public interface Lifecycle {

    /**
     * callback received before any events are processed by the Static event
     * processor. Init methods are invoked in topological order.
     */
    void init();

    /**
     * callback received after all events are processed by the Static event
     * processor, and no more are expected. tearDown methods are invoked in
     * reverse-topological order.
     */
    void tearDown();
}
