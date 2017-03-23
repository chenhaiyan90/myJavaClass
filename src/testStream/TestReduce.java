package testStream;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by chenhaiyan on 2017/1/5.
 */
public class TestReduce {
    public static void main(String[] args) {
        Date date=new Date();
        Instant instant=date.toInstant();
        ZoneId zoneId=ZoneId.systemDefault();
        LocalDateTime localDateTime=LocalDateTime.ofInstant(instant,zoneId);
        List<Person> roster=new LinkedList<>();
        roster.add(new Person("aa1",localDateTime.toLocalDate(), Person.Sex.FEMALE,"aa@qq.com",12));
        roster.add(new Person("bb2",localDateTime.toLocalDate(), Person.Sex.MALE,"bb@qq.com",22));
        roster.add(new Person("aa3",localDateTime.toLocalDate(), Person.Sex.MALE,"aa@qq.com",33));
        roster.add(new Person("cc4",localDateTime.toLocalDate(), Person.Sex.FEMALE,"cc@qq.com",32));
        roster.add(new Person("dd5",localDateTime.toLocalDate(), Person.Sex.FEMALE,"dd@qq.com",43));
        roster.add(new Person("ac6",localDateTime.toLocalDate(), Person.Sex.MALE,"ac@qq.com",52));
        roster.add(new Person("bb7",localDateTime.toLocalDate(), Person.Sex.FEMALE,"bb@qq.com",64));
        double average=roster.stream()
                .filter(person -> person.getGender()== Person.Sex.FEMALE)
//                .forEach(person -> System.out.println(person.getName()));
                .mapToInt(Person::getAge)
                .average()
                .getAsDouble();
        Map<Person.Sex,List<Person>> byGender=
                roster.stream()
                .collect(
                        Collectors.groupingBy(Person::getGender)
                );
        List<Map.Entry<Person.Sex,List<Person>>>
                byGenderList=new ArrayList<>(byGender.entrySet());
        byGenderList.stream()
                .forEach(e->{
                    e.getValue()
                            .stream()
                            .map(Person::getName)
                            .forEach(f->System.out.println(f));
                });

        System.out.println(average);
    }
}
