//Employee class to store data of employee details
package com.hashedin.hu;



import com.hashedin.hu.models.CompOff;
import com.hashedin.hu.models.Gender;
import com.hashedin.hu.models.OptionaLeaves;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Entity
public class Employee {
    public Employee(String name,String doj,int leavesCarriedFromLastYear,String gender)
    {
        this.name = name;
        this.joiningDate = LocalDate.parse(doj);
        this.leavesCarriedFromLastYear = leavesCarriedFromLastYear;
        this.gender = Gender.valueOf(gender);
    }

    public int getNoOfLeavesTaken() {
        return noOfLeavesTaken;
    }

    public void setNoOfLeavesTaken(int noOfLeavesTaken) {
        this.noOfLeavesTaken = noOfLeavesTaken;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ArrayList<LocalDate> getLeaveTakenFrom() {
        return leaveTakenFrom;
    }

    public void setLeaveTakenFrom(ArrayList<LocalDate> leaveTakenFrom) {
        this.leaveTakenFrom = leaveTakenFrom;
    }

    public ArrayList<LocalDate> getLeaveTakenTill() {
        return leaveTakenTill;
    }

    public void setLeaveTakenTill(ArrayList<LocalDate> leaveTakenTill) {
        this.leaveTakenTill = leaveTakenTill;
    }

    public CompOff getCompOff() {
        return compOff;
    }

    public void setCompOff(CompOff compOff) {
        this.compOff = compOff;
    }

    public LocalDate getLeavesLastResetOn() {
        return leavesLastResetOn;
    }

    public void setLeavesLastResetOn(LocalDate leavesLastResetOn)
    {
        this.leavesLastResetOn = leavesLastResetOn;
    }

    public OptionaLeaves getOptionaLeaves() {
        return optionaLeaves;
    }

    public void setOptionaLeaves(OptionaLeaves optionaLeaves)
    {
       this.optionaLeaves = optionaLeaves;
    }

    public int getNoOfMaternityLeavesTaken() {
        return noOfMaternityLeavesTaken;
    }

    public void setNoOfMaternityLeavesTaken(int noOfMaternityLeavesTaken) {
        this.noOfMaternityLeavesTaken = noOfMaternityLeavesTaken;
    }

    public Employee(String name, Long id, int noOfLeavesAvailable, Gender gender, String date) {
        this.name = name;
        this.id = id;
        this.totalNoOfLeaves = noOfLeavesAvailable;
        this.gender = gender;
        this.joiningDate = LocalDate.parse(date);
    }
//properties of employee
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false,nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    private int noOfLeavesTaken = 0;
    private int totalNoOfLeaves =0;
    private Gender gender = null;
    //if leave is taken store starting date here
    private ArrayList<LocalDate> leaveTakenFrom = new ArrayList<LocalDate>();
    //if leave is taken store leave end date here
    private ArrayList<LocalDate> leaveTakenTill = new ArrayList<LocalDate>();
    @Embedded
    private CompOff compOff = new CompOff();//Add any compoff here
    private LocalDate joiningDate; //Joining date of employee for leave calculation
    private LocalDate leavesLastResetOn = LocalDate.of(2019,1,1);
    @Embedded
    private OptionaLeaves optionaLeaves = new OptionaLeaves();
    private int noOfMaternityLeavesTaken = 0;
    private LocalDate maternityLeaveFrom;
    private LocalDate maternityLeaveTill;
    private int leavesCarriedFromLastYear = 0;

    public int getLeavesCarriedFromLastYear() {
        return leavesCarriedFromLastYear;
    }

    public void setLeavesCarriedFromLastYear(int leavesCarriedFromLastYear) {
        this.leavesCarriedFromLastYear = leavesCarriedFromLastYear;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }


    public Employee() {
    }

    public LocalDate getMaternityLeaveFrom() {
        return maternityLeaveFrom;
    }

    public void setMaternityLeaveFrom(LocalDate maternityLeaveFrom)
    {
        this.maternityLeaveFrom = maternityLeaveFrom;
    }

    public LocalDate getMaternityLeaveTill() {
        return maternityLeaveTill;
    }

    public void setMaternityLeaveTill(LocalDate maternityLeaveTill)
    {
        this.maternityLeaveTill = maternityLeaveTill;
    }

    public String getName() {
        return name;
    }
    public void addCompoff(LocalDateTime login,LocalDateTime logout)

    {
        this.compOff.setWorkedOn(login,logout);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalNoOfLeaves() {
        return totalNoOfLeaves;
    }

    public void setTotalNoOfLeaves(int totalNoOfLeaves) {
        this.totalNoOfLeaves = totalNoOfLeaves;
    }
    //if leave is approved the store the dates that leave is approved between
    public void addLeave(LocalDate from,LocalDate to)
    {
        this.leaveTakenFrom.add(from);
        this.leaveTakenTill.add(to);
    }


    public void joiningDate(LocalDate of) {
        this.joiningDate = of;
    }
}
