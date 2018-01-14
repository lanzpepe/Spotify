package com.elano.spotify.controller

import android.media.MediaPlayer
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.elano.spotify.R
import com.elano.spotify.model.SongInfo
import com.elano.spotify.view.RecyclerTouchListener
import com.elano.spotify.view.SongAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mSongList: ArrayList<SongInfo>
    private lateinit var mAdapter: SongAdapter
    private lateinit var mFragmentManager: FragmentManager
    private var mMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mTvHeading = tvHeading
        mFragmentManager = supportFragmentManager
        mSongList = ArrayList()
        mAdapter = SongAdapter(mSongList)
        mTvHeading.text = getString(R.string.text_heading)
        setSupportActionBar(toolbar)
        recyclerViewInit()
        prepareSongData()
    }

    private fun recyclerViewInit() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = mAdapter
        recyclerView.addOnItemTouchListener(RecyclerTouchListener(this, recyclerView, object : RecyclerTouchListener.ClickListener {

            override fun onClick(view: View, position: Int) {
                val fragmentContainer = fragment
                val musicFragment = MusicFragment()
                val bundle = Bundle()

                bundle.putParcelable(SONG_DATA, mSongList[position])
                musicFragment.arguments = bundle
                stopPlaying()
                mMediaPlayer = MediaPlayer.create(this@MainActivity, mSongList[position].id)
                mMediaPlayer!!.start()
                mFragmentManager.beginTransaction().replace(R.id.fragment, musicFragment).commit()
                fragmentContainer.visibility = View.VISIBLE
            }

            override fun onLongClick(view: View, position: Int) {
                Toast.makeText(this@MainActivity, "Not Available", Toast.LENGTH_SHORT).show()
            }
        }))
    }

    private fun stopPlaying() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    private fun prepareSongData() {
        mSongList.add(SongInfo(R.raw.what_about_us, "What About Us", "P!nk", "Beautiful Trauma"))
        mSongList.add(SongInfo(R.raw.attention, "Attention", "Charle Puth", "Voicenotes"))
        mSongList.add(SongInfo(R.raw.wolves, "Wolves", "Selena Gomez, Marshmello", "Wolves"))
        mSongList.add(SongInfo(R.raw.ilysb, "ILYSB", "LANY", "Make Out - EP"))
        mSongList.add(SongInfo(R.raw.havana, "Havana", "Camila Cabello, Young Thug", "Havana"))
        mSongList.add(SongInfo(R.raw.new_rules, "New Rules", "Dua Lipa", "Dua Lipa (Deluxe)"))
        mSongList.add(SongInfo(R.raw.logic_alessia_cara_khalid, "1-800-273-8255", "Logic, Khalid, Alessia Cara", "1-800-273-8255"))
        mSongList.add(SongInfo(R.raw.there_for_you, "There For You", "Martin Garrix, Troye Sivan", "There For You"))
        mSongList.add(SongInfo(R.raw.how_long, "How Long", "Charlie Puth", "Voicenotes"))
        mSongList.add(SongInfo(R.raw.galway_girl, "Galway Girl", "Ed Sheeran", "Divide (Deluxe)"))
        mSongList.add(SongInfo(R.raw.dusk_till_dawn, "Dusk Till Dawn", "ZAYN, Sia", "Dusk Till Dawn"))
        mSongList.add(SongInfo(R.raw.i_like_me_better, "I Like Me Better", "Lauv", "I Like Me Better"))
        mSongList.add(SongInfo(R.raw.crying_in_the_club, "Crying in the Club", "Camila Cabello", "Crying in the Club"))
        mSongList.add(SongInfo(R.raw.rain, "Rain", "The Script", "Freedom Child"))
        mSongList.add(SongInfo(R.raw.scared_to_be_lonely, "Scared to be Lonely", "Martin Garrix, Dua Lipa", "Summer Season 2017"))

        mAdapter.notifyDataSetChanged()
    }

    companion object {
        val SONG_DATA = "key-song-data"
    }
}
