package com.mercedes.amg.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.DestroyMode;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class ObjectMapFactory extends BasePooledObjectFactory<ObjectMapper> {

    @Override
    public ObjectMapper create() throws Exception {
        return new ObjectMapper();
    }

    @Override
    public PooledObject<ObjectMapper> wrap(ObjectMapper objectMapper) {
        return new DefaultPooledObject<>(objectMapper);
    }

    @Override
    public void destroyObject(PooledObject<ObjectMapper> p, DestroyMode destroyMode) throws Exception {
        super.destroyObject(p, destroyMode);
    }
}
