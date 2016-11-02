package me.jcala.xmarket.server.utils;

import com.mongodb.DBObject;

public class MongoUtils {

	static public Object getPath(DBObject base, String... path) {
		DBObject current=base;
		for(int i=0;i < path.length-1;++i) {
			current=(DBObject) current.get(path[i]);
		}
		return current.get(path[path.length-1]);
	}

	public static <T> T getPath(Class<T> clazz, DBObject obj, String[] path) {
		Object o = getPath(obj,path);
		if(clazz.isInstance(o)) {
			return (T) o;
		}
		return null;
	}
}
