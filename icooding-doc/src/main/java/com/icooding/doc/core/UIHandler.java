package com.icooding.doc.core;

import com.icooding.doc.config.DocConfig;

import java.util.List;
import java.util.Map;

/**
 * 输出API UI
 * Created by jagua on 2017/9/25.
 */
public interface UIHandler {

    public String convert(DocConfig config,Map<String,List<API>> apis);


}
