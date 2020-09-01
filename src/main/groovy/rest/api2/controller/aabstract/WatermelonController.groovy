package rest.api2.controller.aabstract

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import rest.api2.domain.aabstract.Watermelon

@Controller("/watermelon")
class WatermelonController {

	private Watermelon watermelon

	@Post("/{name}/{insideColor}")
	Watermelon save(String name,String insideColor) {
		watermelon = new Watermelon(name: name,insideColor: insideColor)
		Watermelon.withNewSession { watermelon.insert(flush:true) }
	}

	@Put("/{id}/{name}/{insideColor}")
	Watermelon update(String id,String name,String insideColor) {
		watermelon = Watermelon.get(id)
		watermelon.name = name;watermelon.insideColor = insideColor
		Watermelon.withNewSession {
			watermelon.validate()
			watermelon.save(flush:true)
		}
	}

	@Delete("/{id}")
	void delete(String id) {
		watermelon = Watermelon.get(id)
		Watermelon.withNewSession {
			watermelon.delete(flush:true)
			HttpResponse.ok()
		}
	}
}