package a1824jj.jp.ac.aiit.dogs_sampel.model

import io.reactivex.Single
import retrofit2.http.GET

interface DogsApi {

    @GET("/DevTides/DogsApi/master/dogs.json")
    fun getDogs(): Single<List<DogBreed>>

}