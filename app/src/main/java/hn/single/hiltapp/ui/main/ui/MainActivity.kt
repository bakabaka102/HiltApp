package hn.single.hiltapp.ui.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hn.single.hiltapp.data.model.User
import hn.single.hiltapp.databinding.ActivityMainBinding
import hn.single.hiltapp.ui.main.adapters.MainAdapter
import hn.single.hiltapp.ui.main.viewmodel.MainViewModel
import hn.single.hiltapp.utils.Status

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private var mainAdapter = MainAdapter()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupObserver() {
        mainViewModel.users.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { users -> renderDataList(users) }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun renderDataList(users: List<User>) {
        mainAdapter.addData(users)
        mainAdapter.notifyDataSetChanged()
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        mainAdapter = MainAdapter()
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = mainAdapter
    }

}