package com.elano.spotify.controller

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.View
import android.widget.Toast
import com.elano.spotify.R
import com.elano.spotify.model.SongInfo
import com.elano.spotify.view.RecyclerItemClickListener
import com.elano.spotify.view.SongAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerItemClickListener.ClickListener {

    private lateinit var mSongList: ArrayList<SongInfo>
    private lateinit var mAdapter: SongAdapter
    private lateinit var mSongCursor: Cursor
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSelectedItem: SparseBooleanArray

    companion object {
        const val SONG_DATA = "key-song-data"
        const val REQUEST_CODE_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSongList = ArrayList()
        mSelectedItem = SparseBooleanArray()
        mAdapter = SongAdapter(mSelectedItem, mSongList)
        mRecyclerView = recyclerView
        tvHeading.text = getString(R.string.text_heading)
        setSupportActionBar(toolbar)
        recyclerViewInit()
        prepareSongData()
    }

    private fun recyclerViewInit() {
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.adapter = mAdapter
        mRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(this, this))
    }

    override fun onItemClick(view: View, position: Int) {
        val fragmentContainer = fragment; val musicFragment = MusicFragment()
        val bundle = Bundle(); val songPosition = mSongList[position]

        if (mSelectedItem.get(position, false)) {
            mSelectedItem.delete(position)
            view.isSelected = false
        }
        else {
            bundle.putParcelable(SONG_DATA, songPosition)
            musicFragment.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.fragment, musicFragment).commit()
            fragmentContainer.visibility = View.VISIBLE
            mSelectedItem.put(position, true)
            view.isSelected = true
        }
    }

    @SuppressLint("InlinedApi")
    private fun prepareSongData() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION)
        else {
            mSongCursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null)
            while (mSongCursor.moveToNext()) {
                val title = mSongCursor.getString(mSongCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                val artist = mSongCursor.getString(mSongCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val album = mSongCursor.getString(mSongCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                val path = mSongCursor.getString(mSongCursor.getColumnIndex(MediaStore.Audio.Media.DATA))

                mSongList.add(SongInfo(title, artist, album, path))
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSION && (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            prepareSongData()
        }
    }
}
