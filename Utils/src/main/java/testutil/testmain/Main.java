package testutil.testmain;

import util.compare.CompareUtil;
import testutil.testentity.Grade;
import testutil.testentity.School;
import testutil.testentity.Student;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2023/8/24
 * @description: TODO
 */
public class Main {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        //小李-》小白，小紫
        //小白-》小绿，小蓝
        //小紫-》小红，小黑
        School school1=initSchool("小李",Arrays.asList("小白","小紫"),Arrays.asList("小绿","小蓝"),Arrays.asList("小红","小黑"));
        School school2=initSchool("小李",Arrays.asList("小白","小紫"),Arrays.asList("小绿","小蓝"),Arrays.asList("小红","小黑"));
        System.out.println(CompareUtil.compareFieldValue(school1, school2));
    }
    private static School initSchool(String presidentName,List<String> teacherNames,List<String>... studentNames){
        School school=new School();
        school.setPresidentName(presidentName);
        List<Grade> grades =teacherNames.stream().map(item->initGrade(item,Arrays.asList(studentNames).get(teacherNames.indexOf(item)))).collect(Collectors.toList());
        school.setGrades(grades);
        return school;
    }
    private static Grade initGrade(String teacherName,List<String> studentNames){
        Grade grade=new Grade();
        grade.setGradeTeacher(teacherName);
        List<Student> students =studentNames.stream().map(Main::initStudent).collect(Collectors.toList());
        grade.setStudents(students);
        return grade;
    }
    private static Student initStudent(String studentName){
        Student student=new Student();
        student.setName(studentName);
        return student;
    }
}
