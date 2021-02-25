package br.com.zup.propostas.util;

import javax.persistence.AttributeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CryptograpyAttributeConverter implements AttributeConverter<String, String> {

    @Autowired
    private Cryptograpy cryptor;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return cryptor.encode(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return cryptor.decode(dbData);
    }
} 

