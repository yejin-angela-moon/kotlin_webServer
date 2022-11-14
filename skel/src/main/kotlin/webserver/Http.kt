package webserver

// provided files

class Request(val url: String, val authToken: String = "")

class Response(val status: Status, val body: String = "")

enum class Status(val code: Int) {
  OK(200),
  FORBIDDEN(403),
  NOT_FOUND(404)
}

fun helloHandler(request: Request): Response {
  val name =
    when {
      request.url.contains("name=") ->
        when {
          request.url.contains("style=") -> request.url.substringAfter("name=").substringBefore("&")
          else -> request.url.substringAfter("name=")
        }
      else -> "World"
    }
  val body = "Hello, $name!"
  if (request.url.substringAfter("style=") == "shouting") {
    return Response(Status.OK, body.uppercase())
  }
  return Response(Status.OK, body)
}

fun homepageHandler(request: Request): Response {
  val pages = mapOf("/" to "Imperial", "/computing" to "DoC")
  val host = request.url.substringAfter("://").substringBefore("/")
  val path = when {
    request.url.contains("?") -> request.url.substringAfter(request.url.substringAfter("://")
      .substringBefore("/")).substringBefore("?")
    else -> request.url.substringAfter(request.url.substringAfter("://").substringBefore("/"))
  }
  val value = pages.getValue(path)
  if (pages.containsKey(path)) {
    return Response(Status.OK,"This is $value.")
  }
  return Response(Status.NOT_FOUND, "")
}

//fun programmingBooksPageHandler(request: Request): Response {

//}


fun route(request: Request): Response {
  val path =
    when {
      request.url.contains("?") -> request.url.substringAfter(request.url.substringAfter("://")
        .substringBefore("/")).substringBefore("?")
      else -> request.url.substringAfter(request.url.substringAfter("://").substringBefore("/"))
    }
  if (path == "/" || path == "/computing") {
    return homepageHandler(request)
    //} else if (path.contains("/search")) {
    //return searchPageHandler(request)
  } else if (path.contains("say-hello")) {
    return helloHandler(request)
  }
  return Response(Status.NOT_FOUND, "")
}

//else if (path == "/browse/books/programming") {
//return programmingBooksPageHandler(request)
//}


//if (request.url.contains("?")) {
//val say = request.url.substringAfter("say-").substringBefore("?")
//} else {
//val say = request.url.substringAfter("say-")
//}