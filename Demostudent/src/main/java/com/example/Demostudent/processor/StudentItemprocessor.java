package com.example.Demostudent.processor;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.stereotype.Component;

import com.example.Demostudent.model.Student;

@Component
public class StudentItemprocessor implements ItemProcessor<Student, Student> {
	private boolean isExecuted = false;

	@Override
	public Student process(Student student) throws Exception {
		System.out.println("----------in processor-----------");
		try {

			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();

			Set<ConstraintViolation<Student>> violations = validator.validate(student);
			for (ConstraintViolation<Student> violation : violations) {
				System.out.println("Violcation -> " + violation.getMessage());
			}

			if (!isExecuted) {
				float avg, perc, totalMarks = 0;

				totalMarks = (student.getMathsMarks() + student.getScienceMarks() + student.getHindiMarks()
						+ student.getDbmsMarks() + student.getEnglishMarks());

				// setNames(new String[] { "totalMarks" });

				avg = (totalMarks / 5);

				if (avg > 80) {
					System.out.println("----------in 80-----------");

					student.setGrade("A");
				} else if (avg > 60 && avg <= 80) {
					System.out.println("----------in 60-80-----------");

					student.setGrade("B");

				} else if (avg > 40 && avg <= 60) {
					student.setGrade("C");
				} else {
					student.setGrade("D");
				}

				perc = (totalMarks / 500) * 100;
				student.setPercantage(perc);
				student.setTotalMarks(totalMarks);
				System.out.println("student data -> " + student);

				return student;
			} else {
				return null;
			}

		} catch (ValidationException e) {
			Student s = new Student();
			return s;
		}

	}

}
