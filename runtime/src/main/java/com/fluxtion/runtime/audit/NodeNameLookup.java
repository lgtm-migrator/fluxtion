package com.fluxtion.runtime.audit;

import java.util.HashMap;
import java.util.Map;

public class NodeNameLookup implements Auditor {

    private final Map<Object, String> node2NameMap = new HashMap<>();
    private final Map<String, Object> name2NodeMap = new HashMap<>();

    @Override
    public void nodeRegistered(Object node, String nodeName) {
        node2NameMap.put(node, nodeName);
        name2NodeMap.put(nodeName, node);
    }

    public String lookupInstanceName(Object node) {
        return node2NameMap.getOrDefault(node, "???");
    }

    public <T> T getInstanceById(String id) throws NoSuchFieldException {
        T node = (T) name2NodeMap.get(id);
        if (node == null) {
            throw new NoSuchFieldException(id);
        }
        return node;
    }

    @Override
    public void init() {
        node2NameMap.clear();
    }
}
