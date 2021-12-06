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
 * Server Side License for more details.
 *
 * You should have received a copy of the Server Side Public License
 * along with this program.  If not, see
 * <http://www.mongodb.com/licensing/server-side-public-license>.
 */
package com.gh.test;

import com.fluxtion.api.StaticEventProcessor;
import com.fluxtion.api.lifecycle.BatchHandler;
import com.fluxtion.api.lifecycle.Lifecycle;

import com.fluxtion.compiler.InprocessSepCompilerTest.MyHandler;
import com.fluxtion.test.event.TimeEvent;

/*
 * <pre>
 * generation time   : 2021-12-06T11:00:56.474529500
 * generator version : ${generator_version_information}
 * api version       : 2.11.2-SNAPSHOT
 * </pre>
 * @author Greg Higgins
 */
@SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
public class GenNode_1638788456418 implements StaticEventProcessor, BatchHandler, Lifecycle {

  //Node declarations
  public final MyHandler handler = new MyHandler();
  //Dirty flags

  //Filter constants

  public GenNode_1638788456418() {}

  @Override
  public void onEvent(Object event) {
    switch (event.getClass().getName()) {
      case ("com.fluxtion.test.event.TimeEvent"):
        {
          TimeEvent typedEvent = (TimeEvent) event;
          handleEvent(typedEvent);
          break;
        }
    }
  }

  public void handleEvent(TimeEvent typedEvent) {
    //Default, no filter methods
    handler.onAllTimeEvents(typedEvent);
    //event stack unwind callbacks
    afterEvent();
  }

  private void afterEvent() {}

  @Override
  public void init() {}

  @Override
  public void tearDown() {}

  @Override
  public void batchPause() {}

  @Override
  public void batchEnd() {}
}
