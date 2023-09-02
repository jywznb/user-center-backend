package com.vv.usercenter.util;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Bean copy utilities using BeanCopier of cglib
 * @author david
 * @date 2020/01/12
 */
public class BeanCopyUtils {

    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE = Maps.newHashMap();

    /**
     * 不使用自定义converter
     * 只会拷贝同类型且同名的属性(拆装箱关系的类型不属于同类型)
     * 被拷贝的属性必须在源类存在getter, 在目标类存在setter
     * @param source, 源类对象
     * @param target, 目标类对象
     */
    public static void copy(Object source, Object target) {
        copy(source, target, null);
    }

    /**
     * 使用自定义converter
     * 只会拷贝converter中处理过的属性, 定义converter时需考虑周全
     * @param source, 源类对象
     * @param target, 目标类对象
     * @param converter, 自定义converter
     */
    public static void copy(Object source, Object target, Converter converter) {
        BeanCopier copier = getCopier(source.getClass(), target.getClass(), null != converter);
        copier.copy(source, target, converter);
    }

    /**
     * 同类型对象复制, 会忽略null属性
     * @param source
     * @param target
     */
    public static void copySelective(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullProperties(source));
    }

    /**
     * 列表对象拷贝
     * @param source
     * @param target
     * @param converter
     */
    public static void copyList(List<Object> source, List<Object> target, Converter converter) {
        // todo source.parallelStream().forEachOrdered();
    }

    private static String generateCopierKey(Class<?> sourceClass, Class<?> targetClass, Boolean useConverter) {
        return String.format("%s -> %s(converter: %s)", sourceClass.getName(), targetClass.getName(), useConverter);
    }

    private static BeanCopier getCopier(Class<?> sourceClass, Class<?> targetClass, Boolean useConverter) {
        String key = generateCopierKey(sourceClass, targetClass, useConverter);
        return BEAN_COPIER_CACHE.computeIfAbsent(key,
                k -> BeanCopier.create(sourceClass, targetClass, useConverter));
    }

    private static String[] getNullProperties(Object object) {
        final BeanWrapper src = new BeanWrapperImpl(object);
        Set<String> nullProperties = Sets.newHashSet();
        for(PropertyDescriptor pd : src.getPropertyDescriptors()) {
            if (src.getPropertyValue(pd.getName()) == null) {
                nullProperties.add(pd.getName());
            }
        }
        String[] result = new String[nullProperties.size()];
        return nullProperties.toArray(result);
    }
}

