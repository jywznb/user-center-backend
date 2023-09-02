package com.vv.usercenter.model.bo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Slf4j
public abstract class BaseBO {

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("parse bo to string failed", e);
            return "{}";
        }
    }

    public String toPrettyString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("parse bo to string failed", e);
            return "{}";
        }
    }

    public static List<String> str2list(String s) {
        if (null == s) {
            return null;
        }

        if (s.isEmpty()) {
            return Lists.newArrayList();
        }

        return Lists.newArrayList(s.split(","));
    }

    public static String list2str(List<String> list) {
        if (null == list) {
            return null;
        }

        return String.join(",", list);
    }

    public static Boolean int2Bool(Integer valid) {
        if (null == valid) {
            return null;
        }
        return valid > 0;
    }

    public static Integer bool2Int(Boolean valid) {
        if (null == valid) {
            return null;
        }
        return valid ? 1 : 0;
    }

    public static Boolean byte2Bool(Byte valid) {
        if (null == valid) {
            return null;
        }
        return valid > 0;
    }

    public static Byte bool2Byte(Boolean valid) {
        if (null == valid) {
            return null;
        }
        return valid ? (byte) 1 : (byte) 0;
    }

    public static Date long2Date(Long timestamp) {
        if(null == timestamp) {
            return null;
        }
        return new Date(timestamp);
    }

    public static Long mills2Seconds(Long timestamp) {
        if(null == timestamp) {
            return null;
        }
        return timestamp / 1000 * 1000;
    }

    public static Long date2Long(Date date) {
        if(null == date) {
            return null;
        }

        return date.getTime();
    }

    public static <T> boolean listEqual(List<T> list1, List<T> list2) {
        if(!CollectionUtils.isEmpty(list1) && !CollectionUtils.isEmpty(list2)) {
            if(list1.size() != list2.size()){
                return false;
            }
            return list1.containsAll(list2);
        }
        return Objects.equal(list1, list2);
    }

}