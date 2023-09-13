package com.example.lessonscrudproject.dto;

import java.util.List;

public interface SimpleCrud<K,V>{
    ResponseDto<V>create(V dto);
    ResponseDto<V>get(K id);
    ResponseDto<V>update(V dto,K id);
    ResponseDto<V>delete(K id);


}
