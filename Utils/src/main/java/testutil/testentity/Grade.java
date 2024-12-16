package testutil.testentity;

import java.util.List;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2023/8/24
 * @description: TODO
 */
public class Grade {
    /**
     * 年级号
     * */
    private String gradeNo;
    /**
     * 年级主任
     * */

    private String gradeTeacher;
    /**
     * 学生
     * */
    List<Student> students;

    public String getGradeNo() {
        return gradeNo;
    }

    public void setGradeNo(String gradeNo) {
        this.gradeNo = gradeNo;
    }

    public String getGradeTeacher() {
        return gradeTeacher;
    }

    public void setGradeTeacher(String gradeTeacher) {
        this.gradeTeacher = gradeTeacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
