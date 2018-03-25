#!/usr/bin/env kscript
//DEPS com.baulsupp:oksocial:1.37.0

import com.baulsupp.oksocial.kotlin.*
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import java.net.URLEncoder

data class User(val id_str: String, val name: String, val screen_name: String, val location: String, val description: String)
data class Media(val id_str: String, val media_url_https: String, val display_url: String, val expanded_url: String, val type: String, val sizes: Map<String, Any> = mapOf())
data class Entities(val hashtags: List<Any> = listOf(), val symbols: List<Any> = listOf(), val user_mentions: List<Any> = listOf(), val urls: List<Any> = listOf(), val media: List<Media> = listOf())
data class Tweet(val id_str: String, val full_text: String, val user: User, val entities: Entities? = null)
data class SearchResults(val statuses: List<Tweet>)

var argumentString = arguments.joinToString("+") { URLEncoder.encode(it, "UTF-8") };

runBlocking {
  val results = client.query<SearchResults>(
          "https://api.twitter.com/1.1/search/tweets.json?tweet_mode=extended&q=$argumentString")

  val images = results.statuses.map {
    it.id_str to it.entities?.media?.map {
      async {
        client.execute(request("${it.media_url_https}:thumb"))
      }
    }
  }.toMap()

  results.statuses.forEach { tweet ->
    println("%-20s: %s".format(tweet.user.screen_name, tweet.full_text))

    images[tweet.id_str]?.forEach {
      showOutput(it.await())
      println()
    }
  }
}
