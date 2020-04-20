package a1824jj.jp.ac.aiit.dogs_sampel.viewmodel

import a1824jj.jp.ac.aiit.dogs_sampel.model.DogBreed
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {

    val dogLiveData = MutableLiveData<DogBreed>()

    fun fetch() {
        val dog = DogBreed("1", "Corgi", "15 years", "breedGroup", "bredFor", "temperament", "")
        dogLiveData.value = dog
    }

}