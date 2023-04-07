package src;

import java.util.*;
import java.util.function.Predicate;

/**
 * A column store implementation where the data is stored in main memory.
 */
public class ColumnStoreMM extends ColumnStoreParent {
    private final HashMap<String, List<Object>> data = new HashMap<>();

    public ColumnStoreMM(HashMap<String, Integer> columnDataTypes) {
//        super(columnDataTypes);
//        for (String columnHeader: columnHeaders) {
//            data.put(columnHeader, new ArrayList<>()); // initialize the data array with empty lists.
//        }
    }
}
