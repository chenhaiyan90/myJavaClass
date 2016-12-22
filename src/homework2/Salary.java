package homework2;

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

    public Salary(byte[] name, int baseSalary, int bonus) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }

}
