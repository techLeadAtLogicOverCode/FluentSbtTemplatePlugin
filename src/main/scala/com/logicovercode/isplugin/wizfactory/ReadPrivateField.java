package com.logicovercode.isplugin.wizfactory;

import java.lang.reflect.Field;

public interface ReadPrivateField {

    default Object readSuperPrivateField(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return readPrivateFieldOfObject(obj, obj.getClass().getSuperclass(), fieldName);
    }

    default Object readPrivateField(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return readPrivateFieldOfObject(obj, obj.getClass(), fieldName);
    }

    default Object readPrivateFieldOfObject(Object obj, Class<?> klass, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field f = klass.getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(obj);
    }
}
