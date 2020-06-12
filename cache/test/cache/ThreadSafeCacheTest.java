package cache;

import cache.impl.ThreadSafeCache;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Chirag Patel
 * User: chiragpatel
 * Date: 5/18/15
 * Time: 7:00 AM
 */
public class ThreadSafeCacheTest {
    @Test
    public void testSingleInstance() throws InterruptedException {

        int numThreads = 1000;
        final List<Cache<String,Integer>> cacheList = new CopyOnWriteArrayList<>();
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(numThreads);


        Runnable cacheCreator = new Runnable() {
            @Override
            public void run() {
                try{
                    start.await();
                } catch (InterruptedException e) {

                }
                cacheList.add(ThreadSafeCache.getInstance(String.class, Integer.class));

                done.countDown();
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i< 1000; i++) {
            executor.execute(cacheCreator);
        }

        start.countDown();
        done.await();
        Cache cacheInstance = cacheList.get(0);

        for(int i=0; i< numThreads; i++) {
            assertTrue(cacheInstance == cacheList.get(i));
        }
    }


    @Test
    public void testThreadSafety() throws InterruptedException {
        int numThreads = 100;

        final Cache<String,Integer> cache =  ThreadSafeCache.getInstance(String.class, Integer.class);
        final List<Integer> values = new CopyOnWriteArrayList<>();

        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(100);


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    start.await();
                } catch (InterruptedException e) {
                }
                for(int i = 0; i < 1000; i++) {
                    if(cache.get("A") == null) {
                        cache.put("A",1);
                    }
                    Integer val = cache.get("A");
                    if(val < 3) {
                        cache.put("A", val+1);
                    } else {
                        cache.put("A", val-1);
                    }
                    values.add(cache.get("A"));
                }
                done.countDown();
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < numThreads; i++) {
            executor.execute(runnable);
        }
        start.countDown();
        done.await();

        for(int i=0; i< values.size(); i++) {
            assertTrue(values.get(i) <=3);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTypeSafetyForKey() {
        final Cache cache =  ThreadSafeCache.getInstance(Number.class, Number.class);
        cache.put("1", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTypeSafetyForValue() {
        final Cache cache =  ThreadSafeCache.getInstance(Number.class, Number.class);
        cache.put(5, "B");
    }

    @Test
    public void testAllowsAddingValues() {
        final Cache cache =  ThreadSafeCache.getInstance(Number.class, Number.class);
        cache.put(5, 4);
        assertEquals(4,cache.get(5));
    }

    @Test
    public void testAllowsAddingSubTypes() {
        final Cache cache =  ThreadSafeCache.getInstance(Integer.class, Integer.class);
        cache.put(5, 4);
        assertEquals(4,cache.get(5));
    }
}
