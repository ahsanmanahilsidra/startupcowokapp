package Adopters

import Models.Attandence
import Models.user
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.AttandenceholderBinding
import com.example.starupcowokapp.databinding.ChatHolderBinding
import com.squareup.picasso.Picasso
import utils.openWhatsAppChat

class AttandenceAdapter (var context: Context, var attandencelist: ArrayList<Attandence>): RecyclerView.Adapter<AttandenceAdapter.ViewHolder>()  {
    inner class ViewHolder(var binding: AttandenceholderBinding) : RecyclerView.ViewHolder(binding.root) {
    }
    fun setfilterList(attendencelist: ArrayList<Attandence>){
        this.attandencelist=attandencelist
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = AttandenceholderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return attandencelist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.date.setText(attandencelist.get(position).date)
        holder.binding.time.setText(attandencelist.get(position).time)
        holder.binding.attandence.setText(attandencelist.get(position).Attandence)


    }
}

