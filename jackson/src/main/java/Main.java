import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

/**
 * Created by liyunpeng on 6/25/16.
 */
public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<String> arr = new ArrayList<>();
        arr.add("arr1");
        arr.add("arr2");
        System.out.println(mapper.writeValueAsString(new PojoEntity("str111", 111, 222, arr, new long[]{12121L, 211121L},
                new PojoEntity.InternalClazz(Main.class), new PojoEntity.InternalClazz[]{new PojoEntity.InternalClazz(Main.class)})));
    }
}
