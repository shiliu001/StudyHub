package testutil.testentity;

import util.compare.UseMyCompare;

import java.util.List;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2023/8/24
 * @description: TODO
 */
public class School {
    //校长名
    @UseMyCompare(comparator = StringCompare.class)
    private String presidentName;
    //年级
    List<Grade> grades;

    public String getPresidentName() {
        return presidentName;
    }

    public void setPresidentName(String presidentName) {
        this.presidentName = presidentName;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
}
