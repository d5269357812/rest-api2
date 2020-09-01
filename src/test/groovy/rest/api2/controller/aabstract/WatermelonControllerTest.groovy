package rest.api2.controller.aabstract

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import rest.api2.domain.aabstract.Watermelon
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class WatermelonControllerTest extends Specification {
    @Inject
    @Client("/")
    RxStreamingHttpClient client

    def "Update的monkeyTest"() {
        String newName = "forPutapiTest"+Math.random().toString()
        String newColor = "black"
        HttpRequest request = HttpRequest.PUT("http://localhost:8080/watermelon/5f4e1551f2fcca4adbc44eec/$newName/$newColor",String)

        HttpResponse<Watermelon> rsp = client.toBlocking().exchange(request,
                Argument.of(Watermelon))
        expect:
        rsp.complete == true
        rsp.body().name == newName
        rsp.body().insideColor == newColor
    }

    def "Delete 和 Save的monkeyTest"() {
        HttpRequest request = HttpRequest.POST("http://localhost:8080/watermelon/forDELApiTest/red",String)

        HttpResponse<Watermelon> rsp1 = client.toBlocking().exchange(request,
                Argument.of(Watermelon))
        request = HttpRequest.DELETE("http://localhost:8080/watermelon/"+rsp1.body().id,String)
        HttpResponse<Watermelon> rsp2 = client.toBlocking().exchange(request,
                Argument.of(Watermelon))
        expect:
        rsp1.complete == true
        rsp1.body().name == "forDELApiTest"
        rsp2.complete == true

    }
}
