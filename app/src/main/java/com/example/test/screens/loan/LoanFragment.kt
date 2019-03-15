package com.example.test.screens.loan


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.screens.main.LoanViewModel
import kotlinx.android.synthetic.main.fragment_card.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoanFragment: Fragment(){

    private val viewModel by sharedViewModel<LoanViewModel>()
    private val adapter = RvAdapter()

    companion object {
        private const val TYPE_FILTER = "type_filter"
        fun newInstance(title: String) = LoanFragment().apply {
            arguments = Bundle().apply {
                putString(TYPE_FILTER, title)
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_card,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter

        viewModel.loansLiveData.observe(this, Observer {loans->
            adapter.setLoan(loans.filter { it.offer_TYPE==arguments?.getString(TYPE_FILTER) })
        })

        adapter.listenerLink = {
            viewModel.startLink(it)
        }

    }
}