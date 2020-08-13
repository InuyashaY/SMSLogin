package yzl.swu.smslogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPhoneEditText.addTextChangedListener(object : TextWatcher{
            private var isAdd = true
            override fun afterTextChanged(s: Editable?) {
                mLoginBtn.isEnabled = s.toString().length == 13
                if (isAdd){
                    s.toString().length.also {
                        if (it == 3 || it == 8){
                            s?.append(" ")
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count>0){
                    isAdd = true
                }
                if (before > 0){
                    isAdd = false
                }
            }

        })

        //登录点击事件
        mLoginBtn.setOnClickListener{
            Intent().apply {
                //设置跳转对象
                setClass(this@MainActivity,VerifyActivity::class.java)
                //设置传递的数据
                putExtra("phoneNumber",getPhoneNumber(mPhoneEditText.text))
                startActivity(this)
            }
        }
    }

    open fun getPhoneNumber(editable: Editable):String{
        SpannableStringBuilder(editable.toString()).apply {
            delete(3,4)
            delete(7,8)
            return this.toString()
        }
    }
}