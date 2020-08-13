package yzl.swu.smslogin

import android.app.Application
import cn.bmob.v3.Bmob

class SMSApplacation: Application() {
    override fun onCreate() {
        super.onCreate()

        //第一：默认初始化
        Bmob.initialize(this, "1874120a219777c80300ed885aa1e33d")
    }
}