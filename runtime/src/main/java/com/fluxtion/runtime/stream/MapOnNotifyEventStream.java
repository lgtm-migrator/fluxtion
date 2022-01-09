package com.fluxtion.runtime.stream;

import com.fluxtion.runtime.annotations.NoEventReference;
import com.fluxtion.runtime.annotations.OnEvent;
import com.fluxtion.runtime.annotations.PushReference;
import com.fluxtion.runtime.annotations.builder.Inject;
import com.fluxtion.runtime.audit.NodeNameLookup;
import lombok.ToString;

@ToString
public class MapOnNotifyEventStream <R, T, S extends EventStream<R>> extends AbstractEventStream<R, T, S> {

    @PushReference
    private final T target;
    private final transient String auditInfo;
    private String instanceNameToNotify;
    @Inject
    @NoEventReference
    public NodeNameLookup nodeNameLookup;

    public MapOnNotifyEventStream(S inputEventStream, T target) {
        super(inputEventStream, null);
        this.target = target;
        auditInfo = target.getClass().getSimpleName();
    }

    protected void initialise() {
        instanceNameToNotify = nodeNameLookup.lookup(target);
    }

    @OnEvent
    public boolean notifyChild() {
        auditLog.info("notifyClass", auditInfo);
        auditLog.info("notifyInstance", instanceNameToNotify);
        return fireEventUpdateNotification();
    }

    @Override
    public T get() {
        return target;
    }
}