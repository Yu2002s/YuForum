package com.drny.forum.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    //初始化相关的配置
    static {
        //只引用不为空的值
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        //取消默认转换 timestamp
        objectMapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);

        //忽略空bean转换错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        //忽略在json中存在，在java对象不存在的错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 解决jackson2无法反序列化LocalDateTime的问题
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * 将java对象转换成json字符串
     *
     * @param obj
     * java 对象
     * @param <T>
     * @return
     */
    public static <T> String objectToString(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json字符串转换成java对象
     *
     * @param json
     * 字符串
     * @param tClass
     * 要转换的对象
     * @param <T>
     * @return
     */
    public static <T> T getObjetFormString(String json, Class<T> tClass) {
        if (StringUtils.isBlank(json) || tClass == null) {
            return null;
        }

        try {
            return tClass.equals(String.class) ? (T) json : objectMapper.readValue(json, tClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将字符串转换成java对象
     * @param json
     * 字符串
     * @param tTypeReference
     * 对象
     *
     * @param <T>
     * @return
     */
    public static <T> T fromString(String json, TypeReference<T> tTypeReference){
        if (StringUtils.isBlank(json) || tTypeReference == null) {
            return null;
        }

        try {
            return tTypeReference.getType().equals(String.class) ? (T) json : objectMapper.readValue(json, tTypeReference);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json字符串转换成java集合对象
     * @param json
     * 字符串
     * @param collectionClass
     * 集合类型
     * @param elementClazzes
     * 成员类型
     * @param <T>
     * @return
     */
    public static <T> T fromString(String json, Class<?> collectionClass, Class<?> ... elementClazzes){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClazzes);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}