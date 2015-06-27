package com.wayne.asynch;

/**
 * DESCRIPT: 解析
 * Author:Wayne
 * COMPANY:深圳沙悟净网络科技有限公司
 * EMAIL:chudian@gmail.com
 * CREATE:2015/6/27
 * VERSION:2.0
 */
public interface IParser<T> {
    T parser(String json);
}
