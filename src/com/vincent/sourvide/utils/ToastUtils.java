/**
* @Title: ToastUtils.java
* @Package com.vincent.sourvide.utils
* @Description: TODO(��һ�仰�������ļ���ʲô)
* @author Vincent
* @date 2017��2��12��
* @version V1.0
*/
package com.vincent.sourvide.utils;

import com.vincent.sourvide.base.MyApplication;

import android.widget.Toast;

/**
* @ClassName: ToastUtils
* @Description: TODO(������һ�仰��������������)
* @author Vincent
* @date 2017��2��12��
*
*/
public class ToastUtils {
	
	public static void defaultToast(String msg){
		Toast.makeText(MyApplication.getContext(),msg,Toast.LENGTH_LONG).show();
	}
	
}
