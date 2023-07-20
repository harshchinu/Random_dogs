package com.example.doggo.data.cache


class LRUCache<K, V>(private val MAX_CACHE_SIZE: Int) {
    private val map: LinkedHashMap<K, V>

    init {
        map = object : LinkedHashMap<K, V>(MAX_CACHE_SIZE, 1.1f, true) {
            override fun removeEldestEntry(eldest: Map.Entry<K, V>): Boolean {
                return size > MAX_CACHE_SIZE
            }
        }
    }

    @Synchronized
    fun put(key: K, value: V) {
        map[key] = value
    }

    @Synchronized
    operator fun get(key: K): V? {
        return map[key]
    }

    @Synchronized
    fun getAllKeys():List<K> = map.keys.toList()

    @Synchronized
    fun clear() {
        map.clear()
    }

    fun getAllValues():List<V> = map.values.toList()
}