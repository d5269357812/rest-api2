package rest.api2.controller

import rest.api2.domain.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.hateoas.JsonError
import io.reactivex.Single
//TODO retuen xml ref 	: https://dzone.com/articles/micronaut-mastery-return-response-based-on-http-ac
@Controller("/course")
class CourseController {

	private Course course
//Todo 拔除觀察者模式 Single
	@Get("/")
	Single<HttpResponse<?>> list() {
		List courses
		courses = Course.list()

		Single.just(courses).map({ result ->
			HttpResponse.ok(result)
		}).onErrorReturn({ throwable ->
			new JsonError(throwable.message)
		})
	}

	@Get("/{id}")
	Single<HttpResponse<?>> get(String id) {
		course = Course.get(id)
		Single.just(course).map({ result ->
			HttpResponse.ok(result)
		}).onErrorReturn({ throwable ->
			new JsonError(throwable.message)
		})
	}

//	TODO learn how to use gorm dirty check
	@Post("/{name}")
	Single<Course> save(String name) {
		course = new Course(name: name)
		Course.withNewSession { Single.just( course.insert(flush:true) ) }
	}

	@Put("/{id}/{name}")
	Single<Course> update(String id,String name) {
		course = Course.get(id)
		course.name = name
		Course.withNewSession {
			course.validate()
			Single.just(course.save(flush:true))
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