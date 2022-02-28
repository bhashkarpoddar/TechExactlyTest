package com.example.techexactlytest.ui.home.application

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.example.techexactlytest.data.dataSource.remote.RetrofitBuilder
import com.example.techexactlytest.data.dataSource.remote.Status.*
import com.example.techexactlytest.data.dataSource.repository.ApplicationRepository
import com.example.techexactlytest.data.model.Applications
import com.example.techexactlytest.databinding.FragmentApplicationsBinding
import com.example.techexactlytest.ui.home.MainViewModel
import com.example.techexactlytest.utils.getViewModel
import com.example.techexactlytest.utils.gone
import com.example.techexactlytest.utils.toast
import com.example.techexactlytest.utils.visible


class ApplicationsFragment : Fragment() {
    private lateinit var binding: FragmentApplicationsBinding
    private lateinit var viewModel: MainViewModel
    private var adapter: ApplicationAdapter? = null
    private var applicationList: MutableList<Applications>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentApplicationsBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = getViewModel { MainViewModel(ApplicationRepository(RetrofitBuilder.apiService)) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObserver()

        binding.search.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                /*search*/
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                println("Text [$s]")
                adapter?.filter?.filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun initObserver() {
        viewModel.getApplications(378).observe(viewLifecycleOwner) {
            Log.d("TAG", "observe: $it")
            it?.let { resource ->
                Log.d("TAG", "Resource: $resource")
                when (resource.status) {
                    SUCCESS -> {
                        binding.applicationsRv.visible()
                        resource.data?.let { response ->
                            if (!response.data.app_list.isNullOrEmpty()){
                                applicationList?.addAll(response.data.app_list)
                                adapter?.setData(applicationList!!)
                            }
                            Log.d("TAG", "initObserver: $response")
                        }
                    }
                    ERROR -> {
                        binding.applicationsRv.visible()
                        toast(it.message!!)
                    }
                    LOADING -> {
                        binding.applicationsRv.gone()
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        applicationList = mutableListOf()
        adapter = ApplicationAdapter(applicationList!!, object : ApplicationAdapter.Interaction{
            override fun onItemSelected(position: Int, item: Applications) {

            }

            override fun onSwitchSelected(position: Int, item: Applications, isCheck: Boolean) {
                val pos = applicationList!!.indexOf(item)
                applicationList!![pos].isChecked = isCheck
                binding.applicationsRv.post(Runnable {
                    adapter?.notifyItemChanged(position)
                })
            }
        })
        binding.applicationsRv.adapter = adapter
    }

}