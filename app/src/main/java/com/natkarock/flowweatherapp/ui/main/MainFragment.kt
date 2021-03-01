package com.natkarock.flowweatherapp.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.natkarock.flowweatherapp.R
import com.natkarock.flowweatherapp.databinding.MainFragmentBinding
import com.natkarock.flowweatherapp.model.*
import com.natkarock.flowweatherapp.model.DataConverter.citiesToStrings
import com.natkarock.flowweatherapp.util.setToolbar
import com.natkarock.flowweatherapp.util.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    val viewModel: MainViewModelImpl by viewModels()

    private var searchView: MaterialSearchView? = null
    private val binding: MainFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(binding)
        observeCities()
        observeWeather()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
        val menuItem = menu.findItem(R.id.action_search)
        searchView?.setMenuItem(menuItem)
    }

    private fun initViews(binding: MainFragmentBinding) {
        binding.apply {
            setToolbar(includedToolbar.toolbar)
            searchView = includedToolbar.searchView
            searchView?.apply {
                setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let {
                            if (it.isNotEmpty()) {
                                viewModel.setCitiesSearch(it)
                            }
                        }
                        return true
                    }

                })
            }
        }
    }


    private fun observeCities() {
        viewModel.citiesModel.observe(viewLifecycleOwner) { cities ->
            updateUI(cities) { model ->
                searchView?.setSuggestions(
                    citiesToStrings(model.data).toTypedArray()
                )
                searchView?.setOnItemClickListener { _, _, p2, _ ->
                    searchView?.closeSearch()
                    viewModel.setWeatherSearch(p2)
                }
            }
        }
    }

    private fun observeWeather() {
        viewModel.weatherModel.observe(viewLifecycleOwner) { weather ->
            updateUI(weather) { model ->
                updateWeatherUi(binding, model.data)
            }
        }
    }


    private fun updateWeatherUi(binding: MainFragmentBinding, weather: Weather) {
        val resources = requireContext().resources
        val degreeVal = resources.getString(R.string.temp_value)
        val humVal = resources.getString(R.string.humidity_value)
        binding.apply {
            weatherGroup.visibility = View.VISIBLE
            txtHumidity.text = "${weather.humidity} $humVal"
            txtName.text = weather.city
            txtTemp.text = "${weather.temp} $degreeVal"
        }
    }

    private fun updateLoading(loading: Boolean) {
        if (loading) {
            binding.loading.visibility = View.VISIBLE
            binding.weatherGroup.visibility = View.GONE
        } else {
            binding.loading.visibility = View.GONE
        }
    }

    private fun <T> updateUI(
        model: UIModel<T>,
        successCallback: (model: UIModel.Result<T>) -> Unit
    ) {
        when (model) {
            is UIModel.Loading -> updateLoading(model.loading)
            is UIModel.Error -> {
                updateLoading(false)
                showSnackbar(
                    searchView as View,
                    getValueFromException(model.error)
                )
            }
            is UIModel.Result -> {
                updateLoading(false)
                successCallback.invoke(model)
            }
        }
    }


}