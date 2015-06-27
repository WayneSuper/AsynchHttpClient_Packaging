package com.wayne.asynch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * DESCRIPT: 抽象解析类
 * 目的：解析服务器端外层数据，并供具体解析类继承重写解析方法
 * Author:Wayne
 * EMAIL:chudian@gmail.com
 * CREATE:2015/6/27
 * VERSION:1.0
 */
public abstract class AbstractParser<T> implements IParser<Result<T>> {
    @Override
    public Result<T> parser(String response) {
        Result<T> result = new Result<>();
        if (response != null && response.length() > 0) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                result.setStatus(jsonObject.getInt("status"));
                result.setMessage(jsonObject.getString("message"));
                Object object = jsonObject.get("data");
                if(object != null &&(object instanceof JSONObject || object instanceof JSONArray)) {
                    T data = parserData(object);
                    result.setData(data);
                }else{
                    result.setData(null);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                result.setStatus(Result.JSON_PARSER_ERROR);
            }
        }else {
            result.setStatus(Result.RESULT_EMPTY);
        }
            return result;
    }

    protected abstract T parserData(Object data);
}
