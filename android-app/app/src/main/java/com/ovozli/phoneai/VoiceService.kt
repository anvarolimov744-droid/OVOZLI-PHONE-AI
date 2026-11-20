package com.ovozli.phoneai
import android.app.*
import android.content.*
import android.os.*
import android.speech.*
import android.net.Uri
import java.util.Locale

class VoiceService: Service(){
 private var recognizer:SpeechRecognizer?=null; private lateinit var tts:android.speech.tts.TextToSpeech
 override fun onCreate(){super.onCreate();tts=android.speech.tts.TextToSpeech(this){tts.language=Locale("uz","UZ")};startForeground(7,notification());beginListening()}
 private fun notification():Notification{val channel="voice_ai";if(Build.VERSION.SDK_INT>=26){val c=NotificationChannel(channel,"Ovozli boshqaruv",NotificationManager.IMPORTANCE_LOW);getSystemService(NotificationManager::class.java).createNotificationChannel(c)};val stop=PendingIntent.getService(this,1,Intent(this,VoiceService::class.java).setAction("STOP"),PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE);return Notification.Builder(this,channel).setContentTitle("Ovozli Phone AI ishlayapti").setContentText("Buyruq aytishingiz mumkin").setSmallIcon(android.R.drawable.ic_btn_speak_now).addAction(Notification.Action.Builder(null,"To‘xtatish",stop).build()).build()}
 override fun onStartCommand(i:Intent?,f:Int,id:Int):Int{if(i?.action=="STOP")stopSelf();return START_NOT_STICKY}
 private fun beginListening(){if(!SpeechRecognizer.isRecognitionAvailable(this))return;recognizer=SpeechRecognizer.createSpeechRecognizer(this);recognizer?.setRecognitionListener(object:RecognitionListener{override fun onReadyForSpeech(p:Bundle?){};override fun onBeginningOfSpeech(){};override fun onRmsChanged(r:Float){};override fun onBufferReceived(b:ByteArray?){};override fun onEndOfSpeech(){};override fun onError(e:Int){if(Build.VERSION.SDK_INT>=31)Handler(mainLooper).postDelayed({listen()},1200)else listen()};override fun onResults(b:Bundle?){val s=b?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.firstOrNull()?.lowercase(Locale("uz"))?:"";command(s);listen()};override fun onPartialResults(b:Bundle?){};override fun onEvent(t:Int,b:Bundle?){} });listen()}
 private fun listen(){val i=Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).putExtra(RecognizerIntent.EXTRA_LANGUAGE, "uz-UZ").putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM).putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,false);try{recognizer?.startListening(i)}catch(_:Exception){}}
 private fun speak(s:String){tts.speak(s,android.speech.tts.TextToSpeech.QUEUE_FLUSH,null,"voice")}
 private fun command(s:String){when{ s.contains("to‘xta")||s.contains("to'xta")-> {speak("Xizmat to‘xtatildi");stopSelf()};s.contains("youtube")->open("https://youtube.com");s.contains("google")->open("https://google.com");s.contains("gmail")->open("https://mail.google.com");s.contains("soat")->speak("Hozir soat "+java.text.SimpleDateFormat("HH:mm",Locale.getDefault()).format(java.util.Date()));s.contains("sana")->speak(java.text.SimpleDateFormat("d MMMM yyyy",Locale("uz")).format(java.util.Date()));s.contains("salom")->speak("Assalomu alaykum");else->speak("Buyruq tushunarsiz")}}
 private fun open(url:String){val i=Intent(Intent.ACTION_VIEW,Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);startActivity(i)}
 override fun onDestroy(){recognizer?.destroy();tts.shutdown();super.onDestroy()};override fun onBind(i:Intent?)=null
}
