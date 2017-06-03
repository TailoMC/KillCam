package me.Tailo.KillCam.Utils;

import java.lang.reflect.Field;

public class Reflections {

	public static void setValue(Object clazz, String field, Object value) {
		try {
			Field f = clazz.getClass().getDeclaredField(field);
			f.setAccessible(true);
			f.set(clazz, value);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static Object getValue(Object clazz, String field) {
		try {
			Field f = clazz.getClass().getDeclaredField(field);
			f.setAccessible(true);
			return f.get(clazz);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
