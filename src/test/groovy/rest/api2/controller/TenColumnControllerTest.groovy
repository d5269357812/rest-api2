package rest.api2.controller

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import rest.api2.domain.TenColumn
import spock.lang.Specification


import javax.inject.Inject

@MicronautTest
class TenColumnControllerTest extends Specification {
    @Inject
    @Client("/")
    RxStreamingHttpClient client

    def "List的猴子測試"() {
        HttpRequest request = HttpRequest.GET("http://localhost:8080/tenColumn")

        HttpResponse<List<TenColumn>> rsp = client.toBlocking().exchange(request,
                Argument.listOf(TenColumn))
        """use rsp.body to get result"""
        expect:
        rsp.body() != []
    }

    def "Get的monkeyTest"() {
        HttpRequest request = HttpRequest.GET("http://localhost:8080/tenColumn/5f4215d005783e23e2324052")

        HttpResponse<TenColumn> rsp = client.toBlocking().exchange(request,
                Argument.of(TenColumn))
        """use rsp.body to get result"""
        expect:
        rsp.body().c1 == "dont"
        rsp.body().c2 == "move"
        rsp.body().c3 == "its for test"

    }

    def "Save的monkeyTest"() {
        HttpRequest request = HttpRequest.POST("http://localhost:8080/tenColumn/forpostApiTest/forpostApiTest/forpostApiTest",String)

        HttpResponse<TenColumn> rsp1 = client.toBlocking().exchange(request,
                Argument.of(TenColumn))
        println(rsp1.body().id.toString())
//        request = HttpRequest.DELETE("http://localhost:8080/tenColumn/"+rsp1.body().id,String)
//        solution workAround get by name

//        HttpResponse<TenColumn> rsp2 = client.toBlocking().exchange(request,
//                Argument.of(TenColumn))

        expect:
        rsp1.complete == true
//        rsp2.complete == true

    }

    def "Update"() {
        String c1 = "forPutapiTestDontMove"+Math.random().toString()
        String c2 = "forPutapiTestDontMove"+Math.random().toString()
        String c3 = "forPutapiTestDontMove"+Math.random().toString()
        HttpRequest request = HttpRequest.PUT("http://localhost:8080/tenColumn/5f4274d9fae86b275579acfa/$c1/$c2/$c3",String)

        HttpResponse<TenColumn> rsp = client.toBlocking().exchange(request,
                Argument.of(TenColumn))
        expect:
        rsp.complete == true
    }

    def "Delete"() {
    }
}
