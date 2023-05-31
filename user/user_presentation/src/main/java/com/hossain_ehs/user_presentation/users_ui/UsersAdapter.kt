package com.hossain_ehs.user_presentation.users_ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.hossain_ehs.tracker_presentation.databinding.ItemExistingUserBinding
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.hossain_ehs.core_ui.R
import com.hossain_ehs.user_domain.model.User

class UsersAdapter (private val listener: OnUserItemClickedListener)
    : ListAdapter<User, UsersAdapter.UserViewHolder>(DiffCallBack())
{
    private val viewBinderHelper : ViewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemExistingUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }


        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val currentUser = getItem(position)
            viewBinderHelper.setOpenOnlyOne(true)
            viewBinderHelper.bind(holder.swipeRevealLayout, currentUser.fullName)
            viewBinderHelper.closeLayout(currentUser.fullName)
            holder.bind(currentUser)
    }

    inner class UserViewHolder(private val binding: ItemExistingUserBinding)
        : RecyclerView.ViewHolder(binding.root) {
        var swipeRevealLayout : SwipeRevealLayout
        init {
            binding.apply {
                swipeRevealLayout = swipeLayout
                txtEdit.setOnClickListener{
                    val position = adapterPosition
                    val user = getItem(position)
                    listener.onEditUserClicked(user)
                }
                txtDelete.setOnClickListener{
                    val position = adapterPosition
                    val user = getItem(position)
                    listener.onDeleteUserClicked(user)
                }
                imgBtnGoToMedication.setOnClickListener{
                    val position = adapterPosition
                    val user = getItem(position)
                    listener.onImgGoToMedicationClicked(user)
                }
            }
        }
        fun bind(user: User) {
            itemView.apply {
                binding.apply {
                    txtExistingUserName.text = user.fullName
                    if (user.userImageUri.isNotEmpty()){
                        Glide.with(imgExistingUser.context)
                            .load(Uri.parse(user.userImageUri))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.outline_account_circle_24)
                            .fitCenter()
                            .transform(CenterInside(), RoundedCorners(24))
                            .into(imgExistingUser)
                    }
                }
            }
        }
    }
    class DiffCallBack: DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    interface OnUserItemClickedListener {
        fun onImgGoToMedicationClicked(user: User)
        fun onEditUserClicked(user: User)
        fun onDeleteUserClicked(user: User)
    }
}