package com.icooding.cms.utils;

import java.util.UUID;

public class TokenUtil {

	private static String[] src = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	private TokenUtil() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * 获取随机字符串
	 * @param length
	 * @param type 1.数字验证码 2.token
	 * @return
	 */
	public static String getRandomString(int length,int type){
		String code = "";
		for(int i=0;i<length;i++){
			code+=src[(int)(Math.random()*(type==1?10:src.length))];
		}
		return code;
	}


	public static String uuid(){
		return UUID.randomUUID().toString().replace("-","");
	}


	public static int hash(String key){
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

}
