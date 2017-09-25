package com.icooding.controller;

import com.icooding.doc.annotation.DOC;
import com.icooding.doc.annotation.Param;

public class UserController {



    @DOC(model = "用户",name = "用户列表",url = "/user",method = "GET" ,
            response = {
                    @Param(name = "pageIndex",type = "int",desc = "页码",def = "1"),
                    @Param(name = "pageSize",type = "int",desc = "页大小",def = "10"),
            }
    )
    public String list(){
        return "";
    }


    @DOC(model = "用户",name = "获取单个用户",url = "/user/{id}",method = "GET" ,
            response = {
                    @Param(name = "id",type = "string",desc = "ID"),
                    @Param(name = "name",type = "string",desc = "姓名")
            }
    )
    public String get(){
        return "";
    }

    @DOC(model = "用户",name = "修改用户",url = "/user/{id}",method = "PUT",
            params = {
                    @Param(name = "name",type = "string",requerd = false,def = "张三")
            },
            response = {
                    @Param(name = "code",type = "string",desc = "状态码"),
                    @Param(name = "msg",type = "string",desc = "消息"),
            }
    )
    public String update(){
        return "";
    }

    @DOC(model = "用户",name = "删除用户",url = "/user/{id}",method = "DELETE",
            response = {
                    @Param(name = "code",type = "string",desc = "状态码"),
                    @Param(name = "msg",type = "string",desc = "消息"),
            }
    )
    public String delete(){
        return "";
    }


}
