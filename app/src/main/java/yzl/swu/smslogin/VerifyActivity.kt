package yzl.swu.smslogin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import kotlinx.android.synthetic.main.activity_verify.*
import java.util.*


class VerifyActivity : AppCompatActivity() {
    private val verifyViews:Array<TextView> by lazy {
        arrayOf(mv1,mv2,mv3,mv4,mv5,mv6)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)
        //获取数据
        intent.getStringExtra("phoneNumber").also {
            //显示号码
            mphoneNumber.text = it
        }

        //输入验证码监听事件
        mOrg.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //显示到TextView中
                for ((i,item) in s?.withIndex()!!){
                    verifyViews[i].text = item.toString()
                }

                //删除
                for (i in s.length..5){
                    verifyViews[i].text = ""
                }

                //判断输入的验证码是否到6位
                if (s.length == 6){
                    BmobUtil.verifySMSCode(mphoneNumber.text.toString(),mOrg.text.toString()){
                        if (it == BmobUtil.SUCCESS){
                            //验证成功，跳转到主页
                            startActivity(Intent(this@VerifyActivity,HomeActivity::class.java))

                        }else{
                            Toast.makeText(this@VerifyActivity,"验证失败",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })

        //监听重新发送按钮点击事件
        resendBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                resendVerify()
            }

        })

    }

    override fun onResume() {
        super.onResume()
        //发送验证码
        sendToVerify()
    }

    //重新发送验证码
    fun resendVerify(){
        resendBtn.isEnabled = false
        resendBtn.alpha = 0.3F
        sendToVerify()
    }

    //发送验证码
    fun sendToVerify(){
        BmobUtil.requestSMSCode(mphoneNumber.text.toString()){
            if (it == BmobUtil.SUCCESS){
                Toast.makeText(this,"发送验证码成功",Toast.LENGTH_SHORT).show()
                //开启倒计时
                startTimeDown()
            }else{
                Toast.makeText(this,"发送验证码失败",Toast.LENGTH_SHORT).show()
            }
        }
    }

    //开启倒计时
    fun startTimeDown(){
        Handler().apply {
            postDelayed(object:Runnable{
                override fun run() {
                    mTime.text = (mTime.text.toString().toInt()-1).toString()
                    if (mTime.text.toString().toInt() > 0){
                        postDelayed(this,1000)
                    }else{
                        removeCallbacks(this)
                        resendBtn.isEnabled = true
                        resendBtn.alpha = 1.0F
                    }
                }

            },1000)
        }
    }
}