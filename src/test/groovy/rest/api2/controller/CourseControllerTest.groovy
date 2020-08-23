package rest.api2.controller

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import rest.api2.domain.Course
import spock.lang.Specification


import javax.inject.Inject

@MicronautTest
class CourseControllerTest extends Specification {
    @Inject
    @Client("/")
    RxStreamingHttpClient client

    def "List的猴子測試"() {
        HttpRequest request = HttpRequest.GET("http://localhost:8080/course")

        HttpResponse<List<Course>> rsp = client.toBlocking().exchange(request,
                Argument.listOf(Course))
        """use rsp.body to get result"""
        expect:
        rsp.body() != []
    }

    def "Get的monkeyTest"() {
        HttpRequest request = HttpRequest.GET("http://localhost:8080/course/5f3ba356a5b9ab46b4d76033")

        HttpResponse<Course> rsp = client.toBlocking().exchange(request,
                Argument.of(Course))
        """use rsp.body to get result"""
        expect:
        rsp.body().name == "apitestGet"

    }

    def "Save的monkeyTest"() {
        HttpRequest request = HttpRequest.POST("http://localhost:8080/course/forpostApiTest",String)

        HttpResponse<Course> rsp1 = client.toBlocking().exchange(request,
                Argument.of(Course))
        println(rsp1.body().id.toString())
//        request = HttpRequest.DELETE("http://localhost:8080/course/"+rsp1.body().id,String)
//        Todo when insert objectId value change
//        solution workAround get by name

//        HttpResponse<Course> rsp2 = client.toBlocking().exchange(request,
//                Argument.of(Course))

        expect:
        rsp1.complete == true
//        rsp2.complete == true

//        Todo aftertest clean data
    }

    def "Update"() {
        String newName = "forPutapiTest"+Math.random().toString()
        HttpRequest request = HttpRequest.PUT("http://localhost:8080/course/5f3ba4e567f85562ce9dae01/"+newName,String)

        HttpResponse<Course> rsp = client.toBlocking().exchange(request,
                Argument.of(Course))
        expect:
        rsp.complete == true
    }

    def "Delete"() {
    }
}
