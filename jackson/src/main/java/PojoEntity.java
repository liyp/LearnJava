import java.util.List;

/**
 * Created by liyunpeng on 6/25/16.
 */
public class PojoEntity {

    public String str;

    public int i;
    public Integer i1;

    public List<String> strArr;

    public long[] longs;

    public InternalClazz internalClazz;

    public InternalClazz[] internalClazzs;


    public PojoEntity(String str, int i, Integer i1, List<String> strArr, long[] longs, InternalClazz internalClazz,InternalClazz[] internalClazzs) {
        this.str = str;
        this.i = i;
        this.i1 = i1;
        this.strArr = strArr;
        this.longs = longs;
        this.internalClazz = internalClazz;
        this.internalClazzs = internalClazzs;
    }

    static class InternalClazz {
        public Class clazz;

        public InternalClazz(Class clazz) {
            this.clazz = clazz;
        }
    }
}
