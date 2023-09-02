package com.vv.usercenter.converter;

import org.springframework.cglib.core.Converter;

/**
 * 同名同类型
 * byte/int <--> bool
 *
 * @author jyw
 */
public class EntityBoConverter implements Converter {

    private static final String LIST_SEP = ",";

    private static final EntityBoConverter CONVERTER = new EntityBoConverter();

    public static EntityBoConverter getConverter() {
        return CONVERTER;
    }

    @Override
    public Object convert(Object source, Class targetClass, Object context) {

        // byte/int/null <--> bool
        if (targetClass.equals(Boolean.class)) {
            if (null == source) {
                return Boolean.FALSE;
            }
            if (source instanceof Byte) {
                return byte2Bool((Byte) source);
            }
            if (source instanceof Integer) {
                return int2Bool((Integer) source);
            }
        }

        if (null == source) {
            return null;
        }

        // same class
        if (source.getClass().equals(targetClass)) {
            return source;
        }


        if (source instanceof Boolean) {
            if (targetClass.equals(Byte.class)) {
                return bool2Byte((Boolean) source);
            }
            if (targetClass.equals(Integer.class)) {
                return bool2Int((Boolean) source);
            }
        }

        // unhandled
        return null;
    }

    protected static Boolean byte2Bool(Byte valid) {
        return null == valid ? null : valid > 0;
    }

    protected static Byte bool2Byte(Boolean valid) {
        return null == valid ? null : valid ? (byte) 1 : (byte) 0;
    }

    protected static Boolean int2Bool(Integer valid) {
        return null == valid ? null : valid > 0;
    }

    protected static Integer bool2Int(Boolean valid) {
        return null == valid ? null : valid ? 1 : 0;
    }
}
