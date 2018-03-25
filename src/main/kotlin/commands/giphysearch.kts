#!/usr/bin/env kscript
//DEPS com.baulsupp:oksocial:1.37.0

import com.baulsupp.oksocial.kotlin.*
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking

data class Pagination(val total_count: Int, val count: Int, val offset: Int)
data class Meta(val status: Int, val msg: String, val response_id: String)
data class Image(val url: String?, val width: String?, val height: String?, val size: String?, val mp4: String?, val mp4_size: String?, val webp: String?, val webp_size: String?)
data class ImageResult(val type: String, val id: String, val url: String, val images: Map<String, Image>)
data class SearchResults(val data: List<ImageResult>, val pagination: Pagination, val meta: Meta)

val size = "preview_gif"

runBlocking {
  val urls = client.query<SearchResults>(
    "https://api.giphy.com/v1/gifs/search?q=" + arguments.joinToString(
      "+")).data.mapNotNull { it.images[size]?.url }

  val fetches = urls.map {
    async {
      client.execute(request(it))
    }
  }

  fetches.forEach {
    showOutput(it.await())
  }
}
