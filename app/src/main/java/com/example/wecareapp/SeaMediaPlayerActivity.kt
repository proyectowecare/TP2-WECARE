package com.example.wecareapp

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class SeaMediaPlayerActivity : AppCompatActivity() {
    private lateinit var mp: MediaPlayer;
    private var totalTime: Int = 0;
    var tipoMusica = "mar"
    private val db = FirebaseFirestore.getInstance();
    var totalCanciones = 0;
    var canciones: IntArray = intArrayOf(R.raw.mar,R.raw.mar2,R.raw.mar3,R.raw.mar4)
    var posicion=0
    //var imageBtn= findViewById<ImageView>(R.id.musicImg)
    private val database = db.collectionGroup("canciones").whereEqualTo("tipo",tipoMusica)
        .whereEqualTo("orden",posicion).get();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player)
        var song: String
        mp = MediaPlayer()
        db.collectionGroup("canciones").whereEqualTo("tipo",tipoMusica)
            .whereGreaterThan("orden",0).orderBy("orden", Query.Direction.DESCENDING).limit(1).get()
            .addOnSuccessListener {
                    queryNumero -> for (numero in queryNumero){
                totalCanciones = Integer.parseInt(numero.data["orden"].toString());
                Log.d("Mensaje","Total de Canciones encontradas: $totalCanciones")
            }

            }.addOnFailureListener{
                Log.d("Mensaje","No hay nada")
            }

        database.addOnSuccessListener {
                queryCanciones ->
            for(cancion in queryCanciones)
            {
                Log.d("Mensaje","Canción: ${cancion.data["url"]}")
                song = cancion.data["url"].toString()
                Log.d("Mensaje", "Canción Seteada: $song")
                mp = MediaPlayer.create(this, Uri.parse(song))
                //mp.setDataSource(this,Uri.parse(song))
                //mp.prepare()
                Log.d("Mensaje","Total de Canciones encontradas: $totalCanciones")
                totalTime = mp.duration
                //imageBtn.setImageURI(Uri.parse(cancion.data["imagen"].toString()))
            }
        }
        //mp.setDataSource("https://firebasestorage.googleapis.com/v0/b/we-care-9099e.appspot.com/o/Canciones%2Fpiano2.mp3?alt=media&token=f064da5b-8cd4-4b0f-b232-da7e338cb3b2")
        //mp.prepare()
        //mp = MediaPlayer.create(this, canciones[0])
        mp.isLooping = true
        mp.setVolume(0.5f, 0.5f)
        totalTime = mp.duration

        // Volume Bar
        var volumeBar= findViewById<SeekBar>(R.id.volumeBar)
        volumeBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        var volumeNum = progress / 100.0f
                        mp.setVolume(volumeNum, volumeNum)
                    }
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {
                }
                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            }
        )

        // Position Bar
        var positionBar= findViewById<SeekBar>(R.id.positionBar)
        positionBar.max = totalTime
        positionBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        mp.seekTo(progress)
                    }
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {
                }
                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            }
        )

        // Thread
        Thread(Runnable {
            while (mp != null) {
                try {
                    var msg = Message()
                    msg.what = mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                }
            }
        }).start()
    }
    override fun onDestroy() {
        super.onDestroy()
        if(mp.isPlaying) {
            mp.stop();
        }
        //mp.release();
        mp.reset();
    }
    @SuppressLint("HandlerLeak")
    var handler = object : Handler() {

        override fun handleMessage(msg: Message) {
            var positionBar= findViewById<SeekBar>(R.id.positionBar)
            var elapsedTimeLabel= findViewById<TextView>(R.id.elapsedTimeLabel)
            var remainingTimeLabel= findViewById<TextView>(R.id. remainingTimeLabel)

            var currentPosition = msg.what


            // Update positionBar
            positionBar.progress = currentPosition

            // Update Labels
            var elapsedTime = createTimeLabel(currentPosition)
            elapsedTimeLabel.text = elapsedTime

            var remainingTime = createTimeLabel(totalTime - currentPosition)
            remainingTimeLabel.text = "-$remainingTime"
        }
    }

    fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }

    fun playBtnClick(v: View) {
        var playBtn= findViewById<Button>(R.id.playBtn)
        if (mp.isPlaying) {
            // Stop
            mp.pause()
            playBtn.setBackgroundResource(R.drawable.play)

        } else {
            // Start
            mp.start()
            playBtn.setBackgroundResource(R.drawable.stop)
        }
    }

    fun nextBtnClick(v: View) {
        var playBtn= findViewById<Button>(R.id.playBtn)
        if (mp.isPlaying) {
            // Stop
            mp.stop()
            mp.reset()
            playBtn.setBackgroundResource(R.drawable.play)

        }
        posicion=posicion+1
        var nextBtn = findViewById<Button>(R.id.NextBtn)
        //if (posicion > canciones.size-1)
        if(posicion>totalCanciones)
        {
            posicion=0
            mp.pause()
            db.collectionGroup("canciones").whereEqualTo("tipo",tipoMusica)
                .whereEqualTo("orden",posicion).get().addOnSuccessListener {
                        queryCanciones -> for (cancion in queryCanciones){
                    mp = MediaPlayer.create(this,Uri.parse(cancion.data["url"].toString()))
                    //mp.setDataSource(this,Uri.parse(cancion.data["url"].toString()))
                    //mp.prepare()
                    totalTime = mp.duration
                }
                }
            //mp = MediaPlayer.create(this, canciones[posicion])
            //totalTime=mp.duration
            playBtnClick(v)
        }
        else{
            mp.stop()
            mp.reset()
            db.collectionGroup("canciones").whereEqualTo("tipo",tipoMusica)
                .whereEqualTo("orden",posicion).get().addOnSuccessListener {
                        queryCanciones -> for (cancion in queryCanciones){
                    mp = MediaPlayer.create(this,Uri.parse(cancion.data["url"].toString()))
                    //mp.setDataSource(this,Uri.parse(cancion.data["url"].toString()))
                    //mp.prepare()
                    totalTime = mp.duration
                }
                }
            //mp = MediaPlayer.create(this, canciones[posicion])
            //totalTime=mp.duration
            playBtnClick(v)

        }
    }

    fun previousBtnClick(v: View) {
        var playBtn= findViewById<Button>(R.id.playBtn)
        if (mp.isPlaying) {
            mp.stop()
            mp.reset()
            playBtn.setBackgroundResource(R.drawable.play)

        }
        posicion=posicion-1
        var previousBtn = findViewById<Button>(R.id.previousBtn)
        if (posicion<0 )
        {
            posicion = totalCanciones
            //posicion=canciones.size-1
            mp.pause()
            db.collectionGroup("canciones").whereEqualTo("tipo",tipoMusica)
                .whereEqualTo("orden",posicion).get().addOnSuccessListener {
                        queryCanciones -> for (cancion in queryCanciones){
                    mp = MediaPlayer.create(this,Uri.parse(cancion.data["url"].toString()))
                    //mp.setDataSource(this,Uri.parse(cancion.data["url"].toString()))
                    //mp.prepare()
                    totalTime = mp.duration
                }
                }
            //mp = MediaPlayer.create(this, canciones[posicion])
            //totalTime=mp.duration
            playBtnClick(v)
        }
        else{
            mp.stop()
            mp.reset()
            db.collectionGroup("canciones").whereEqualTo("tipo",tipoMusica)
                .whereEqualTo("orden",posicion).get().addOnSuccessListener {
                        queryCanciones -> for (cancion in queryCanciones){
                    mp = MediaPlayer.create(this,Uri.parse(cancion.data["url"].toString()))
                    //mp.setDataSource(this,Uri.parse(cancion.data["url"].toString()))
                    //mp.prepare()
                    totalTime = mp.duration
                }
                }
            //mp = MediaPlayer.create(this, canciones[posicion])
            //totalTime=mp.duration
            playBtnClick(v)

        }
    }


}