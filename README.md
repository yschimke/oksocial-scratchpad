# oksocial-scratchpad

```
$ brew install holgerbrandl/tap/kscript
$ git clone https://github.com/yschimke/oksocial-scratchpad
```

### Running normal commands

```
$ ./src/main/kotlin/commands/giphysearch.kts peace
```

### Authentication Support 

```
$ oksocial --authenticate twitter
$ ./src/main/kotlin/commands/tweetsearch.kts peace
```

### Extension and Top Level Functions

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
