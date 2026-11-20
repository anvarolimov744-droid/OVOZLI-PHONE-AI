package com.ovozli.phoneai
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Toast

class MainActivity: Activity(){
 override fun onCreate(savedInstanceState: Bundle?){super.onCreate(savedInstanceState)
  val layout=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;gravity=Gravity.CENTER;padding=40;setBackgroundColor(Color.rgb(8,13,27))}
  val title=TextView(this).apply{text="OVOZLI PHONE AI";textSize=28f;setTextColor(Color.WHITE);gravity=Gravity.CENTER}
  val info=TextView(this).apply{text="Ovozli boshqaruv xizmatini yoqing. Ishlayotganda doimiy bildirishnoma ko‘rinadi.";textSize=16f;setTextColor(Color.LTGRAY);gravity=Gravity.CENTER;setPadding(0,30,0,30)}
  val start=Button(this).apply{text="Tinglashni boshlash";setOnClickListener{if(checkSelfPermission(Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED)requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO),10) else startVoice()}}
  val stop=Button(this).apply{text="To‘xtatish";setOnClickListener{stopService(Intent(this@MainActivity,VoiceService::class.java));Toast.makeText(this@MainActivity,"To‘xtatildi",Toast.LENGTH_SHORT).show()}}
  layout.addView(title);layout.addView(info);layout.addView(start);layout.addView(stop);setContentView(layout)
 }
 override fun onRequestPermissionsResult(r:Int,p:Array<out String>,g:IntArray){super.onRequestPermissionsResult(r,p,g);if(r==10&&g.isNotEmpty()&&g[0]==PackageManager.PERMISSION_GRANTED)startVoice()}
 private fun startVoice(){startForegroundService(Intent(this,VoiceService::class.java));Toast.makeText(this,"Ovozli xizmat ishga tushdi",Toast.LENGTH_SHORT).show()}
}
