package a1824jj.jp.ac.aiit.dogs_sampel.viewmodel

import a1824jj.jp.ac.aiit.dogs_sampel.model.DogBreed
import a1824jj.jp.ac.aiit.dogs_sampel.model.DogDatabase
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val dogLiveData = MutableLiveData<DogBreed>()

    fun fetch(uuid: Int) {
       launch {
           val dog = DogDatabase(getApplication()).dogDao().getDog(uuid)
           dogLiveData.value = dog
       }
    }

}