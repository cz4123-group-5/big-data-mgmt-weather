import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Predicate;

/**
 * An abstract class representing a column store.
 */
public abstract class ColumnStoreAbstract {
    public static final int STRING_DATATYPE = 0;
    public static final int INTEGER_DATATYPE = 1;
    public static final int FLOAT_DATATYPE = 2;
    public static final int TIME_DATATYPE= 3;
    protected static final String DTFORMATSTRING = "yyyy-MM-dd HH:mm";
  
  
}
