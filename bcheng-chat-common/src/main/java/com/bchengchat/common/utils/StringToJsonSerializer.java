package com.bchengchat.common.utils;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class StringToJsonSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (!StringUtils.hasText(value)){
            gen.writeNull();
        }
        //如果是json，则返回解析后的json
        try {
            if (JSONUtil.isTypeJSON(value)){
                gen.writeObject(JSONUtil.parse(value));
            }else {
                gen.writeObject(value);
            }
        }catch (Exception e){
            gen.writeObject(value);
        }
    }
}
