package ua.od.whcrow.samples.spring6.boot_web._commons.collections;

import java.util.LinkedHashMap;
import java.util.Map;

public class LimitedSizeHashMap<K,V> extends LinkedHashMap<K,V> {
	
	private final int sizeLimit;
	
	public LimitedSizeHashMap(int sizeLimit) {
		super(sizeLimit * 10 / 7, 0.7f, true);
		this.sizeLimit = sizeLimit;
	}
	
	public LimitedSizeHashMap(int sizeLimit, Map<? extends K, ? extends V> map) {
		this(sizeLimit);
		putAll(map);
	}
	
	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size() > sizeLimit;
	}
	
}
