package com.rogeriogregorio.securityloginjwt.security.utils;

import org.springframework.stereotype.Component;

@Component
public interface DataMapper {

    <S, T> T map(S source, Class<T> targetClass);

    <S, T> T map(S source, T target);
}
