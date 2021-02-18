package com.example.architecturebase.mvvm

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturebase.adapter.MainAdapter
import com.example.architecturebase.databinding.FragmentRecyclerBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MVVMViewFragment : Fragment(){

    private val mvvmModel by viewModels<MVVMModel>()

    val mainAdapter = MainAdapter()

    var binding: FragmentRecyclerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecyclerBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewModelProvider(this).get(MVVMModel :: class.java)

        binding?.mainRV?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
        }


        mvvmModel.data.observe(viewLifecycleOwner, Observer {
            mainAdapter.items = it
            binding?.listSRL?.isRefreshing = false

            if (it == null) {
                Toast.makeText(context, "Error. Data is empty.", Toast.LENGTH_SHORT).show()
            }
        })

        mvvmModel.getPosts()

        showStatus()
    }

    private fun showStatus() {
        val onLine = checkInternet()
        if(!onLine) {
            Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkInternet(): Boolean {
        var result = false
        val connect = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connect.activeNetwork ?: return false
            val network =
                connect.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                network.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                network.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                network.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connect.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return result
    }
}