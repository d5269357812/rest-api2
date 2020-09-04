package rest.api2.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import rest.api2.domain.Computer

@Controller("/computer")
class ComputerController {

	private Computer computer
	@Get("/")
	List<Computer> list() {
		Computer.list()
	}

	@Get("/{id}")
	Computer get(String id) {
		computer = Computer.get(id)
	}

	@Post("/{name}")
	Computer save(String name) {
		computer = new Computer(name: name)
		Computer.withNewSession { computer.insert(flush:true) }
	}

	@Put("/{id}/{name}")
	Computer update(String id,String name) {
		computer = Computer.get(id)
		computer.name = name
		Computer.withNewSession {
			computer.validate()
			computer.save(flush:true)
		}
	}

	@Delete("/{id}")
	void delete(String id) {
		computer = Computer.get(id)
		Computer.withNewSession {
			computer.delete(flush:true)
		}
		HttpResponse.ok()
	}

	@Put("/addService/{id}/{serviceName}")
	Computer addService(String id,String serviceName) {
		computer = Computer.get(id)
		computer.services.add(serviceName)
		Computer.withNewSession {
			computer.validate()
			computer.save(flush:true)
		}
	}

	@Put("/popSezrvice/{id}/{serviceName}")
	Computer popSezrvice(String id,String serviceName) {
		computer = Computer.get(id)
		if(! computer.services.remove(serviceName) ) throw new Exception("The service $serviceName is not ofund")
		Computer.withNewSession {
			computer.validate()
			computer.save(flush:true)
		}
	}

	@Put("/addUser/{id}/{username}/{password}")
	Computer addUser(String id,String username,String password) {
		computer = Computer.get(id)
		computer.users[username] = password
		Computer.withNewSession {
			computer.validate()
			computer.save(flush:true)
		}
	}

	@Put("/lLogin/{id}/{username}/{password}")
	void lLogin(String id,String username,String password) {
		Map<String,String> userMap = Computer.get(id).users
		if (! username in userMap.keySet()) throw new Exception("帳號錯誤")
		else if (userMap[username] != password) throw new Exception("密碼錯誤")
		else return
	}

}