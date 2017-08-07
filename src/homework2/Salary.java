package homework2;

import java.util.Arrays;

/**
 * Created by chenhaiyan on 2016/12/19.
 */
public class Salary {
    byte[] name;
    int baseSalary;
    int bonus;

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public byte[] getName() {
        return name;
    }

    public void setName(byte[] name) {
        this.name = name;
    }

    public Salary() {
        super();
    }

    @Override
    public String toString() {
        return "Salary{" +
                "name=" + Arrays.toString(name) +
                ", baseSalary=" + baseSalary +
                ", bonus=" + bonus +
                '}';
    }

    public Salary(byte[] name, int baseSalary, int bonus) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }

}
