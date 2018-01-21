package com.elano.spotify.controller

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.elano.spotify.R
import com.elano.spotify.model.SongInfo
import kotlinx.android.synthetic.main.fragment_music.view.*
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 */
class MusicFragment : Fragment(), View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        SeekBar.OnSeekBarChangeListener {

    private lateinit var mTvSongPlay: TextView
    private lateinit var mTvSingerPlay: TextView
    private lateinit var mBtnPlay: Button
    private lateinit var mRunnable: Runnable
    private var songProgressBar: SeekBar? = null
    private var mHandler: Handler? = null
    private var mMediaPlayer: MediaPlayer? = null

    companion object {
        const val UPDATE_FREQUENCY: Long = 1000
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.fragment_music, container, false)
        val song = arguments.getParcelable(MainActivity.SONG_DATA) as SongInfo

        setViews(rootView)

        mMediaPlayer = MediaPlayer()
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
            updateProgressBar()
            view?.setBackgroundResource(R.drawable.ic_pause)
        }
        else {
            mMediaPlayer?.seekTo(mMediaPlayer!!.currentPosition)
            mMediaPlayer?.start()
            updateProgressBar()
            view?.setBackgroundResource(R.drawable.ic_play)
        }
    }

    override fun onPrepared(mediaPlayer: MediaPlayer?) {
        mMediaPlayer?.start()
        songProgressBar?.max = mMediaPlayer!!.duration
        updateProgressBar()
    }

    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        mHandler?.removeCallbacks(mRunnable)
        mMediaPlayer?.release()
        mMediaPlayer = null
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser)
            mMediaPlayer?.seekTo(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) { return }

    override fun onStopTrackingTouch(seekBar: SeekBar?) { return }

    override fun onDestroy() {
        super.onDestroy()
        mHandler?.removeCallbacks(mRunnable)
        mMediaPlayer?.stop()
        mMediaPlayer?.reset()
        mMediaPlayer?.release()
        mMediaPlayer = null
    }

    private fun setViews(view: View) {
        mTvSongPlay = view.tvSongPlay
        mTvSingerPlay = view.tvSingerPlay
        mBtnPlay = view.btnPlay
        songProgressBar = view.seekBar
    }

    fun setHandler(handler: Handler?) {
        this.mHandler = handler
    }

}// Required empty public constructor