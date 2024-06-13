package com.rogeriogregorio.securityloginjwt.security.services;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface DataMapper {

    <S, T> T map(S source, Class<T> targetClass);

    <S, T> T map(S source, T target);

    <T> T fromJson(JSONObject jsonObject, Class<T> targetClass);

    <T> T fromMap(Map<String, Object> source, Class<T> targetClass);
}
