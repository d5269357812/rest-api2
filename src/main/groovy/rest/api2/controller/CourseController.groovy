package rest.api2.controller

import rest.api2.domain.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
//TODO retuen xml ref 	: https://dzone.com/articles/micronaut-mastery-return-response-based-on-http-ac
@Controller("/course")
class CourseController {

	private Course course
	@Get("/")
	static List<Course> list() {
		Course.list()
	}

	@Get("/{id}")
	Course get(String id) {
		course = Course.get(id)
	}

//	TODO learn how to use gorm dirty check
//	TODO null input 實做
	@Post("/{name}")
	Course save(String name) {
		course = new Course(name: name)
		Course.withNewSession { course.insert(flush:true) }
	}

	@Put("/{id}/{name}")
	Course update(String id,String name) {
		course = Course.get(id)
		course.name = name
		Course.withNewSession {
			course.validate()
			course.save(flush:true)
		}
	}

	@Delete("/{id}")
	void delete(String id) {
		course = Course.get(id)
		Course.withNewSession {
			course.delete(flush:true)
			HttpResponse.ok()
		}
	}
}