import java.io.FileWriter

/**
 * Created by liwei on 15-3-17.
 */
object test extends App {
  println(HtmlParse.getMicroBlog(HtmlParse.getDoc("http://weibo.cn/pennyliang")))
  println(HtmlParse.getSearchMicroBlog("云南", HtmlParse.getDoc("http://weibo.cn/search/mblog?hideSearchFrame=&keyword=%E4%BA%91%E5%8D%97&sort=hot&page=1")))
}
