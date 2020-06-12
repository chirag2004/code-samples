package cache.impl;

import cache.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Chirag Patel
 * User: chiragpatel
 * Date: 5/18/15
 * Time: 6:24 AM
 */
public class ThreadSafeCache<K,V> implements Cache<K,V> {

    private Map<K,V> concurrentHashMap = new ConcurrentHashMap<>();
    private volatile static ThreadSafeCache cache;
    private Class keyClass;
    private Class valueClass;

    private ThreadSafeCache(Class<K> keyClass, Class<V> valueClass){
        this.keyClass = keyClass;
        this.valueClass = valueClass;
    }

    @Override
    public void put(K key, V value) {
        if(!keyClass.isAssignableFrom(key.getClass())){
            throw new  IllegalArgumentException("key is of unexpected class");
        }
        if(!valueClass.isAssignableFrom(value.getClass())) {
            throw new  IllegalArgumentException("value is of unexpected class");
        }
        concurrentHashMap.put(key,value);
    }

    @Override
    public V get(K key) {
        return concurrentHashMap.get(key);
    }

    public synchronized static <K,V> Cache<K,V> getInstance(Class<K> keyClass, Class<V> valueClass) {
        if(cache == null) {
            cache = new ThreadSafeCache<>(keyClass,valueClass);
        }
        if(!cache.valueClass.isAssignableFrom(valueClass)) {
            throw new IllegalArgumentException("an instance with a different value class already created");
        }
        if(!cache.keyClass.isAssignableFrom(keyClass)) {
            throw new IllegalArgumentException("an instance with a different key class already created");
        }
        return cache;
    }

    @Override
    public ThreadSafeCache<K,V> clone() {
        throw new UnsupportedOperationException("Cache cannot be cloned");
    }
}
