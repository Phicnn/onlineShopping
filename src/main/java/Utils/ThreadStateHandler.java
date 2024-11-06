package Utils;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ThreadStateHandler {
    private static final InheritableThreadLocal<HashMap> tMap = new InheritableThreadLocal<>();

    public static HashMap getMap() {
        if (tMap.get() == null){
            LinkedHashMap<String, Object> linkedHashMap = Maps.newLinkedHashMap();
            tMap.set(linkedHashMap);
        }
        return tMap.get();
    }
    public static <T> T getValue(String key) {
        return (T) getMap().getOrDefault(key,null);
    }
    public static <T> void setValue(String key, T value) {
        getMap().put(key,value);
    }
    private static void removeKey(String key) {
        getMap().remove(key);
    }
    public static void removeThreadMap() {
        tMap.remove();
    }
}
