package org.example;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Job {

    // "position","domain","city","company","experience","english","salary","technology","specialization","period","title","group","framework"
    // "QA/QC/SDET","Ecommerce",12,1,3,5,1220,"","Manual QA","2024-12",3,2,""
    // "Software Engineer","Adtech / Advertising",1,1,6,4,6500,"Java","Back-end","2024-12",4,1,"Hibernate,Spring,Vue.js,Ruby on Rails"
    // "QA/QC/SDET","Security",1,1,3,5,1700,"","Manual QA","2024-12",3,2,""

    private String position;
    private String domain;
    private String city;
    private int company;
    private int experience;
    private int english;
    private int salary;
    private String technology;
    private String specialization;
    private String period;
    private int title;
    private int group;
    private String framework;

    public String[] toArray() {
        return new String[]{
                position, domain, city,
                String.valueOf(company), String.valueOf(experience), String.valueOf(english),
                String.valueOf(salary), technology, specialization, period,
                String.valueOf(title), String.valueOf(group), framework
        };
    }
}