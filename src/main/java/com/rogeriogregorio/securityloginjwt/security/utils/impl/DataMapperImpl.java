package com.rogeriogregorio.securityloginjwt.security.utils.impl;

import com.rogeriogregorio.securityloginjwt.security.utils.DataMapper;
import com.rogeriogregorio.securityloginjwt.security.utils.CatchError;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataMapperImpl implements DataMapper {

    private final ModelMapper modelMapper;
    private final CatchError catchError;

    @Autowired
    public DataMapperImpl(ModelMapper modelMapper, CatchError catchError) {
        this.modelMapper = modelMapper;
        this.catchError = catchError;
    }

    @Override
    public <S, T> T map(S source, Class<T> targetClass) {

        return catchError.run(() -> modelMapper.map(source, targetClass));
    }

    @Override
    public <S, T> T map(S source, T target) {

        return catchError.run(() -> {
            modelMapper.map(source, target);
            return target;
        });
    }

    @Override
    public <T> T fromJson(JSONObject jsonObject, Class<T> targetClass) {

        return catchError.run(() -> modelMapper.map(jsonObject.toMap(), targetClass));
    }

    @Override
    public <T> T fromMap(Map<String, Object> source, Class<T> targetClass) {

        return catchError.run(() -> modelMapper.map(source, targetClass));
    }
}
