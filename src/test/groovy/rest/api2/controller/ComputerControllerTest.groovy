package rest.api2.controller

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import rest.api2.domain.Computer
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class ComputerControllerTest extends Specification {
    @Inject
    @Client("/")
    RxStreamingHttpClient client

    def "List的猴子測試"() {
        HttpRequest request = HttpRequest.GET("http://localhost:8080/computer")

        HttpResponse<List<Computer>> rsp = client.toBlocking().exchange(request,
                Argument.listOf(Computer))
        """use rsp.body to get result"""
        expect:
        rsp.body() != []
        "琮焊得蘋果" in rsp.body().collect { computer -> computer.name}
    }

    def "Get的monkeyTest"() {
        HttpRequest request = HttpRequest.GET("http://localhost:8080/computer/5f51abb4514b4b1322b27a1a")

        HttpResponse<Computer> rsp = client.toBlocking().exchange(request,
                Argument.of(Computer))
        """use rsp.body to get result"""
        expect:
        rsp.body().name == "琮焊得蘋果"

    }

    def "Update的monkeyTest"() {
        String newName = "forPutapiTest"+Math.random().toString()
        HttpRequest request = HttpRequest.PUT("http://localhost:8080/computer/5f51abcf514b4b1322b27a1b/"+newName,String)

        HttpResponse<Computer> rsp = client.toBlocking().exchange(request,
                Argument.of(Computer))
        expect:
        rsp.complete == true
        rsp.body().name == newName
    }

    def "Delete 和 Save的monkeyTest"() {
        HttpRequest request = HttpRequest.POST("http://localhost:8080/computer/forDELApiTest",String)

        HttpResponse<Computer> rsp1 = client.toBlocking().exchange(request,
                Argument.of(Computer))
        request = HttpRequest.DELETE("http://localhost:8080/computer/"+rsp1.body().id,String)
        HttpResponse<Computer> rsp2 = client.toBlocking().exchange(request,
                Argument.of(Computer))
        expect:
        rsp1.complete == true
        rsp1.body().name == "forDELApiTest"
        rsp2.complete == true

    }
}
