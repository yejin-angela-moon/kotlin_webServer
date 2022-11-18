package webserver

// write your web framework code here:
//http://www.google.com/search?q=kotlin&safe=active

fun scheme(url: String): String = url.substringBefore("://")

fun host(url: String): String = url.substringAfter("://").substringBefore("/")

fun path(url: String): String =
  when {
    url.contains("?") -> url.substringAfter(host(url)).substringBefore("?")
    else -> url.substringAfter(host(url))
  }

fun queryParams(url: String): List<Pair<String, String>> =
  when {
    url.substringAfter(host(url)) == "/" -> emptyList()
    else ->
      url.substringAfter("?").split("&").map { x -> Pair(x.substringBefore("="), x.substringAfter("=")) }
  }


// http handlers for a particular website...

fun homePageHandler(request: Request): Response = Response(Status.OK, "This is Imperial.")

