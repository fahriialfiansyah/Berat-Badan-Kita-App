package org.d3ifcool.beratbadankita.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.beratbadankita.R
import org.d3ifcool.beratbadankita.data.History
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter (private val mContext: Context, private val mHistory: List<History>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder?>() {

    private var beratID = ""

    var listener: RecyclerViewClickListener? = null

    interface RecyclerViewClickListener {
        fun onItemClicked(view: View, history: History)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.item_riwayat_berat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        val history: History = mHistory[position]

        holder.hapusBtn.setOnClickListener { it ->
            AlertDialog.Builder(mContext).setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle("Hapus Data").setMessage("Apakah kamu yakin?")
                .setPositiveButton("Ya") { _, _ ->
                    history.beratId?.let {
                        FirebaseDatabase.getInstance().getReference("History")
                            .child(beratID).child(it).removeValue()
                    }
                    (mHistory as MutableList<History>).remove(history)
                    Toast.makeText(mContext, "Data berat badan berhasil dihapus", Toast.LENGTH_LONG).show()
                }.setNegativeButton("Tidak", null).show()
            listener?.onItemClicked(it, history)
        }
        holder.hapusBtn.setBackgroundColor(Color.TRANSPARENT)
        holder.dateText.text = history.date.let { date.format(it) }
        holder.noteText.text = history.catatan
        "${history.saatIni.toString()} kg".also { holder.weightText.text = it }
    }

    override fun getItemCount(): Int {
        return mHistory.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var dateText: TextView = itemView.findViewById(R.id.tvDate)
        var noteText: TextView = itemView.findViewById(R.id.tvNote)
        var weightText: TextView = itemView.findViewById(R.id.tvWeight)
        var hapusBtn: ImageButton = itemView.findViewById(R.id.hapusBtn)
    }
}