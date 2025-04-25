package org.example;

import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;

import java.math.BigDecimal;


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

        Gson gson = new Gson();

        System.out.println(JSONUtil.toJsonStr(root));

    }

}
