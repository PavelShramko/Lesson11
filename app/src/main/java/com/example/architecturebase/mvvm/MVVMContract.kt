package com.example.architecturebase.mvvm

import androidx.lifecycle.LiveData
import com.example.architecturebase.network.NewPost
import com.example.architecturebase.network.Post

interface MVVMContract {
    interface IPresenter{
        val data: LiveData<List<Post>>
        val new_data: LiveData<NewPost>
        fun getPosts()
        fun getNewPost()

    }
}