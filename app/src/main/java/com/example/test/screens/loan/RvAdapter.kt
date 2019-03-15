package com.example.test.screens.loan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.test.R
import com.example.test.retrofit.Loan
import com.squareup.picasso.Picasso

class RvAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<RvAdapter.Holder>() {
    private var listLoan: List<Loan>? = null
    var listenerCard: (Loan) -> Unit = {}
    var listenerLink:(String)->Unit ={}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.holder, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return if (listLoan != null) {
            listLoan!!.size
        } else 0
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.bind(listLoan!![position])
    }

    fun setLoan(loans: List<Loan>) {
        listLoan = loans
        notifyDataSetChanged()
    }

    inner class Holder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

        val pic = view.findViewById<ImageView>(R.id.ivPic)
        val summary = view.findViewById<TextView>(R.id.tvSummary)
        val card = view.findViewById<androidx.cardview.widget.CardView>(R.id.cvLoan)
        val btLink = view.findViewById<Button>(R.id.btLink)

        fun bind(loan: Loan) {

            summary.text = loan.offer_SUMMARY
            Picasso.get()
                .load(loan.offer_PIC)
                .into(pic)
            card.setOnClickListener{
                listenerCard.invoke(loan)
            }
            btLink.setOnClickListener {
                listenerLink.invoke(loan.offer_LINK)
            }
        }
    }
}