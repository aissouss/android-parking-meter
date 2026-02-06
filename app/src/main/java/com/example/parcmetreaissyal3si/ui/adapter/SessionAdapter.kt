package com.example.parcmetreaissyal3si.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.parcmetreaissyal3si.R
import com.example.parcmetreaissyal3si.data.Session
import com.example.parcmetreaissyal3si.databinding.ItemSessionBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SessionAdapter : ListAdapter<Session, SessionAdapter.SessionViewHolder>(SessionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val binding = ItemSessionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SessionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SessionViewHolder(
        private val binding: ItemSessionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE)

        fun bind(session: Session) {
            val context = binding.root.context
            binding.tvSessionId.text = context.getString(R.string.session_id, session.id)
            binding.tvSessionDate.text = dateFormat.format(Date(session.dateHeure))
            binding.tvSessionMontant.text = String.format("%.3f DT", session.montant)

            if (session.acheve) {
                binding.tvSessionStatut.text = context.getString(R.string.session_acheve)
                binding.tvSessionStatut.setTextColor(
                    ContextCompat.getColor(context, R.color.status_en_service)
                )
            } else {
                binding.tvSessionStatut.text = context.getString(R.string.session_inacheve)
                binding.tvSessionStatut.setTextColor(
                    ContextCompat.getColor(context, R.color.status_hors_service)
                )
            }
        }
    }

    class SessionDiffCallback : DiffUtil.ItemCallback<Session>() {
        override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean {
            return oldItem == newItem
        }
    }
}
