package sam.redrudolf

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Sam on 20/07/2017.
 */
interface RedditAPI {
    @GET("/top.json")
    fun getTop(@Query("after") after: String, @Query("limit") limit: String): Call<RedditNewsResponse>
}