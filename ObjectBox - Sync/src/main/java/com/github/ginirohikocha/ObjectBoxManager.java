package com.github.ginirohikocha;

import com.github.ginirohikocha.entity.MyObjectBox;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import lombok.Getter;

/**
 * @author GinirohikoCha
 * @since 2022/8/12
 */
public class ObjectBoxManager {

    private static ObjectBoxManager instance;

    public static ObjectBoxManager getInstance(String dbName) {
        if (instance == null) {
            instance = new ObjectBoxManager(dbName);
        }
        return instance;
    }

    @Getter
    private final BoxStore store;

    public ObjectBoxManager(String dbName) {
        store = MyObjectBox.builder().name(dbName).build();
    }

    public <T> Box<T> getBox(Class<T> entityClass) {
        return store.boxFor(entityClass);
    }

    public float getSize() {
        return (float) store.sizeOnDisk() / 1024f;
    }

    public void close() {
        store.close();
        instance = null;
    }
}
