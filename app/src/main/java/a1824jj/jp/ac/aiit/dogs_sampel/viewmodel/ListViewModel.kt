package a1824jj.jp.ac.aiit.dogs_sampel.viewmodel

import a1824jj.jp.ac.aiit.dogs_sampel.model.DogBreed
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {
    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        val dog1 = DogBreed("1", "Corgi", "15 years", "breedGroup", "bredFor", "temperament", "")
        val dog2 = DogBreed("2", "Labrador", "10 years", "breedGroup", "bredFor", "temperament", "")
        val dog3 = DogBreed("3", "Rotwailer", "20 years", "breedGroup", "bredFor", "temperament", "")

        val dogList = arrayListOf(dog1, dog2, dog3)

        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }
}