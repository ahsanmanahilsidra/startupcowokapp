package Adapters

import Adopters.OnboardingItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.starupcowokapp.R

class OnboardingItemAdapter(private val onboardingItems: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingItemAdapter.OnboardingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.onbordingitem,
            parent,
            false
        )
        return OnboardingItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingItemViewHolder, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }

    inner class OnboardingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageOnboarding = view.findViewById<ImageView>(R.id.imageOnboarding)
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)
        private val textDescription = view.findViewById<TextView>(R.id.textDescription)

        fun bind(onboardingItem: OnboardingItem) {
            imageOnboarding.setImageResource(onboardingItem.onboardingImage)
            textTitle.text = onboardingItem.title
            textDescription.text = onboardingItem.description
        }
    }
}
