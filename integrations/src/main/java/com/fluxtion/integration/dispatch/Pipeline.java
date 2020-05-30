/*
 * Copyright (c) 2020, V12 Technology Ltd.
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
package com.fluxtion.integration.dispatch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;

/**
 * A pipeline is made up of multiple {@link EventFilter} stages. The pipeline
 * supports multiplexing with the {@link PipelineStatge#merge(com.fluxtion.integration.dispatch.Pipeline.PipelineStatge)
 * } operation.
 *
 * @author Greg Higgins greg.higgins@v12technology.com
 */
@Log4j2
public class Pipeline {

    private final PipelineStatge root = new PipelineStatge(new RootNode());
    private final List<EventFilter> sortedFilters = new ArrayList<>();

    public void start() {
        log.info("starting pipeline");
        sortedFilters.clear();
        sortTopologically(root);
        log.debug("filter chain:{}", sortedFilters);
        sortedFilters.forEach(EventFilter::initHandler);
        log.info("started pipeline");
    }

    public void stop() {
        log.info("stopping pipeline");
        for (int i = sortedFilters.size(); i-- > 0;) {
            sortedFilters.get(i).stopHandler();
        }
        log.info("stopped pipeline");
    }

    private void sortTopologically(PipelineStatge stage) {
        Stream<PipelineStatge> stream = stage.children.stream();
        stream.filter(s -> !s.isVisited()).forEach((PipelineStatge s) -> {
            sortTopologically(s);
            s.visited = true;
        });
        if (!(stage.filterElement instanceof RootNode)) {
            sortedFilters.add(stage.filterElement);
        }
    }

    public <S extends EventFilter> PipelineStatge<S> entry(S entryNode) {
        PipelineStatge<S> stage = new PipelineStatge(entryNode);
        root.children.add(stage);
        return stage;
    }

    public class PipelineStatge<T extends EventFilter> {

        List<PipelineStatge> children = new ArrayList<>();

        private final T filterElement;
        private boolean visited = false;

        public boolean isVisited() {
            return visited;
        }

        public PipelineStatge(T filterElement) {
            this.filterElement = filterElement;
        }

        public <S extends EventFilter> PipelineStatge<S> next(S filter) {
            PipelineStatge<S> nextStage = new PipelineStatge<>(filter);
            children.add(nextStage);
            filterElement.next(filter);
            return nextStage;
        }

        public <S extends EventFilter> PipelineStatge<S> merge(PipelineStatge toMerge) {
            SynchronizedFilter joiner = new SynchronizedFilter();
            PipelineStatge nextStage = next(joiner);
            toMerge.filterElement.next(joiner);
            toMerge.children.add(nextStage);
            return nextStage;
        }

    }

    private static class RootNode extends EventFilter {

        @Override
        public void processEvent(Object o) {
        }

        @Override
        protected void stopHandler() {
            log.info("stopping pipeline");
        }

        @Override
        protected void initHandler() {
            log.info("starting pipeline");
        }

    }
}
