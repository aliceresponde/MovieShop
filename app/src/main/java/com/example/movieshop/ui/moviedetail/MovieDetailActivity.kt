package com.example.movieshop.ui.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movieshop.R

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        handleIntent()
    }

    /**
     * @link[https://developer.android.com/studio/write/app-link-indexing]
     */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent()
    }

    private fun handleIntent() {
        val appLinkAction = intent.action
        val appLinkData: Uri? = intent.data
        if (Intent.ACTION_VIEW == appLinkAction) {
            appLinkData?.lastPathSegment?.also { movieId -> getMovie(movieId.toInt()) }
        }
    }

    private fun getMovie(id: Int?) {
        Toast.makeText(this, id.toString(), Toast.LENGTH_SHORT).show()
    }
}