package a1824jj.jp.ac.aiit.dogs_sampel.viewmodel

import a1824jj.jp.ac.aiit.dogs_sampel.model.DogBreed
import a1824jj.jp.ac.aiit.dogs_sampel.model.DogDatabase
import a1824jj.jp.ac.aiit.dogs_sampel.model.DogsApiService
import a1824jj.jp.ac.aiit.dogs_sampel.util.SharedPreferencesHelper
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.util.*

class ListViewModel(application: Application) : BaseViewModel(application) {

    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private val dogsService = DogsApiService()
    private val disposable = CompositeDisposable()

    private var prefHelper = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L //5second -> nanosecond


    fun refresh(){
        val updateTime = prefHelper.getUpdateTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        }else{
            fetchFromRemote()
        }
    }

    private fun fetchFromRemote(){
        loading.value = true
        disposable.add(
            dogsService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<DogBreed>>(){
                    override fun onSuccess(t: List<DogBreed>) {
                        storeDogsLocally(t)
                        Toast.makeText(getApplication(), "Dogs retrieved from endpoint", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private fun dogsRetrieved(dogList: List<DogBreed>){
        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }

    private fun storeDogsLocally(dogList: List<DogBreed>){
        launch {
            val dao =  DogDatabase(getApplication()).dogDao()
            dao.deleteAllDog()
            val result = dao.insertAll(*dogList.toTypedArray())
            var i = 0
            while(i < dogList.size){
                dogList[i].uuid = result[i].toInt()
                ++i
            }
            dogsRetrieved(dogList)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }


    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val dogs = DogDatabase(getApplication()).dogDao().getAllDogs()
            dogsRetrieved(dogs)
            Toast.makeText(getApplication(), "Dogs retrieved from database", Toast.LENGTH_LONG).show()
        }
    }

    fun refreshBypassCache() {
        fetchFromRemote()
    }

}