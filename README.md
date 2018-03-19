# oksocial-scratchpad

```
$ brew install yschimke/tap/oksocial
$ git clone https://github.com/yschimke/oksocial-scratchpad
```

### Running normal commands

```
$ ./src/main/kotlin/commands/giphysearch.kts peace
```

![Giphy Example](https://media.giphy.com/media/2t9vw2YQroJdS7VfyH/giphy.gif)

https://github.com/yschimke/oksocial-scratchpad/blob/master/src/main/kotlin/commands/giphysearch.kts

```
data class Image(val url: String?, val width: String?, val height: String?, val size: String?, val mp4: String?, val mp4_size: String?, val webp: String?, val webp_size: String?)
data class ImageResult(val type: String, val id: String, val url: String, val images: Map<String, Image>)
data class SearchResults(val data: List<ImageResult>, val pagination: Pagination, val meta: Meta)

...

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
```  

### Authentication Support 

```
$ oksocial --authenticate twitter
$ ./src/main/kotlin/commands/tweetsearch.kts peace
```

### Extension and Top Level Functions

arguments - command line arguments
client - OkHttpClient instance configured with built in authentication support for many services
request - request builder function

https://github.com/yschimke/oksocial/blob/master/src/main/kotlin/com/baulsupp/oksocial/kotlin/kotlin.kt
https://github.com/yschimke/oksocial/blob/master/src/main/kotlin/com/baulsupp/oksocial/kotlin/io.kt
https://github.com/yschimke/oksocial/blob/master/src/main/kotlin/com/baulsupp/oksocial/kotlin/OkShell.kt

### IDE Support

In Intellij, open build.gradle

### Coloured Output

```
data class Status(...) {
  val delay by lazy { between(result.time(), master.time()) }

  val delayInWords by lazy {
    val roughDelay = delay.minusSeconds(delay.seconds % 60).toMillis()
    val color = when {
      delay > ofHours(3) -> RED
      delay > ofMinutes(90) -> MAGENTA
      else -> GREEN
    }
    formatDurationWords(roughDelay, true, true).color(color)
  }
}

println(status.delayInWords)
```

### Customising Requests

```
  val result = query<Status>(
    request("https://xxx/myendpoint") {
        post(form {
          add("user", System.getenv("USER"))
          add("field", "abc")
        })
        cacheControl(CacheControl.FORCE_NETWORK)
      }
    }
  )
```
