# icooding-jfinal-restful 是对jfinal封装的restful风格请求的解决方案

##### 1.0
1.配置

``` java

	@Override
    public void configPlugin(Plugins me) { 
        RestfulPlugin restfulPlugin = new RestfulPlugin();
        restfulPlugin.setPackages(new String[]{"com.axinfu.axindf.ws.action.pre"});//扫描的包
        restfulPlugin.setActionConvert(new DaifuActionConvert());//对系统原有url的配置
        me.add(restfulPlugin);
    }
	
	@Override
    public void configInterceptor(Interceptors me) { 
        me.add(new ResponseBodyInterceptor());// 将Controller或Controller父类 有ResponseBody注解的类统一返回JSON
    }
	
	@Override
	public void configHandler(Handlers me) { 
        me.add(new HttpMethodHandler());//url重写
    }
	
	public class DaifuActionConvert implements ActionConvert {
		@Override
		public String controller(String controllerKey) {
			return "/action/"+controllerKey.replace("Controller","").toLowerCase();
		}

		@Override
		public String method(String actionKey) {
			return actionKey;
		}
	}
	
```