 
package com.spring.common.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
 
/**
 * 
 * @Description:
 * @author hezhen
 * @date 2016年8月10日 下午8:33:32
 */
public class TimeJsonSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date value, JsonGenerator jgen,

    SerializerProvider provider) throws IOException,

    JsonProcessingException {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        String formattedDate = formatter.format(value);

        jgen.writeString(formattedDate);

    }

}
