package com.example.architecturebase.mvvm

import androidx.lifecycle.*
import com.example.architecturebase.network.NewPost
import com.example.architecturebase.network.Post
import com.example.architecturebase.network.UseCase
import com.example.architecturebase.repository.Repository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class MVVMModel() : ViewModel(), MVVMContract.IPresenter, LifecycleObserver {

    override val data: MutableLiveData<List<Post>> = MutableLiveData()
    private val _new_data: MutableLiveData<NewPost> = MutableLiveData()
    override val new_data: LiveData<NewPost> = _new_data
    private val repository: Repository = Repository()
    private val useCase = UseCase()

    override fun getPosts() {
        repository.postApi.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        useCase.createUseCase(posts)
                        data.postValue(posts)
                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                data.postValue(null)
                t.printStackTrace()
            }
        })
    }

    fun getNewMessage() : Single<NewPost>{
        return repository.getNewPost().map {
            it[0]
        }
    }

    override fun getNewPost() {
        getNewMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    data ->_new_data.postValue(data)
                }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        getNewPost()
    }
}