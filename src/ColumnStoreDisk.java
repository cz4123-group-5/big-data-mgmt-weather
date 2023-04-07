import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

/**
 * A general column store implementation where the data is stored in disk.
 */
public class ColumnStoreDisk extends ColumnStoreAbstract{
    /**
     * Buffer size when reading files.
     */
    protected static final int BUFFER_SIZE = 10240;

    public ColumnStoreDisk(HashMap<String, Integer> columnDataTypes) {
        super(columnDataTypes);
    }
