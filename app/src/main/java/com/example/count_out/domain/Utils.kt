package com.example.count_out.domain

import android.content.Context
import android.content.Context.VIBRATOR_MANAGER_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatTime( sec: String, min: String, hours: String): String = "$hours:$min:$sec"
fun Int.pad(): String = this.toString().padStart(2, '0')
fun String.toDoubleMy(): Double = if (this.isNotEmpty()) this.toDouble() else 0.0
fun String.toIntMy(): Int = if (this.isNotEmpty()) this.toInt() else 0
fun Boolean.to01(): Int = if (this) 1 else 0
fun Double.toPositive(): Double = if (this < 0) 0.0 else this
fun String.toNumeric(): Double{
    var result = ""
    return if (this.isNotEmpty()){
        this.forEach { char->
            if (char in '0'..'9' || char == '.') result += char
        }
        if (result.isNotEmpty()) result.toDouble() else 0.0
    } else 0.0
}
fun Long.getTime(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(date)
}
fun vibrate(context: Context){
    val vibrateApp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService( VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    }
    vibrateApp.vibrate(VibrationEffect.createOneShot(50, 10) )
}

//class TestTTSFile @Inject constructor(val context: Context, val speechManager: SpeechManager) {
//    fun writeToFile(text: String, nameFile: String){
//        speechManager.writeToFile(text, nameFile)
//        context.fileList().forEach { lg("file: $it") }
//    }

//    fun readFile(filename: String){
//        var fileInputStream: FileInputStream? = null
//        fileInputStream = openFileInput(filename)
//        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
//        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
//        val stringBuilder: StringBuilder = StringBuilder()
//        var text: String? = null
//        while({ text = bufferedReader.readLine(); text }() != null) {
//            stringBuilder.append(text)
//        }
////Displaying data on EditText
//        fileData.setText(stringBuilder.toString()).toString()

//        context.openFileInput(filename).bufferedReader().useLines { lines ->
//            lines.fold("") { some, text ->
//                "$some\n$text"
//            }
//        }
//        val audioFile = File("path/to/audio/file.wav")
//        val audioInputStream = AudioSystem.getAudioInputStream(audioFile)
//        val audioFormat = audioInputStream.format
//        val buffer = ByteArray(audioInputStream.available())
//        audioInputStream.read(buffer)

//    }
//    fun existFile(filename: String): Boolean{
//        val files: Array<String> = context.fileList()
//        return files.find { it == filename }?.isNotEmpty() ?: false
//    }

//    fun fileCreate(fileUri: Uri){
//        val envPath: String = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
//        val mp = MediaPlayer.create(context, fileUri)
//        val inputText = "DASDASDFASD"
//        val myHashRender = HashMap<String, String>()
//        myHashRender[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = inputText
//        val destFileName: String = envPath + "/" + "tts_file.wav"
//        val sr: Int = tts.synthesizeToFile(inputText, myHashRender, destFileName)
//        Log.d("TAG", "synthesize returns = $sr")
//        val fileTTS: File = File(destFileName)
//
//
//        if (fileTTS.exists()) Log.d("TAG", "successfully created fileTTS")
//        else Log.d("TAG", "failed while creating fileTTS")

//        val fileUri = Uri.fromFile(fileTTS)
//        Log.d("TAG", "successfully created uri link: " + fileUri.path)
//        btnRead.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                if (mp.isPlaying()) {
//                    mp.pause();
//                    Log.d(TAG, "successfully paused");
//                } else {
//                    mp.start();
//                    Log.d(TAG, "successfully started");
//                }
//            }
//        });
//    }
//}

//fun writeToFile(text: String, nameFile: String){
//        lg("getFeatures ${tts?.getVoices()}")
//        var response: Int? = 0
//        val utteranceId = "utteranceId12345"
//        val textWrite = " Speech text"
//        val fileName = "tts_file" //getFilesDir()
//        val file = File( context.filesDir, fileName )
//        val params = Bundle()
//        params.putString( TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId)
//        try { response = tts?.synthesizeToFile( textWrite, params, file, utteranceId) }
//        catch (e: Exception ) { lg ("error $e")}
//        lg(("response $response"))
//    }