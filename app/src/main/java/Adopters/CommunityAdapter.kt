package Adopters

import Models.user
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.CommunityHolderBinding
import com.example.starupcowokapp.databinding.SpaceHolderBinding
import com.squareup.picasso.Picasso

class CommunityAdapter (var context: Context, var communitylist: ArrayList<user>):RecyclerView.Adapter<CommunityAdapter.ViewHolder>()  {
    inner class ViewHolder(var binding: CommunityHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    }
 fun setfilterList(communitylist: ArrayList<user>){
     this.communitylist=communitylist
     notifyDataSetChanged()
 }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityAdapter.ViewHolder {
        var binding = CommunityHolderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(communitylist.get(position).Image).placeholder(R.drawable.loading).into(holder.binding.profileimg)
        holder.binding.Name.setText(communitylist.get(position).Name)
        holder.binding.email.setText(communitylist.get(position).Email)
        holder.binding.contect.setText(communitylist.get(position).Phone_number)
    }

    override fun getItemCount(): Int {
        return communitylist.size
    }




}