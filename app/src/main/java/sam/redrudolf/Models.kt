package sam.redrudolf

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Created by Sam on 20/07/2017.
 */
data class RedditNews(val after: String, val before: String, val news: List<RedditNewsItem>)

data class RedditNewsItem(val title: String, val thumbnail: String, val url: String?) : ViewType {
    override fun getViewType() = AdapterConstants.NEWS
}
class RedditNewsResponse(val data: RedditDataResponse)

class RedditDataResponse(val children: List<RedditChildrenResponse>, val after: String?, val before: String?)

class RedditChildrenResponse(val data: RedditNewsDataResponse)

class RedditNewsDataResponse(val title: String, val thumbnail: String, val url: String?)

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImg(imageUrl: String) {
    if (!TextUtils.isEmpty(imageUrl)) {
        Picasso.with(context).load(imageUrl).into(this)
    }
    if(this.drawable == null){
        this.setImageResource(R.mipmap.ic_launcher_round)
    }
}