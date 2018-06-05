package com.ic.doc;


import com.ic.utils.JSON;

/**
 * Created by jagua on 2017/9/22.
 */
public class MarkdownToHtml {
    public static void main(String[] args) {
        String jsonStr = "{\"specialName\":\"just a test\",\"size\":4,\"list\":[1,2,3,4],\"myList\":{\"list\":[1,2,3,4],\"size\":4,\"specialName\":\"just a test\"}}";
        System.out.println(JSON.format(jsonStr));
    }
}
