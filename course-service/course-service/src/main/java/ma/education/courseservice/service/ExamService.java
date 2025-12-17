package ma.education.courseservice.service;

import lombok.RequiredArgsConstructor;
import ma.education.courseservice.entity.Exam;
import ma.education.courseservice.repository.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Exam getExamById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Examen introuvable"));
    }

    public Exam createExam(Exam exam) {
        return examRepository.save(exam);
    }

    public List<Exam> getExamsByCourse(Long courseId) {
        return examRepository.findByCourseId(courseId);
    }

    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }
}