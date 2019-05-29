package et.edu.aait.courseproject

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
@RequestMapping("/api/courses", produces = ["application/json"])
class CourseController(private val repository: CourseRepository) {

    @GetMapping("/")
    fun findAll():List<Course> = repository.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Course>{
       return repository.findById(id).map {
           course -> ResponseEntity.ok(course)
       }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping
    fun findByCode(@PathParam("code") code: String): ResponseEntity<Course>{
        return repository.findByCode(code).map {
            course -> ResponseEntity.ok(course)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PostMapping(consumes=["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun insertCourse(@RequestBody course: Course): Course =
            repository.save(course)

    @PutMapping("/{id}", consumes=["application/json"])
    fun updateCourse(@PathVariable id: Long, @RequestBody course:Course): ResponseEntity<Course> {

        return repository.findById(id).map { existingCourse ->
            val updatedCourse: Course = existingCourse
                    .copy(title = course.title, ects = course.ects, description = course.description)
            ResponseEntity.ok().body(repository.save(updatedCourse))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteCourse(@PathVariable id: Long): ResponseEntity<Unit> {
        return repository.findById(id).map{ course ->
            repository.delete(course)
            ResponseEntity<Unit>(HttpStatus.NO_CONTENT)
        }.orElse(ResponseEntity.notFound().build())
    }
}