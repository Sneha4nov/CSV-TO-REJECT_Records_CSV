package com.example.Demostudent.reader;

import java.util.Arrays;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Demostudent.model.SkippedStudent;
import com.example.Demostudent.model.Student;
import com.example.Demostudent.writer.SkipWriter;

@Component
public class ItemSkipListner implements SkipListener<Student, Student> {

	@Autowired
	SkipWriter skipWriter;

	ExecutionContext executionContext;
	
	

	@Override
	public void onSkipInRead(Throwable t) {
		System.out.println("in onSkipInRead --->" + ((FlatFileParseException) t).getInput());
		try {
			SkippedStudent ss = new SkippedStudent();
			ss.setSkippedRecord(((FlatFileParseException) t).getInput());
			skipWriter.write(Arrays.asList(ss));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onSkipInWrite(Student item, Throwable t) {
		System.out.println("in onSkipInWrite --->" + item);

	}

	@Override
	public void onSkipInProcess(Student item, Throwable t) {

		System.out.println("in onSkipInProcess --->" + item);
	}

}
