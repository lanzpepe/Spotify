package com.elano.spotify.controller

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.elano.spotify.R
import com.elano.spotify.model.SongInfo
import kotlinx.android.synthetic.main.fragment_music.view.*

/**
 * A simple [Fragment] subclass.
 */
class MusicFragment : Fragment() {

    private lateinit var mSongPlay: TextView
    private lateinit var mSingerPlay: TextView

    @Nullable
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.fragment_music, container, false)
        val song = arguments.getParcelable(MainActivity.SONG_DATA) as SongInfo

        setViews(rootView)
        mSongPlay.text = song.name
        mSingerPlay.text = song.singer

        return rootView
    }

    private fun setViews(view: View) {
        mSongPlay = view.tvSongPlay
        mSingerPlay = view.tvSingerPlay
    }

}// Required empty public constructor