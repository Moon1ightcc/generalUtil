package org.example;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.Iterator;


/**
 * @author: limao
 * @date 2025-02-10
 * @description:
 */
public class Object2String {

    public static void main(String[] args) {

        Root root = new Root();
        Root.Detail detail = new Root.Detail();
        detail.setReason("test");
        root.setFee(detail);

        BigDecimal bigDecimal = BigDecimal.valueOf(Math.abs(Double.parseDouble(String.valueOf(9105.97112)))).setScale(2, BigDecimal.ROUND_HALF_UP);


        System.out.println(bigDecimal);

        JSONArray jsonArray = JSONUtil.parseArray("[\"600715\", \"300043\", \"300002\", \"600633\", \"300113\", \"002624\", \"002517\", \"300148\", \"002602\", \"002174\", \"002605\", \"300031\", \"300533\", \"300299\", \"603258\", \"002425\", \"300052\", \"300459\", \"002558\", \"002168\", \"603444\", \"300494\", \"002555\", \"300467\", \"600892\", \"300315\"]");

        System.out.println(JSONUtil.toJsonStr(root));

    }

}
