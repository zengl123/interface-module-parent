package com.drore.tdp.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.xml.XMLSerializer;
import org.json.XML;

/**
 * @author ZENLIN
 */
@Slf4j
public class XmlUtil {
    /**
     * 将xml字符串转换成json对象
     *
     * @param xmlString
     * @return
     */
    public static JSONObject xml2json(String xmlString) {
        return JSON.parseObject(String.valueOf(XML.toJSONObject(xmlString)));
    }

    public static JSONObject xmlToJson(String xmlString) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        net.sf.json.JSON json = xmlSerializer.read(xmlString);
        return JSONObject.parseObject(json.toString());
    }
}