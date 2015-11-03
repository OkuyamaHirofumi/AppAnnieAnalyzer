package htmlParser;

import java.awt.List;
import java.util.*;
import java.util.Map.Entry;

public class MapDescendingSort {
	public static Map<String, Integer> descendingSort(Map<String, Integer> map){
		java.util.List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> entry1,
					Entry<String, Integer> entry2) {
				return ((Integer)entry2.getValue().compareTo((Integer)entry1.getValue()));
			}
		});
		//List→Mapに変換する
		Map<String,Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for(Iterator<Map.Entry<String, Integer>> iterator = entries.iterator(); iterator.hasNext(); ){
			Map.Entry<String, Integer> entry = iterator.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}
