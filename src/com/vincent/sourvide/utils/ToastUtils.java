/**
* @Title: ToastUtils.java
* @Package com.vincent.sourvide.utils
* @Description: TODO(用一句话描述该文件做什么)
* @author Vincent
* @date 2017年2月12日
* @version V1.0
*/
package com.vincent.sourvide.utils;

import com.vincent.sourvide.base.MyApplication;

import android.widget.Toast;

/**
* @ClassName: ToastUtils
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Vincent
* @date 2017年2月12日
*
*/
public class ToastUtils {
	
	public static void defaultToast(String msg){
		Toast.makeText(MyApplication.getContext(),msg,Toast.LENGTH_LONG).show();
	}
	
}
