package rest.api2.service

import grails.gorm.services.Service
import groovy.transform.CompileStatic
import rest.api2.domain.Course

@Service(Course)
@CompileStatic
interface TenColumnService {

    Course get(Serializable id)

    List<Course> list(Map args)

    Long count()

    Course save(Course Course)
}