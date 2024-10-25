package com.example.count_out.domain

import android.content.Context
import android.content.Context.VIBRATOR_MANAGER_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.example.count_out.R
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Month
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Int.pad(): String = this.toString().padStart(2, '0')


fun String.toDoubleMy(): Double = if (this.isNotEmpty()) this.toDouble() else 0.0
fun String.toIntMy(): Int = if (this.isNotEmpty()) this.toInt() else 0
fun Boolean.to01(): Int = if (this) 1 else 0
fun Double.minus(): Double = if (this - 0.1 < 0) 0.0 else ((this * 10).toInt() - 1).toDouble()/10
fun Double.plus(): Double = ((this * 10).toInt() + 1).toDouble()/10
fun Float.mRound(): Int =
    if (this > 0) {
        if (this - this.toInt() >= 0.5 ) { this.toInt() + 1 }
        else { this.toInt() }
    } else {
        if (this - this.toInt() >= -0.5 ) { this.toInt() }
        else { this.toInt() - 1 }
    }
fun String.toNumeric(): Double{
    var result = ""
    return if (this.isNotEmpty()){
        this.forEach { char-> if (char in '0'..'9' || char == '.') result += char }
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
val DayOfWeek.rus: Int get() = rusDayOfWeek(this.value)
val Month.rus: Int get() = rusMonth(this.value)

fun rusDayOfWeek(dayOfWeek: Int): Int {
    return when(dayOfWeek){
        1 -> R.string.monday
        2 -> R.string.tuesday
        3 -> R.string.wednesday
        4 -> R.string.thursday
        5 -> R.string.friday
        6 -> R.string.saturday
        7 -> R.string.sunday
        else -> R.string.sunday
    }
}
fun rusMonth(dayOfWeek: Int): Int {
    return when(dayOfWeek){
        1 -> R.string.january
        2 -> R.string.february
        3 -> R.string.march
        4 -> R.string.april
        5 -> R.string.may
        6 -> R.string.june
        7 -> R.string.july
        8 -> R.string.august
        9 -> R.string.september
        10 -> R.string.october
        11 -> R.string.november
        12 -> R.string.december
        else -> R.string.december
    }
}
fun roundMy( value: Float): Int{
    return if (value > 0) {
        if (value - value.toInt() >= 0.5 ) { value.toInt() + 1 }
        else { value.toInt() }
    } else {
        if (value - value.toInt() >= -0.5 ) { value.toInt() }
        else { value.toInt() - 1 }
    }
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