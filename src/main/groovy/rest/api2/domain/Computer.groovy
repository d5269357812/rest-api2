package rest.api2.domain
import grails.gorm.annotation.Entity

@Entity
class Computer {
	String 		id
	String		name
	List<String> services = []
	Map<String,String> users = [:]

	static mapping = {
		collection 'COMPUTER'
		database 'Cluster0'
		comment '課程'
			id 					generator: 'uuid', 			attr:"_id",		    index: true , indexAttributes: [unique:true, dropDCourseups:true]
			name				attr:"NAME",				comment:"課程名稱",   ignoreNotFound: true
			services			attr:"SERVICES",			comment:"服務",		 ignoreNotFound: true
			users				attr:"USERS",				comment:"使用者",	 ignoreNotFound: true
	}


	static constraints = {
		name				(nullable:false, blank: false)
		services			(nullable:true)
		users				(nullable:true)
	}
}