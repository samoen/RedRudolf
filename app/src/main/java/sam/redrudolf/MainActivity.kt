package sam.redrudolf

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import rx.subscriptions.CompositeSubscription
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val newsManager by lazy { NewsManager() }
    private var redditNews: RedditNews? = null
    private var subscriptions = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reddit_recyclerview.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({ requestNews() }, linearLayout))
        }

        if (reddit_recyclerview.adapter == null) reddit_recyclerview.adapter = NewsAdapter(object : NewsDelegateAdapter.onViewSelectedListener{
            override fun onItemSelected(url: String?) {
                if (!url.isNullOrEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }
            }
        })
        requestNews()
    }
    override fun onResume() {
        super.onResume()
        subscriptions = CompositeSubscription()
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }

    private fun requestNews() {

        val subscription = newsManager.getRedditNews(redditNews?.after ?: "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        { retrievedNews ->
                            redditNews = retrievedNews
                            (reddit_recyclerview.adapter as NewsAdapter).addNews(retrievedNews.news)
                        },
                        {
                            e -> Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
                        }
                )
        subscriptions.add(subscription)
    }
}
