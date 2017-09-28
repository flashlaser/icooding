package com.icooding.jfinal.restful.plugin;

import com.icooding.jfinal.restful.annotation.RequestMapping;
import com.icooding.jfinal.restful.annotation.RequestMethod;
import com.jfinal.plugin.IPlugin;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class RestfulPlugin implements IPlugin {


    private final  static  String fer = "/";

    private String[] packages;
    private ActionConvert actionConvert;

    public void setPackages(String[] packages) {
        this.packages = packages;
    }

    public void setActionConvert(ActionConvert actionConvert) {
        this.actionConvert = actionConvert;
    }

    public RestfulPlugin() {
    }

    public RestfulPlugin(String[] packages) {
        this.packages = packages;
    }

    public RestfulPlugin(String[] packages, ActionConvert actionConvert) {
        this.packages = packages;
        this.actionConvert = actionConvert;
    }

    @Override
    public boolean start() {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (String aPackage : packages) {
            classes.addAll(ClassUtil.getClasses(aPackage));
        }

        for (Class<?> aClass : classes) {
            RequestMapping classRequestMapping = aClass.getAnnotation(RequestMapping.class);
            if(null != classRequestMapping){
                String controllerKey = classRequestMapping.jfinalValue();
                String controllerMapping = classRequestMapping.value();
                Method[] methods = aClass.getDeclaredMethods();
                for (Method method : methods) {
                    RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
                    if(Modifier.isPublic(method.getModifiers()) && null != methodRequestMapping){
                        String actionKey = methodRequestMapping.jfinalValue();
                        String methodMapping = methodRequestMapping.value();
                        RequestMethod[] httpmethod = methodRequestMapping.method();
                        if(StringUtils.isEmpty(controllerKey)){
                            if(actionConvert != null){
                                controllerKey =  actionConvert.controller(aClass.getSimpleName());
                            }else{
                                controllerKey =  aClass.getSimpleName();
                            }
                        }
                        if(StringUtils.isEmpty(actionKey)){
                            if(actionConvert != null){
                                actionKey =  actionConvert.method(method.getName());
                            }else{
                                actionKey =  method.getName();
                            }
                        }
                        controllerKey = controllerKey.startsWith(fer)?controllerKey:fer+controllerKey;
                        actionKey = actionKey.startsWith(fer)?actionKey:fer+actionKey;
                        String jfinalActionKey =  controllerKey + actionKey;
                        String controller = StringUtils.isNotEmpty(controllerMapping)? controllerMapping+methodMapping:methodMapping;
                        for (RequestMethod httpMethod : httpmethod) {
                            MappingManager.addUrlMapping(new Mapping(jfinalActionKey,controller,httpMethod));
                        }
                    }
                }
            }
        }
        MappingManager.print();
        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }
}
