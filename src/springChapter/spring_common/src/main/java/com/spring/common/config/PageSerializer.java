package com.spring.common.config;

import com.spring.common.web.vo.Page;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @desc: Page序列化配置
 * @User: ambitor_luo
 * @Date: 2015/7/14
 */
public class PageSerializer extends JsonSerializer<Page> {
    @Override
    public void serialize(Page page, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("pageNum",page.getPageNum());
        jsonGenerator.writeNumberField("pageSize",page.getPageSize());
        jsonGenerator.writeNumberField("totalCount",page.getTotalCount());
        jsonGenerator.writeNumberField("totalPage",page.getTotalPage());
        jsonGenerator.writeBooleanField("isFirstPage",page.isFirstPage());
        jsonGenerator.writeBooleanField("isLastPage",page.isLastPage());
        jsonGenerator.writeObjectField("results",page.getResults());
        jsonGenerator.writeEndObject();
    }
}
