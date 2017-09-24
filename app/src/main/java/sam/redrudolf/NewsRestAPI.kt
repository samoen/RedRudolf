package sam.redrudolf

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Sam on 20/07/2017.
 */
class NewsRestAPI {
    private val redditApi: sam.redrudolf.RedditAPI

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        redditApi = retrofit.create(sam.redrudolf.RedditAPI::class.java)
    }

    fun getRedditTop(after: String, limit: String): Call<RedditNewsResponse> {
        return redditApi.getTop(after, limit)
    }
}