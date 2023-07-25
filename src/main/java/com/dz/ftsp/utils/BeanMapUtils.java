package com.dz.ftsp.utils;


import com.dz.ftsp.exception.FtspException;
import com.dz.ftsp.util.JsoupUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * Bean、Map互转工具类
 */
public class BeanMapUtils {

    private static Logger logger = LoggerFactory.getLogger(BeanMapUtils.class);

    /**
     * 将对象转换成map
     *
     * @param bean
     * @return
     */
    public static Map<String, ?> toMap(Object bean) {
        Map<String, Object> map = new HashMap<String, Object>();
        ReflectionUtils.doWithFields(bean.getClass(), field -> {
            field.setAccessible(true);
            map.put(field.getName(), ReflectionUtils.getField(field, bean));
        });
        return map;
    }

    public static <T> T toBean(Class<T> clazz, Map<String, ?> map) {
        return toBean(clazz, map, true);
    }

    /**
     * 将map转换成对象
     *
     * @param clazz
     * @param map
     * @param throwException
     * @param <T>
     * @return
     */
    public static <T> T toBean(Class<T> clazz, Map<String, ?> map, boolean throwException) {
        try {
            T t = clazz.newInstance();
            for (String key : map.keySet()) {
                try {
                    Field field = clazz.getDeclaredField(key);
                    setFiels(field, t, map.get(key), DateUtils.DATE_TIME_PATTERN);
                } catch (Exception e) {
                    if (throwException) {
                        throw new FtspException("map转换成Bean异常", e);
                    } else {
                        logger.info("[金服Bean转换工具类][转换Bean异常，跳过该key][key:" + key + "]", e);
                    }
                }
            }
            return t;
        } catch (Exception e) {
            FtspException ftspException = null;
            if (e instanceof FtspException) {
                ftspException = (FtspException) e;
            } else {
                ftspException = new FtspException("map转换成Bean异常", e);
            }
            throw ftspException;
        }
    }

    public static void setFiels(Field targetField, Object target, Object value, Object param) {
        //clear xss
        if (value instanceof String && ValidateUtils.isNotEmptyString((String) value)) {
            value = JsoupUtils.clean((String) value);
        }

        Object parseValue = null;
        if (ValidateUtils.isNull(value)) {
            parseValue = null;
        } else if (String.class.equals(value.getClass())
                && ValidateUtils.isEmptyString((String) value)
                && !String.class.equals(targetField.getType())) {
            parseValue = null;
        } else {
            Class clazz = targetField.getType();
            if (String.class.equals(clazz)) {
                parseValue = String.valueOf(value);
            } else if (Long.class.equals(clazz)) {
                parseValue = Long.valueOf(String.valueOf(value));
            } else if (Integer.class.equals(clazz)) {
                parseValue = Integer.valueOf(String.valueOf(value));
            } else if (Short.class.equals(clazz)) {
                parseValue = Short.valueOf(String.valueOf(value));
            } else if (BigDecimal.class.equals(clazz)) {
                parseValue = new BigDecimal(String.valueOf(value));
            } else if (Date.class.equals(clazz)) {
                parseValue = DateUtils.parse(String.valueOf(value), String.valueOf(param));
            } else {
                throw new FtspException("暂时不支持设置该种字段类型，" + clazz.getName());
            }
        }
        targetField.setAccessible(true);
        ReflectionUtils.setField(targetField, target, parseValue);
    }

    /**
     * 拷贝数据，并跳过指定的字段
     *
     * @param source
     * @param target
     * @param ignoreSpecField
     * @param ignores
     */
    public static void copyProperties(Object source, Object target, boolean ignoreSpecField, List<String> ignores) {
        List<String> tmp = new ArrayList<>();
        if (ignores != null) {
            tmp.addAll(ignores);
        }
        if (ignoreSpecField) {
            tmp.add("id");
            tmp.add("createTime");
            tmp.add("updateTime");
        }
        BeanUtils.copyProperties(source, target, tmp.toArray(new String[0]));
    }

    /**
     * 拷贝数据，并跳过指定的字段
     *
     * @param source
     * @param target
     * @param ignores
     */
    public static void copyProperties(Object source, Object target, List<String> ignores) {
        copyProperties(source, target, true, ignores);
    }
}
