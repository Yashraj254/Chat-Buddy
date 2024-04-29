package com.yashraj.chat_data.utils

import com.yashraj.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

abstract class SafeApiCall {

    // we'll use this function in all
    // repos to handle api errors.
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {

        // Returning api response
        // wrapped in Resource class
        return withContext(Dispatchers.IO) {
            try {

                // Here we are calling api lambda
                // function that will return response
                // wrapped in Retrofit's Response class
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    // In case of success response we
                    // are returning Resource.Success object
                    // by passing our data in it.
                    Resource.Success(data = response.body()!!)
                } else {

                    // Simply returning api's own failure message
                    Resource.Error("Something went wrong. Code: ${response.code()}")
                }

            } catch (e: HttpException) {
                // Returning HttpException's message
                // wrapped in Resource.Error
                Resource.Error(errorMessage = e.message ?: "Something went wrong.")
            }
//            catch (e: IOException) {
//                // Returning no internet message
//                // wrapped in Resource.Error
//                Resource.Error("Please check your network connection. ${e.message} ${e.localizedMessage} ${e.cause} ${e.stackTrace} ")
//            }
            catch (e: Exception) {
                // Returning 'Something went wrong' in case
                // of unknown error wrapped in Resource.Error
                Resource.Error(errorMessage = "${e.message} ")
            }
        }
    }
}