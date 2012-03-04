package game.text;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DataBucketGeneric implements DataBucket {
	public Map<String, Object> data = new HashMap<String, Object>();
	
	public Object getData(String key) {
		return data.get(key);
	}

	public Collection<String> listDataKeys() {
		return data.keySet();
	}

	public void setData(String key, Object value) {
		data.put(key, value);
	}

	public void removeData(String key) {
		data.remove(key);
	}

	public void removeAllData() {
		data.clear();
	}	
}
