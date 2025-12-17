package ma.education.courseservice.service;

import lombok.RequiredArgsConstructor;
import ma.education.courseservice.client.StudentClient;
import ma.education.courseservice.client.StudentDTO;
import ma.education.courseservice.entity.Enrollment;
import ma.education.courseservice.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentClient studentClient;
    private final CourseService courseService;

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscription introuvable"));
    }

    public Enrollment createEnrollment(Enrollment enrollment) {
        // Vérifier que l'étudiant existe via OpenFeign
        try {
            StudentDTO student = studentClient.getStudentById(enrollment.getStudentId());
            System.out.println("Inscription pour l'étudiant: " + student.getFirstName() + " " + student.getLastName());
        } catch (Exception e) {
            throw new RuntimeException("Étudiant introuvable avec l'id: " + enrollment.getStudentId());
        }

        // Vérifier que le cours existe
        courseService.getCourseById(enrollment.getCourseId());

        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }
}