package game.text;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class ContainerGeneric<T extends Thing> implements Container<T> {
	/** things that are in this container mapped by key */
	private Map<String,T> things = new HashMap<String,T>();  
	
	public void addOne(T thing) {
		this.addOne(thing.getName().toLowerCase(), thing);
	}

	public void addOne(String itemKey, T thing) {
		this.things.put(itemKey, thing);
	}

	public boolean hasOne(T thing) {
		if (thing.getName() != null) {
			return this.things.containsKey(thing.getName().toLowerCase());
		}
		return false;
	}

	public boolean hasOne(String key) {
		return this.things.containsKey(key);
	}

	public int countAll() {
		return this.things.size();
	}

	public T getOne(String key) {
		return this.things.get(key);	
	}
	
	public T matchOne(String keyStartsWith) {
		for (String key : this.things.keySet()) {
			if (key.startsWith(keyStartsWith)) {
				return this.things.get(key);
			}
		}
		return null;		
	}

	public Collection<T> getAll() {
		return this.things.values();
	}
	
	public void removeOne(String key) {
		this.things.remove(key);
	}

	public void removeOne(T thing) {
		removeOne(thing.getName().toLowerCase());
	}
	
	public void removeAll() {
		this.things.clear();
	}
}
