package rest.api2.controller.aabstract

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import rest.api2.domain.aabstract.Apple

@Controller("/apple")
class AppleController {

	private Apple apple

	@Post("/{name}/{color}")
	Apple save(String name , String color = null) {
		apple = new Apple(name: name,color:color)
		Apple.withNewSession { apple.insert(flush:true) }
	}

	@Put("/{id}/{name}/{color}")
	Apple update(String id,String name,String color) {
		apple = Apple.get(id)
		apple.name = name;apple.color = color
		Apple.withNewSession {
			apple.validate()
			apple.save(flush:true)
		}
	}

	@Delete("/{id}")
	void delete(String id) {
		apple = Apple.get(id)
		Apple.withNewSession {
			apple.delete(flush:true)
			HttpResponse.ok()
		}
	}
}