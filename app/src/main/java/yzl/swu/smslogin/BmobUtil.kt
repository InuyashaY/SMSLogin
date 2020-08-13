package yzl.swu.smslogin

import android.util.Log
import android.widget.Toast
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener

object BmobUtil  {
    const val SUCCESS = 0
    const val FAILURE = 1

    //发送验证码
    fun requestSMSCode(phone:String,callBack:(Int)->Unit){
        BmobSMS.requestSMSCode(phone, "", object : QueryListener<Int>() {
            override fun done(p0: Int?, p1: BmobException?) {
                if (p1 == null){
                    //验证码发送成功
                    callBack(SUCCESS)
                }else{
                    callBack(FAILURE)
                    Log.v("yzll","${p1.errorCode}:${p1.message}")
                }
            }
        })
    }


    //验证验证码
    fun verifySMSCode(phone: String,code: String, callBack: (Int)->Unit){
        BmobSMS.verifySmsCode(phone,code,object : UpdateListener(){
            override fun done(p0: BmobException?) {
                if (p0 == null){
                    callBack(SUCCESS)
                }else{
                    callBack(FAILURE)
                }
            }
        })
    }

}