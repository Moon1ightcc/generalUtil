package org.example;

import java.util.ArrayList;
import java.util.List;

public class TableSchemaLoader {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.removeIf("2"::equals);

    }

}