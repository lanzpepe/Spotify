package com.elano.spotify.controller

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.elano.spotify.R
import com.elano.spotify.model.SongInfo
import com.elano.spotify.view.SongAdapter
import kotlinx.android.synthetic.main.fragment_music.*
import kotlinx.android.synthetic.main.fragment_music.view.*


/**
 * A simple [Fragment] subclass.
 */
class MusicFragment : Fragment() {

    private lateinit var mSongList: ArrayList<String>
    private lateinit var mSingerList: ArrayList<String>
    private var position = 0

    companion object {
        val KEY_SONG = "key-song"
        val KEY_SINGER = "key-singer"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (savedInstanceState != null) {
            mSongList = savedInstanceState.getStringArrayList(KEY_SONG)
            mSingerList = savedInstanceState.getStringArrayList(KEY_SINGER)
        }

        // Inflate the layout for this fragment
        return  inflater!!.inflate(R.layout.fragment_music, container, false)
    }

    fun setSongList(songList: ArrayList<String>) {
        this.mSongList = songList
    }

    fun setSingerList(singerList: ArrayList<String>) {
        this.mSingerList = singerList
    }

}// Required empty public constructor