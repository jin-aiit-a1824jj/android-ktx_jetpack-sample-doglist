package a1824jj.jp.ac.aiit.dogs_sampel.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import a1824jj.jp.ac.aiit.dogs_sampel.R
import a1824jj.jp.ac.aiit.dogs_sampel.viewmodel.DetailViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private var dogUuid = 0

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch()

        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid

        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dogLiveData.observe(this, Observer {
            it?.let {
                dogName.text = it.dogBreed
                dogPurpose.text = it.bredFor
                dogTemperament.text = it.temperament
                dogLifespan.text = it.lifeSpan
            }
        })
    }
}
