import Fragments.CreateSpace
import Models.Space
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.starupcowokapp.Booking
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.SpaceHolderBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firestore.admin.v1.Index
import com.squareup.picasso.Picasso

class MybookingAdapter(var context: Context, var mybookinglist: ArrayList<Models.Booking>) :
    RecyclerView.Adapter<MybookingAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: SpaceHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    fun setfilterList(mybookinglist: ArrayList<Models.Booking>) {
        this.mybookinglist = mybookinglist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = SpaceHolderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mybookinglist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.delet2.visibility = View.GONE
        holder.binding.edit2.visibility = View.GONE
        FirebaseFirestore.getInstance().collection("space")
            .document(mybookinglist.get(position).spaceid).get().addOnSuccessListener {
                Picasso.get().load(it.data!!["spaceurl"].toString()).placeholder(R.drawable.loading)
                    .into(holder.binding.spaceimg)
            }

        holder.binding.head.setText(mybookinglist.get(position).Spacename)
        holder.binding.amount.setText("Price: ${mybookinglist.get(position).price}")
        holder.binding.spaceimg.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, Booking::class.java)
            intent.putExtra("bookingid", mybookinglist.get(position).bookingid)
            context.startActivity(intent)
        })


    }
}