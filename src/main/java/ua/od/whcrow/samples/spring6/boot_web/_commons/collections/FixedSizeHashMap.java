package ua.od.whcrow.samples.spring6.boot_web._commons.collections;

import java.util.LinkedHashMap;
import java.util.Map;

public class FixedSizeHashMap<K,V> extends LinkedHashMap<K,V> {
	
	private final int sizeLimit;
	
	public FixedSizeHashMap(int sizeLimit) {
		super(sizeLimit * 10 / 7, 0.7f, true);
		this.sizeLimit = sizeLimit;
	}
	
	public FixedSizeHashMap(int sizeLimit, Map<? extends K, ? extends V> map) {
		this(sizeLimit);
		putAll(map);
	}
	
	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size() > sizeLimit;
	}
	
}
