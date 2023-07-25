package com.dz.ftsp.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.dz.ftsp.exception.FtspException;

public class JsonUtils {

    private static final SerializeConfig   config;

    static {
        config = new SerializeConfig();
        config.put(java.util.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss")); // 使用和json-lib兼容的日期输出格�?
    }

    public  static String toJson(Object obj){
        return JSON.toJSONString(obj,config);
    }


    public static <T> T toObject(String json,Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }

    public static Object toObject(String json){
        return JSON.parse(json);
    }

    /**
     * 将json string 转为 Map的同时 检验必要的参数是否存在,抛出对应异常
     *
     *
     * @return
     */
    public static Map<String,String> parseObject(String json,String []param){
        Map<String,String> m = new HashMap<String,String>();
        try{
            JSONObject jo = (JSONObject) JSON.parse(json);
            for(String p:param){
                String o = jo.getString(p);
                if(o == null){
                    throw new FtspException("json string :"+json+", Required String parameter :["+p+"]  is not present");
                }
                m.put(p, o);
            }
        }catch(com.alibaba.fastjson.JSONException e){
            e.printStackTrace();
            throw new FtspException("json string:"+json+" parse failure",e);
        }
        return m;
    }

    public static Map<String,Object> toMap(Object o){
        return JSONObject.toJavaObject(JSONObject.parseObject(JSON.toJSONString(o)), Map.class);
    }

    public static void main(String args[]) {

    }

}
