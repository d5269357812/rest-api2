package rest.api2.controller

import grails.gorm.transactions.Transactional
import rest.api2.domain.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
// TODO find new usage replacement of JsonError
//import io.micronaut.http.hateos.JsonError
import io.reactivex.Single

@Controller("/course")
class CourseController {

	private Course course

	@Get("/")
	Single<HttpResponse<?>> list() {
		List courses
		courses = Course.list()

		Single.just(courses).map({ result ->
			HttpResponse.ok(result)
		})
//				.onErrorReturn({ throwable ->
//			new JsonError(throwable.message)
//		})
	}

	@Get("/{id}")
	Single<HttpResponse<?>> get(String id) {
		course = Course.get(id)
		Single.just(course).map({ result ->
			HttpResponse.ok(result)
		})
//		onErrorReturn({ throwable ->
//			new JsonError(throwable.message)
//		})
	}

//	TODO learn how to use gorm dirty check
	@Post("/{name}")
	Single<Course> save(String name) {
		course = new Course(name: name)
		Course.withNewSession { Single.just( course.insert(flush:true) ) }
	}
//
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
	Single<HttpResponse<?>> delete(String id) {
		course = Course.get(id)
		Course.withNewSession {
			Single.just(course.delete(flush: true)).map({ result ->
				HttpResponse.ok(result)
			})
//					.onErrorReturn({ throwable ->
//				new JsonError(throwable.message)
//			})
		}
	}
}