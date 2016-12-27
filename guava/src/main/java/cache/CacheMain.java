package cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * Created by liyunpeng on 10/10/16.
 */
public class CacheMain {

    private static Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(1, TimeUnit.DAYS)
                                                      .build();

    public static void main(String[] args) {
        String vv = "";
        try {
            vv = cache.get("a", new Callable<String>() {
                public String call() throws Exception {
                    return null;
                }
            });
        } catch (ExecutionException e) {
            vv = "";
        }
        System.out.println(vv);
    }
}
