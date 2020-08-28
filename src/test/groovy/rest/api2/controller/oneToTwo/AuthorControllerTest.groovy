package rest.api2.controller.oneToTwo

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import rest.api2.domain.oneToTwo.Author
import rest.api2.domain.oneToTwo.Book
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class AuthorControllerTest extends Specification {
    @Inject
    @Client("/")
    RxStreamingHttpClient client

//    def "List的猴子測試"() {
//        HttpRequest request = HttpRequest.GET("http://localhost:8080/author")
//
//        HttpResponse<List<Author>> rsp = client.toBlocking().exchange(request,
//                Argument.listOf(Author))
//        """use rsp.body to get result"""
//        expect:
//        rsp.body() != []
//    }

    def "Get的monkeyTest"() {
        HttpRequest request = HttpRequest.GET("http://localhost:8080/author/5f490571dd47f330ac34cec1")

        HttpResponse<Author> rsp = client.toBlocking().exchange(request,
                Argument.of(Author))
        """use rsp.body to get result"""
        expect:
        rsp.body().name == "海賊王"

    }

    def "Update的monkeyTest"() {
        String newName = "forPutapiTest"+Math.random().toString()
        HttpRequest request = HttpRequest.PUT("http://localhost:8080/author/5f490566dd47f330ac34cec0/"+newName,String)

        HttpResponse<Author> rsp = client.toBlocking().exchange(request,
                Argument.of(Author))
        expect:
        rsp.complete == true
    }

    def "Delete 和 Save的monkeyTest"() {
        HttpRequest request = HttpRequest.POST("http://localhost:8080/author/forDELApiTest",String)

        HttpResponse<Author> rsp1 = client.toBlocking().exchange(request,
                Argument.of(Author))
        request = HttpRequest.DELETE("http://localhost:8080/author/"+rsp1.body().id,String)
        HttpResponse<Author> rsp2 = client.toBlocking().exchange(request,
                Argument.of(Author))
        expect:
        rsp1.complete == true
        rsp2.complete == true

    }

    def "出書和close書的 monkeyTest"(){
        """新增兩本書，加到沙特國王。
clean up 刪除兩本書 從沙特國王拔除這兩本書"""
        HttpRequest request = HttpRequest.POST("http://localhost:8080/book/firstBook",String)
        HttpResponse<Book> rsp1 = client.toBlocking().exchange(request, Argument.of(Book))
        request = HttpRequest.POST("http://localhost:8080/book/secondBook",String)
        HttpResponse<Book> rsp2 = client.toBlocking().exchange(request, Argument.of(Book))

        request = HttpRequest.PUT("http://localhost:8080/author/publish/5f490b2a5c4f9c366ba7e890/${rsp1.body().id}/${rsp2.body().id}",String)

        HttpResponse<Author> rsp3 = client.toBlocking().exchange(request, Argument.of(Author))

        request = HttpRequest.PUT("http://localhost:8080/author/closeBook/5f490b2a5c4f9c366ba7e890/${rsp1.body().id}/${rsp2.body().id}",String)

        HttpResponse<Author> rsp4 = client.toBlocking().exchange(request,
                Argument.of(Author))

        request = HttpRequest.DELETE("http://localhost:8080/book/"+rsp1.body().id,String)
        HttpResponse<Book> rsp5 = client.toBlocking().exchange(request,
                Argument.of(Book))

        request = HttpRequest.DELETE("http://localhost:8080/book/"+rsp2.body().id,String)
        HttpResponse<Book> rsp6 = client.toBlocking().exchange(request,
                Argument.of(Book))
        expect:
        rsp3.complete == true
        rsp4.complete == true
        rsp3.body().bookFirst.title == "firstBook"
        rsp3.body().bookSecond.title == "secondBook"
    }
}
