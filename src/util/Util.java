package util;

import java.util.HashMap;

public class Util {
	
	public static <K,V> HashMap<K,V> zip(K[] keys, V[] vals){
		HashMap<K,V> res = new HashMap<K,V>();
		for(int i=0;i<keys.length && i<vals.length;i++){
			res.put(keys[i], vals[i]);
		}
		return res;
	}
	
	public static String[] allToString(Object[] objs){
		String[] res = new String[objs.length];
		for(int i=0;i<res.length;i++){
			res[i]=objs.toString();
		}
		return res;
	}
	
	public static boolean contains(Object[] arr, Object obj){
		return indexOf(arr,obj)>=0;
	}
	
	public static int indexOf(Object[] arr, Object obj){
		int res = 0;
		for(Object o : arr){
			if(o.equals(obj)){
				return res;
			}
			res++;
		}
		return -1;
	}
	
	public static String join(String with, Object[] arr){
		String res="";
		for(int i=0;i<arr.length;i++){
			if(i>0){
				res+=with;
			}
			res+=arr[i].toString();
		}
		return res;
	}
	
	public static String join(char with, Object[] arr){
		return join(with+"",arr);
	}
	
	public static String sprintf(String src, Object...objects){
		String res = "";
		boolean escape = false;
		final char escapechar = '%';
		//TODO finish
		return res;
	}

	private Util() {}

}
