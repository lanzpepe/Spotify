package com.elano.spotify.controller

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.elano.spotify.R
import com.elano.spotify.model.SongInfo
import com.elano.spotify.view.RecyclerTouchListener
import com.elano.spotify.view.SongAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mSongs: ArrayList<SongInfo>
    private lateinit var mAdapter: SongAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mSongs = ArrayList()
        mAdapter = SongAdapter(mSongs)
        setSupportActionBar(toolbar)
        initCollapsingToolbar()
        recyclerViewInit()
        prepareSongData()
    }

    private fun initCollapsingToolbar() {
        val ab = appBar
        val ct = collapsingToolbar

        ab.setExpanded(true)
        ct.title = ""
        ab.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1)
                    scrollRange = appBarLayout!!.totalScrollRange
                if (scrollRange + verticalOffset == 0) {
                    ct.title = getString(R.string.app_name)
                    ct.setExpandedTitleTextAppearance(R.style.ProximaNova)
                    ct.setCollapsedTitleTextColor(Color.WHITE)
                    isShow = true
                }
                else if (isShow) {
                    ct.title = ""
                    isShow = false
                }
            }
        })
    }

    private fun recyclerViewInit() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = mAdapter
        recyclerView.addOnItemTouchListener(RecyclerTouchListener(this, recyclerView, object : RecyclerTouchListener.ClickListener {

            override fun onClick(view: View, position: Int) {
                val songInfo = mSongs[position]
                val bundle = Bundle()

                bundle.putParcelable(SongAdapter.SONG_DATA, songInfo)
                val musicFragment = MusicFragment()
                musicFragment.arguments = bundle
                musicFragment.setMenuVisibility(true)
            }

            override fun onLongClick(view: View, position: Int) {
                TODO("not implemented")
            }
        }))
    }

    private fun prepareSongData() {
        mSongs.add(SongInfo("Attention", "Charle Puth", "Voicenotes"))
        mSongs.add(SongInfo("What About Us", "P!nk", "Beautiful Trauma"))
        mSongs.add(SongInfo("Wolves", "Selena Gomez, Marshmello", "Wolves"))
        mSongs.add(SongInfo("ILYSB", "LANY", "Make Out - EP"))
        mSongs.add(SongInfo("Havana", "Camila Cabello, Young Thug", "Havana"))
        mSongs.add(SongInfo("New Rules", "Dua Lipa", "Dua Lipa (Deluxe)"))
        mSongs.add(SongInfo("1-800-273-8255", "Logic, Khalid, Alessia Cara", "1-800-273-8255"))
        mSongs.add(SongInfo("There For You", "Martin Garrix, Troye Sivan", "There For You"))
        mSongs.add(SongInfo("How Long", "Charlie Puth", "Voicenotes"))
        mSongs.add(SongInfo("Galway Girl", "Ed Sheeran", "Divide (Deluxe)"))
        mSongs.add(SongInfo("Dusk Till Dawn", "ZAYN, Sia", "Dusk Till Dawn"))
        mSongs.add(SongInfo("I Like Me Better", "Lauv", "I Like Me Better"))
        mSongs.add(SongInfo("Crying in the Club", "Camila Cabello", "Crying in the Club"))
        mSongs.add(SongInfo("Rain", "The Script", "Freedom Child"))
        mSongs.add(SongInfo("Scared to be Lonely", "Martin Garrix, Dua Lipa", "Summer Season 2017"))
    }
}
