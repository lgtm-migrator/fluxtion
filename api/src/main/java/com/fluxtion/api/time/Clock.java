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
package com.fluxtion.api.time;

import com.fluxtion.api.annotations.EventHandler;
import com.fluxtion.api.annotations.Initialise;
import com.fluxtion.api.audit.Auditor;
import com.fluxtion.api.event.Event;
import com.fluxtion.api.event.GenericEvent;

/**
 * A clock instance in a static event processor, use the @Inject annotation to
 * ensure the same of instance of the clock is used for all nodes.
 *
 * @author V12 Technology Ltd.
 */
public class Clock implements Auditor{

    private long eventTime;
    private ClockStrategy wallClock;

    @Override
    public void eventReceived(Event event) {
        eventTime = event.getEventTime();
    }

    @Override
    public void eventReceived(Object event) {
        if(Event.class.isAssignableFrom(event.getClass())){
            eventReceived((Event)event);
        }else{
            eventTime = getWallClockTime();
        }
    }

    @Override
    public void nodeRegistered(Object node, String nodeName) {/*NoOp*/}

    @EventHandler(propagate = false)
    public void setClockStrategy(GenericEvent<ClockStrategy> event) {
        this.wallClock = event.value;
    }

    public long getEventTime() {
        return eventTime;
    }

    public long getWallClockTime() {
        return wallClock.getWallClockTime();
    }

    @Initialise
    @Override
    public void init() {
        wallClock = System::currentTimeMillis;
    }

}
