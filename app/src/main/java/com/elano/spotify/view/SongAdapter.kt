package com.elano.spotify.view

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elano.spotify.R
import com.elano.spotify.controller.MusicFragment
import com.elano.spotify.model.SongInfo
import kotlinx.android.synthetic.main.song_list_row.view.*

/**
 * Created by Jess on 12/15/2017.
 */
class SongAdapter(private val songList: ArrayList<SongInfo>) : RecyclerView.Adapter<SongAdapter.MyViewHolder>() {

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder!!.tvName.text = songList[position].name
        holder.tvSinger.text = songList[position].singer
        holder.tvAlbum.text = songList[position].album
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder =
            MyViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.song_list_row, parent, false))

    override fun getItemCount(): Int = songList.size

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container = view.container!!
        val tvName = view.tvNameHolder!!
        val tvSinger = view.tvSingerHolder!!
        val tvAlbum = view.tvAlbumHolder!!
    }

    companion object {
        val SONG_DATA = "key-song"
    }
}