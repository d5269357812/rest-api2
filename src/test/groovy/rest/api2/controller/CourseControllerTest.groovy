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
//TODO generate test data auto matic amd clean
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
        "apitestGet" in rsp.body().collect { course -> course.name}
        "5f47aa4e3b3dd548619ed94d" in rsp.body().collect { course -> course.id}
    }

    def "Get的monkeyTest"() {
        HttpRequest request = HttpRequest.GET("http://localhost:8080/course/5f47a9f43b3dd548619ed94c")

        HttpResponse<Course> rsp = client.toBlocking().exchange(request,
                Argument.of(Course))
        """use rsp.body to get result"""
        expect:
        rsp.body().name == "apitestGet"

    }

    def "Update的monkeyTest"() {
        String newName = "forPutapiTest"+Math.random().toString()
        HttpRequest request = HttpRequest.PUT("http://localhost:8080/course/5f47aa4e3b3dd548619ed94d/"+newName,String)

        HttpResponse<Course> rsp = client.toBlocking().exchange(request,
                Argument.of(Course))
        expect:
        rsp.complete == true
        rsp.body().name == newName
    }

    def "Delete 和 Save的monkeyTest"() {
        HttpRequest request = HttpRequest.POST("http://localhost:8080/course/forDELApiTest",String)

        HttpResponse<Course> rsp1 = client.toBlocking().exchange(request,
                Argument.of(Course))
        request = HttpRequest.DELETE("http://localhost:8080/course/"+rsp1.body().id,String)
        HttpResponse<Course> rsp2 = client.toBlocking().exchange(request,
                Argument.of(Course))
        expect:
        rsp1.complete == true
        rsp1.body().name == "forDELApiTest"
        rsp2.complete == true

    }
}
