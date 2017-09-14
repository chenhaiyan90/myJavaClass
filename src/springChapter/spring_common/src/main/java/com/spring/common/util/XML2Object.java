package com.spring.common.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/**
 * @Description： xml转换成Object
 * Created by Ambitor on 2016/6/7.
 */
public class XML2Object {

    /**
     * 把xml文件反序列化成Object
     * @param xmlStr
     * @param tClass
     * @param <T>
     * @return
     * @throws JAXBException
     */
    public static <T> T xml2Object(String xmlStr, Class<T> tClass) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(tClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object aliPay = unmarshaller.unmarshal(new StringReader(xmlStr));
        return (T) aliPay;
    }
}
