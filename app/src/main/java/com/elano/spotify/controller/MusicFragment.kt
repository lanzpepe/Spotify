package com.elano.spotify.controller

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.elano.spotify.R
import com.elano.spotify.model.SongInfo
import com.elano.spotify.view.SongAdapter
import kotlinx.android.synthetic.main.fragment_music.*


/**
 * A simple [Fragment] subclass.
 */
class MusicFragment : Fragment() {

    private lateinit var mTvSongPlay: TextView
    private lateinit var mTvSingerPlay: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val songInfo = this.arguments.getParcelable(SongAdapter.SONG_DATA) as SongInfo

        setViews()
        mTvSongPlay.text = songInfo.name
        mTvSingerPlay.text = songInfo.singer

        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_music, container, false)
    }

    private fun setViews() {
        mTvSongPlay = tvSongPlay
        mTvSingerPlay = tvSingerPlay
    }

}// Required empty public constructor