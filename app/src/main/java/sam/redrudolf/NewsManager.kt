package sam.redrudolf

import rx.Observable

class NewsManager(private val api: NewsRestAPI = NewsRestAPI()) {

    fun getRedditNews(after: String, limit: String = "10"): Observable<RedditNews> {
        return Observable.create {
            subscriber ->
            val callResponse = api.getRedditTop(after, limit)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                val dataResponse = response.body().data
                val news = dataResponse.children.map {
                    val item = it.data
                    RedditNewsItem(item.title, item.thumbnail, item.url)
                }
                val redditNews = RedditNews(dataResponse.after ?: "", dataResponse.before ?: "", news)
                subscriber.onNext(redditNews)
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}