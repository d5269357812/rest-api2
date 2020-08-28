package rest.api2.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import grails.gorm.annotation.Entity

/**
 * <p>Title: O/R Mapping(For Course)</p>
 * <p>Description: BaseDAO/SqlObj/ResultSetObj/</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: Free Open Everybody Use</p>
 * @author PA-ORM TableMining 
 * @desc Groovy ORM 1.0.3 (INNER_RELATION/課程)
 */
@Entity
class InnerRelation {

	String 			id
	String			name
	String		 	mate_id

	static mapping = {
		collection 'INNER_RELATION'
		database 'Cluster0'
		comment '人類 task 兩 doc 一對一關聯 https://tsunglife.atlassian.net/browse/SFIM-57?atlOrigin=eyJpIjoiZWU1ZTM4MjJjMTM2NDczNDg3OGFiMTQ5ODQ5MGZjMWYiLCJwIjoiaiJ9'
			id 					generator: 'uuid', 			attr:"_id"
			name				attr:"NAME",				comment:"名字",	ignoreNotFound: true
			mate_id				attr:"MATE",				comment:"伴侶", 	ignoreNotFound: true
	}


	static constraints = {
		name				nullable:false, blank: false
		mate_id				nullable:true
	}

}

