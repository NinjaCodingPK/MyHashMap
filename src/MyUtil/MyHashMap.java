package MyUtil;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by wookie on 6/30/16.
 */
public class MyHashMap<K, V> implements Map<K, V> {
    public static final int DEFAULT_START_CAPACITY = 10;
    public static final  int ADDED_MEMORY = 16;
    public class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;
        private final int hash;
        private Entry<K, V> next;

        public Entry(K key, V value, int hash, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        public Entry<K, V> getNext() {
            return next;
        }

        public void setNext(Entry<K, V> next) {
            this.next = next;
        }

        @Override
        public V setValue(V value) {
            V temp = this.value;
            this.value = value;
            return temp;
        }

        public int getHash() {
            return hash;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entry<?, ?> entry = (Entry<?, ?>) o;

            if (getHash() != entry.getHash()) return false;
            return getKey().equals(entry.getKey());

        }

        @Override
        public int hashCode() {
            int result = getKey().hashCode();
            result = 31 * result + getHash();
            return result;
        }
    }

    private Entry entries[];
    private int size;

    public MyHashMap() {
        entries = new Entry[DEFAULT_START_CAPACITY];
    }

    private void addCapacity() {
        Entry[] temp = new Entry[size + ADDED_MEMORY];
        System.arraycopy(entries, 0, temp, 0, entries.length);
        entries = temp;
    }

    private void checkCapacity() {
        if(entries.length >= size) {
            addCapacity();
        }
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return null;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {

    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {

    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return false;
    }

    @Override
    public V replace(K key, V value) {
        return null;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return null;
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        int position = key.hashCode() % entries.length;
        return (V) entries[position].getValue();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return null;
    }

    private void putnext(Entry next, K key, V value) {
        if(next.getNext() != null) {
            putnext(next.getNext(), key, value);
        }
        else
            next.setNext(new Entry(key, value, key.hashCode(), null));
    }

    @Override
    public V put(K key, V value) {
        int position =  key.hashCode() % entries.length;

        if(entries[position] == null) {
            entries[position] = new Entry(key, value, key.hashCode(), null);
        }
        else
            putnext(entries[position], key, value);
        size++;
        return value;
    }

    private void removeNext(Entry next) {
        if(next != null) {
            if(next.getNext() != null)
                removeNext(next);

            next = null;
        }
    }

    @Override
    public V remove(Object key) {
        int position = key.hashCode() % entries.length;
        Entry temp = entries[position];
        removeNext(entries[position]);
        entries[position] = null;
        size--;
        return (V) temp.getValue();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        for(Entry en : entries)
            removeNext(en);
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();

        for(Entry en : entries) {
            if(en != null)
                keySet.add((K) en.getKey());
        }

        return keySet;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();

        for(Entry en : entries) {
            if(en != null)
                values.add((V) en.getValue());
        }

        return values;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }
}
