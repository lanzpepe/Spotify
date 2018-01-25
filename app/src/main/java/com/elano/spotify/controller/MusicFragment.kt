package com.elano.spotify.controller

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.elano.spotify.R
import com.elano.spotify.model.SongInfo
import kotlinx.android.synthetic.main.fragment_music.*
import kotlinx.android.synthetic.main.fragment_music.view.*
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 */
class MusicFragment : Fragment(), View.OnClickListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    private lateinit var mTvSongPlay: TextView
    private lateinit var mTvSingerPlay: TextView
    private lateinit var mBtnPlay: Button
    private var mHandler: Handler? = null
    private var mMediaPlayer: MediaPlayer? = null
    private var mRunnable: Runnable? = null
    private var songProgressBar: SeekBar? = null

    companion object {
        const val UPDATE_FREQUENCY: Long = 100
        const val TAG = "MusicFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.fragment_music, container, false)
        val song = arguments.getParcelable(MainActivity.SONG_DATA) as SongInfo

        setViews(rootView)
        mHandler = Handler()
        mMediaPlayer = MediaPlayer()
        songProgressBar?.progressDrawable?.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN)
        songProgressBar?.progress = 0
        try {
            if (mMediaPlayer!!.isPlaying)
                mMediaPlayer?.reset()
            mMediaPlayer?.setDataSource(song.data)
            mMediaPlayer?.prepare()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mTvSongPlay.text = song.name
        mTvSingerPlay.text = song.singer
        mBtnPlay.setOnClickListener(this)
        mMediaPlayer?.setOnPreparedListener(this)
        mMediaPlayer?.setOnCompletionListener(this)
        songProgressBar?.setOnSeekBarChangeListener(this)

        return rootView
    }

    private fun updateProgressBar() {
        songProgressBar?.progress = mMediaPlayer!!.currentPosition
        if (mMediaPlayer!!.isPlaying) {
            mRunnable = Runnable {
                updateProgressBar()
            }
        }
        mHandler?.postDelayed(mRunnable, UPDATE_FREQUENCY)
    }

    override fun onClick(view: View?) {
        if (mMediaPlayer!!.isPlaying) {
            mMediaPlayer?.pause()
            setPlayImage(view)
        }
        else {
            mMediaPlayer?.seekTo(mMediaPlayer!!.currentPosition)
            mMediaPlayer?.start()
            setPauseImage(view)
        }
        songProgressBar?.progress = mMediaPlayer!!.currentPosition
    }

    override fun onPrepared(mediaPlayer: MediaPlayer?) {
        mMediaPlayer?.start()
        setPauseImage(btnPlayPause)
        songProgressBar?.max = mMediaPlayer!!.duration
        updateProgressBar()
    }

    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        setPlayImage(btnPlayPause)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser)
            mMediaPlayer?.seekTo(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        Log.e(TAG, "Progress: ${seekBar?.progress}")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        Log.e(TAG, "Progress: ${seekBar?.progress}")
    }

    private fun setPlayImage(view: View?) {
        view?.setBackgroundResource(R.drawable.ic_play)
    }

    private fun setPauseImage(view: View?) {
        view?.setBackgroundResource(R.drawable.ic_pause)
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler?.removeCallbacks(mRunnable)
        mMediaPlayer?.stop()
        mMediaPlayer?.release()
        mMediaPlayer = null
    }

    private fun setViews(view: View) {
        mTvSongPlay = view.tvSongPlay
        mTvSingerPlay = view.tvSingerPlay
        mBtnPlay = view.btnPlayPause
        songProgressBar = view.seekBar
    }

}// Required empty public constructor