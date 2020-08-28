package rest.api2.controller.oneToTwo

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import rest.api2.domain.oneToTwo.Book
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class BookControllerTest extends Specification {
    @Inject
    @Client("/")
    RxStreamingHttpClient client

    def "List的猴子測試"() {
        HttpRequest request = HttpRequest.GET("http://localhost:8080/book")

        HttpResponse<List<Book>> rsp = client.toBlocking().exchange(request,
                Argument.listOf(Book))
        """use rsp.body to get result"""
        expect:
        rsp.body() != []
    }

    def "Get的monkeyTest"() {
        HttpRequest request = HttpRequest.GET("http://localhost:8080/book/5f48faff30c14841fee1c3c4")

        HttpResponse<Book> rsp = client.toBlocking().exchange(request,
                Argument.of(Book))
        """use rsp.body to get result"""
        expect:
        rsp.body().title == "被討厭的勇氣"

    }

    def "Update的monkeyTest"() {
        String newName = "forPutapiTest"+Math.random().toString()
        HttpRequest request = HttpRequest.PUT("http://localhost:8080/book/5f48fb5a30c14841fee1c3c5/"+newName,String)

        HttpResponse<Book> rsp = client.toBlocking().exchange(request,
                Argument.of(Book))
        expect:
        rsp.complete == true
    }

    def "save & Delete的monkeyTest"() {
        HttpRequest request = HttpRequest.POST("http://localhost:8080/book/forDELApiTest",String)

        HttpResponse<Book> rsp1 = client.toBlocking().exchange(request,
                Argument.of(Book))
        request = HttpRequest.DELETE("http://localhost:8080/book/"+rsp1.body().id,String)
        HttpResponse<Book> rsp2 = client.toBlocking().exchange(request,
                Argument.of(Book))
        expect:
        rsp1.complete == true
        rsp2.complete == true

    }
}
