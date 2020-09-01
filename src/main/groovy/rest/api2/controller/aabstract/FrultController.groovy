package rest.api2.controller.aabstract

import io.micronaut.http.annotation.*
import rest.api2.domain.aabstract.Frult

@Controller("/frult")
class FrultController {

	private Frult frult
	@Get("/")
	List<Frult> list() {
		Frult.list()
	}

	@Get("/{id}")
	Frult get(String id) {
		frult = Frult.get(id)
	}

}