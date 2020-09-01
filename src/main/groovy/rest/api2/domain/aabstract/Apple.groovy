package rest.api2.domain.aabstract
import grails.gorm.annotation.Entity

@Entity
class Apple extends Frult {
	String		color

	static mapping = {
			color				attr:"COLOR",				comment:"表皮顏色", ignoreNotFound: true
	}


	static constraints = {
		color				(nullable:true, blank: true)
	}
}

