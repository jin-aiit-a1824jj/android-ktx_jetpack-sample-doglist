package a1824jj.jp.ac.aiit.dogs_sampel.view

import android.os.Bundle
import androidx.fragment.app.Fragment

import a1824jj.jp.ac.aiit.dogs_sampel.R
import a1824jj.jp.ac.aiit.dogs_sampel.databinding.FragmentDetailBinding
import a1824jj.jp.ac.aiit.dogs_sampel.model.DogPaletter
import a1824jj.jp.ac.aiit.dogs_sampel.util.getProgressDrawable
import a1824jj.jp.ac.aiit.dogs_sampel.util.loadImage
import a1824jj.jp.ac.aiit.dogs_sampel.viewmodel.DetailViewModel
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private var dogUuid = 0

    private lateinit var viewModel: DetailViewModel

    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        setHasOptionsMenu(true)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
        }
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch(dogUuid)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dogLiveData.observe(this, Observer {
            dataBinding.dog = it
            it.imageUrl?.let { url ->
                setupBackGroundColor(url)
            }
        })
    }

    private fun setupBackGroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate {
                            val intColor = it?.lightMutedSwatch?.rgb ?: 0
                            val myPalette = DogPaletter(intColor)
                            dataBinding.palettee = myPalette
                        }
                }

            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_send_sms -> {

            }

            R.id.action_share -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }
}
