package cache;

/**
 * Created by Chirag Patel
 * User: chiragpatel
 * Date: 5/18/15
 * Time: 6:23 AM
 */
public interface Cache<K,V> {
    public void put(K key, V value);
    public V get(K key);
}
