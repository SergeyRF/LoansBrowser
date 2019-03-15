package com.example.test.screens.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.test.R
import com.example.test.extensions.setVisibility
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val viewModel  by viewModel<LoanViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        viewModel.singleLink.observe(this, Observer { link->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
        })

        viewModel.typeLiveData.observe(this, Observer { listType->
            adapter.tabs = listType.toMutableList()
            adapter.notifyDataSetChanged()
        })

        viewModel.loadingLiveData.observe(this, Observer { visible ->
            progress.setVisibility(visible)
        })

        viewModel.showRetryLiveData.observe(this, Observer {visible ->
            viewPager.setVisibility(!visible)
            tvReload.setVisibility(visible)
        })

        viewModel.errorLiveData.observe(this, Observer {  error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        })

        tvReload.setOnClickListener {
            viewModel.loadData()
        }
    }
}
