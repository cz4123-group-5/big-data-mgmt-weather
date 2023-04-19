import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.AbstractList;
import java.util.List;

public class ArrayListOnDisk<E> extends AbstractList<E> implements List<E> {
    private RandomAccessFile raf;
    private File file;
    private int bufferSize;
    private long size;
    private Class<E> elementType;

    public ArrayListOnDisk(String filename, Class<E> elementType) {
        this.file = new File(filename);;
        this.elementType = elementType;
        this.bufferSize = getBufferSizeForType(elementType);
        try {
            raf = new RandomAccessFile(file, "rw");
            size = raf.length() / bufferSize;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public E get(int index) {
        try {
            raf.seek(index * bufferSize);
            byte[] buffer = new byte[bufferSize];
            raf.read(buffer);
            return deserialize(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        return (int) size;
    }

    @Override
    public boolean add(E e) {
        try {
            byte[] buffer = serialize(e);
            raf.seek(size * bufferSize);
            raf.write(buffer);
            size++;
            return true;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private byte[] serialize(E object) {
        // check if null
        if (object == null) {
            return new byte[bufferSize];
        }
        if (elementType == Double.class) {
            Double value = (Double) object;
            ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
            buffer.putDouble(value);
            return buffer.array();
        } else if (elementType == Integer.class) {
            Integer value = (Integer) object;
            ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
            buffer.putInt(value);
            return buffer.array();
        } else if (elementType == LocalDateTime.class) {
            LocalDateTime value = (LocalDateTime) object;
            long epochSeconds = value.toEpochSecond(ZoneOffset.UTC);
            int nanoOfSecond = value.getNano();
            ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES + Integer.BYTES);
            buffer.putLong(epochSeconds);
            buffer.putInt(nanoOfSecond);
            return buffer.array();
        } else if (elementType == Station.class) {
            Station value = (Station) object;
            byte[] nameBytes = value.getName().getBytes(StandardCharsets.UTF_8);
            ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES + nameBytes.length);
            buffer.putInt(nameBytes.length);
            buffer.put(nameBytes);
            return buffer.array();
        }
        throw new UnsupportedOperationException("Serialization not supported for type: " + elementType);
    }

    private E deserialize(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        // check if null
        if (buffer.remaining() == 0) {
            return null;
        }

        if (elementType == Double.class) {
            Double value = buffer.getDouble();
            return (E) value;
        } else if (elementType == Integer.class) {
            Integer value = buffer.getInt();
            return (E) value;
        } else if (elementType == LocalDateTime.class) {
            long epochSeconds = buffer.getLong();
            int nanoOfSecond = buffer.getInt();
            LocalDateTime value = LocalDateTime.ofEpochSecond(epochSeconds, nanoOfSecond, ZoneOffset.UTC);
            return (E) value;
        } else if (elementType == Station.class) {
            int nameLength = buffer.getInt();
            byte[] nameBytes = new byte[nameLength];
            buffer.get(nameBytes);
            String name = new String(nameBytes, StandardCharsets.UTF_8);
            for (Station station : Station.values()) {
                if (station.getName().equals(name)) {
                    return (E) station;
                }
            }
            throw new IllegalArgumentException("Invalid station name: " + name);
        }
        throw new UnsupportedOperationException("Deserialization not supported for type: " + elementType);
    }

    private int getBufferSizeForType(Class<E> type) {
        if (type == Double.class) {
            return Double.BYTES;
        }
        if (type == Integer.class) {
            return Integer.BYTES;
        }
        if (type == LocalDateTime.class) {
            return Long.BYTES + Integer.BYTES;
        }
        if (type == Station.class) {
            int maxNameLength = 0;
            for (Station station : Station.values()) {
                maxNameLength = Math.max(maxNameLength, station.getName().length());
            }
            return Integer.BYTES + maxNameLength;
        }
        // Handle other types if necessary
        throw new UnsupportedOperationException("Buffer size calculation not supported for type: " + type);
    }

    @Override
    protected void finalize() throws Throwable {
        raf.close();
        super.finalize();
    }

    public void writeCacheToDisk() {
        // not implemented yet
    }
}
