package game.text;

import java.util.Collection;

public abstract class ActionableContainerGeneric<T extends Thing> extends ActionableGeneric 
		implements Container<T> {
	private Container<T> bucket = new ContainerGeneric<T>(); // implement Container via composition
	
	public ActionableContainerGeneric() {
		super();
	}	

	@Override
	abstract public void setDefaultActions();

	@Override
	public void addOne(T thing) {
		bucket.addOne(thing);		
	}

	@Override
	public void addOne(String key, T thing) {
		bucket.addOne(key, thing);
	}

	@Override
	public Collection<T> getAll() {
		return bucket.getAll();
	}

	@Override
	public T getOne(String key) {
		return bucket.getOne(key);
	}

	@Override
	public boolean hasOne(T thing) {
		return bucket.hasOne(thing);
	}

	@Override
	public boolean hasOne(String key) {
		return bucket.hasOne(key);
	}

	@Override
	public T matchOne(String keyStartsWith) {
		return bucket.matchOne(keyStartsWith);		
	}

	@Override
	public void removeOne(T thing) {
		bucket.removeOne(thing);
	}

	@Override
	public void removeOne(String key) {
		bucket.removeOne(key);
		
	}

	@Override
	public int countAll() {
		return bucket.countAll();
	}
	
	@Override
	public void removeAll() {
		bucket.removeAll();
	}

}
