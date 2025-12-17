package ma.education.studentservice.service;

import lombok.RequiredArgsConstructor;
import ma.education.studentservice.dto.StudentDTO;
import ma.education.studentservice.entity.Student;
import ma.education.studentservice.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec l'id : " + id));
        return convertToDTO(student);
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        // Vérifier si l'email existe déjà
        if (studentRepository.findByEmail(studentDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Un étudiant avec cet email existe déjà");
        }

        Student student = convertToEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec l'id : " + id));

        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setAddress(studentDTO.getAddress());
        student.setPhone(studentDTO.getPhone());
        student.setClasse(studentDTO.getClasse());
        student.setSpecialization(studentDTO.getSpecialization());

        Student updatedStudent = studentRepository.save(student);
        return convertToDTO(updatedStudent);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Étudiant introuvable avec l'id : " + id);
        }
        studentRepository.deleteById(id);
    }

    public List<StudentDTO> searchStudents(String keyword) {
        return studentRepository
                .findByFirstNameContainingOrLastNameContaining(keyword, keyword)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<StudentDTO> getStudentsByClass(String classe) {
        return studentRepository.findByClasse(classe).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Méthodes de conversion
    private StudentDTO convertToDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getDateOfBirth(),
                student.getAddress(),
                student.getPhone(),
                student.getEnrollmentDate(),
                student.getClasse(),
                student.getSpecialization()
        );
    }

    private Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setAddress(dto.getAddress());
        student.setPhone(dto.getPhone());
        student.setClasse(dto.getClasse());
        student.setSpecialization(dto.getSpecialization());
        return student;
    }
}