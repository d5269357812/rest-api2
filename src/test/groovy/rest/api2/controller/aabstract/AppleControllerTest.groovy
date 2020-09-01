package rest.api2.controller.aabstract

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import rest.api2.domain.aabstract.Apple
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class AppleControllerTest extends Specification {
    @Inject
    @Client("/")
    RxStreamingHttpClient client

    def "Update的monkeyTest"() {
        String newName = "forPutapiTest"+Math.random().toString()
        Random random  = new Random();List<String> givenList = ["red","green"]
        String newColor   = givenList.get(random.nextInt(givenList.size()))
        HttpRequest request = HttpRequest.PUT("http://localhost:8080/apple/5f4e05fbe5e2364d096e04c4/$newName/$newColor",String)

        HttpResponse<Apple> rsp = client.toBlocking().exchange(request,
                Argument.of(Apple))
        expect:
        rsp.complete == true
        rsp.body().name == newName
        rsp.body().color == newColor
    }

    def "Delete 和 Save的monkeyTest"() {
        HttpRequest request = HttpRequest.POST("http://localhost:8080/apple/forDELApiTest/red",String)

        HttpResponse<Apple> rsp1 = client.toBlocking().exchange(request,
                Argument.of(Apple))
        request = HttpRequest.DELETE("http://localhost:8080/apple/"+rsp1.body().id,String)
        HttpResponse<Apple> rsp2 = client.toBlocking().exchange(request,
                Argument.of(Apple))
        expect:
        rsp1.complete == true
        rsp1.body().name == "forDELApiTest"
        rsp2.complete == true

    }
}
