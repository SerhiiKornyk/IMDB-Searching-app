package com.example.mvvmexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mvvmexample.R
import com.example.mvvmexample.databinding.FragmentMainBinding
import com.example.mvvmexample.viewModels.MainFragmentViewModel
import kotlinx.coroutines.flow.collect
import coil.load
import com.example.mvvmexample.models.ObjectApiResponse
import kotlinx.coroutines.launch
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class MainFragment() : Fragment(), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private lateinit var binding: FragmentMainBinding


    val viewModel: MainFragmentViewModel by viewModel(qualifier = named("mainFragmentViewModel"))

    companion object {
        @JvmStatic
        fun newInstance(): MainFragment = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater, container, false)

        subscribeUi()
        configUi()

        return binding.root
    }


    private fun configUi() {
        binding.handler = ClickHandler()
        viewModel.getIds()

    }

    private  fun subscribeUi() {
        lifecycleScope.launch {
            viewModel.fragmentState.collect { state ->
                when (state) {
                    is MainFragmentUiState.SuccessState -> {
                        setVisibleComponent(state.objectApiResponse)
                    }
                    else -> {
                        // handle error
                    }
                }

            }
        }

    }

    private fun setVisibleComponent(objectApi: ObjectApiResponse) {
        when (objectApi.type) {
            getString(R.string.text) -> {
                with(binding) {
                    textView.visibility = View.VISIBLE
                    textView.text = objectApi.message

                    imageView2.visibility = View.GONE
                    webView.visibility = View.GONE
                }
            }
            getString(R.string.image) -> {
                with(binding) {
                    imageView2.visibility = View.VISIBLE
                    imageView2.load(objectApi.url)

                    webView.visibility = View.GONE
                    textView.visibility = View.GONE
                }
            }

            getString(R.string.webview) -> {
                with(binding) {
                    webView.visibility = View.VISIBLE
                    webView.loadUrl(objectApi.url)

                    imageView2.visibility = View.GONE
                    textView.visibility = View.GONE

                }
            }
        }
    }


    inner class ClickHandler() {
        fun btnClick(view: View?) {
            viewModel.changeObject()
        }
    }
}
