package rest.api2.domain.aabstract
import grails.gorm.annotation.Entity

@Entity
class Watermelon extends Frult {
	String		insideColor

	static mapping = {
			insideColor				attr:"INSIDE_COLOR",				comment:"果肉顏色", ignoreNotFound: true
	}

	static constraints = {
			insideColor				(nullable:true, blank: true)
	}
}

