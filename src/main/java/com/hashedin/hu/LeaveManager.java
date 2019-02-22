//Checks whether leave application is to be approved or rejected
package com.hashedin.hu;

import com.hashedin.hu.models.Gender;
import com.hashedin.hu.models.LeaveResponse;
import com.hashedin.hu.models.LeaveStatus;
import com.hashedin.hu.models.LeaveTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
@Service
public class LeaveManager {
    //creates instance of accural for getting total number of leaves employee has

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public LeaveAccrual getAccural() {
        return accural;
    }

    public void setAccural(LeaveAccrual accural) {
        this.accural = accural;
    }

    public int getLeavesAvailable() {
        return leavesAvailable;
    }

    public void setLeavesAvailable(int leavesAvailable) {
        this.leavesAvailable = leavesAvailable;
    }

    public long getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(long noOfDays) {
        this.noOfDays = noOfDays;
    }

    private LeaveAccrual accural = new LeaveAccrual();
    //initialization of number of leaves available to employee
    private int leavesAvailable = 0;
    private long noOfDays = 0;


    public LeaveResponse applyLeave(LeaveRequest leave)
    {   //Credit check the number of leaves to employee until the requested date
        accural.addLeavesMonthly(leave.getEmployee(),leave.getRequestedDate());
        //calculate the number of leaves employee has
        leavesAvailable = leave.getEmployee().getLeavesCarriedFromLastYear()+leave.getEmployee().getTotalNoOfLeaves() -leave.getEmployee().getNoOfLeavesTaken();
        LeaveResponse response= new LeaveResponse(LeaveStatus.REJECTED,"");
        if(leave.getTypes() == LeaveTypes.MATERNITY)
        {
            if(checkMaternityLeaves(leave))
            {//save the details of maternity leave into employee object
                leave.getEmployee()
                        .setNoOfMaternityLeavesTaken(leave.empoyee.getNoOfMaternityLeavesTaken()+1);
                leave.getEmployee()
                        .setMaternityLeaveFrom(leave.getStartDate());
                leave.getEmployee()
                        .setMaternityLeaveTill(leave.getEndDate());
                //increase the number of maternity leave employee has taken
                response.setStatus(LeaveStatus.APPROVED);
                response.setComment("enjoy the maternity leave");
                leave.empoyee.addLeave(leave.getStartDate(),leave.getEndDate());
                saveToRepository(leave);
                return response;
            }
            else {
                response.setStatus(LeaveStatus.REJECTED);
                response.setComment("You cant apply for materity leave please check conditions");
                return  response;
            }
        }
        //check for valid dates
        isDateValid(leave);

        if(checkForOverlap(leave))
        {   //if leave is duplicate i.e employee has already applied for leave on the same date reject the leave
            response.setStatus(LeaveStatus.REJECTED);
            response.setComment("duplicate request");
            return response;
        }
        //check for paternity leave
        else if(leave.getTypes() == LeaveTypes.PATERNITY)
        {
            if(checkForPaternityLeaves(leave))
            {
                response.setStatus(LeaveStatus.APPROVED);
                leave.empoyee.addLeave(leave.getStartDate(),leave.getEndDate());
                saveToRepository(leave);
                return response;
            }
            response.setStatus(LeaveStatus.REJECTED);
            response.setComment("only MALE can apply for paternity leave");
            return response;

        }
        //check if holiday is of type COMP_OFF
        else if(leave.getTypes()== LeaveTypes.COMP_OFF)
        {
            if(handleCompOff(leave))
            {
                leave.empoyee.getCompOff().setAvailableLeaves(leave.empoyee.getCompOff().getAvailableLeaves()-1);
                response.setStatus(LeaveStatus.APPROVED);
                leave.empoyee.addLeave(leave.getStartDate(),leave.getEndDate());
                saveToRepository(leave);
                return response;
            }
            response.setStatus(LeaveStatus.REJECTED);
            return response;
        }
        else if(leave.getTypes() == LeaveTypes.SABBATICAL)
        {
            if(handleSabbatical(leave))
            {   saveToRepository(leave);
                response.setStatus(LeaveStatus.APPROVED);
            }
            else
            {
                response.setStatus(LeaveStatus.REJECTED);
            }
            return response;
        }
        //if leave is of Normal leave
        if (checkForAvailableLeaves(leave))
        {
            response.setStatus(LeaveStatus.APPROVED);
            leave.empoyee.addLeave(leave.getStartDate(),leave.getEndDate());
            leave.empoyee.setNoOfLeavesTaken(leave.empoyee.getNoOfLeavesTaken() +1);
            saveToRepository(leave);
        }
        response.setComment("please check your leave balance");
        return response;
    }

    private void saveToRepository(LeaveRequest leave) {
        if(leave.getEmpId()!=0) {
            employeeRepository.save(leave.empoyee);
        }
    }

    private boolean handleSabbatical(LeaveRequest leave) {
        if(ChronoUnit.YEARS.between(leave.empoyee.getJoiningDate(), leave.getStartDate()) >=2
        && ChronoUnit.DAYS.between(leave.getStartDate(),leave.getEndDate()) > 45
        && ChronoUnit.MONTHS.between(leave.getStartDate(),leave.getEndDate()) <3)
        {
            return true;

        }
        return false;
    }

    private boolean checkForOptionalLeaves(LeaveRequest leave) {
        for (LocalDate date = leave.getStartDate();
             date.isBefore(leave.getEndDate()); date = date.plusDays(1)) {
            if(leave.empoyee.getOptionaLeaves().checkForOptionalLeaves(date))
            {
                return true;
            }
        }

        return false;
    }


    private boolean handleCompOff(LeaveRequest leave) {
        //check the number of days that employee is applying for compoff
        noOfDays = getWorkingDaysBetweenTwoDays(leave.getStartDate(),leave.getEndDate());
        //check if employee has compoff leaves in his account
        if(leave.empoyee.getCompOff().getAvailableLeaves()>0){
        int availableCompoffs = leave.empoyee.getCompOff().avilableCompoffs(leave.getStartDate());
        if (availableCompoffs >= noOfDays)
        {
            return true;
        }
        return false;

        }
        return false;
    }

    private boolean checkForOverlap(LeaveRequest leave) {
        //collect the leaves thay employee has already applied for
        ArrayList<LocalDate> from = leave.empoyee.getLeaveTakenFrom();
        ArrayList<LocalDate> to = leave.empoyee.getLeaveTakenTill();
        for (int i=0;i< from.size();i++) {
            if(validateOverlap(from.get(i), to.get(i),leave))
            {
                return true;
            }

        }
        return false;
    }

    private boolean validateOverlap(LocalDate from, LocalDate to,LeaveRequest leave) {
        /* If the current applied leave is before the leave start date and current applied leave
        * enddate is after the leave end date then overlap will occur*/
        if ((from.isBefore(leave.getStartDate()) && to.isAfter(leave.getEndDate())))
        {
            return true;
        }
        /*If the current applied leave start date is before the stored leave startdate
        and current leave endaate os after leave end date then overlap occurs
        */
        else if(leave.getStartDate().isBefore(to) && leave.getEndDate().isAfter(to))
        {
            return true;
        }
        /*If current date is equal to any of the db leave dates then overlap will occur
        **/
        else if(leave.getEndDate() == to ||
                leave.getStartDate()== from ||
                leave.getEndDate()== from || leave.getStartDate()== to)
        {
            return true;
        }
        /*If current leave applied date is before start and enddate is after start then overlap occurs
        * */
        else if(leave.getStartDate().isBefore(from) && leave.getEndDate().isAfter(from))
        {
            return true;
        }
        return  false;
    }

    private boolean checkForPaternityLeaves(LeaveRequest leave) {
        noOfDays = getWorkingDaysBetweenTwoDays(leave.getStartDate(), leave.getEndDate());
        if(leave.empoyee.getGender() == Gender.MALE && noOfDays <= 10){
            return true;
        }
        return false;
    }

    private boolean checkMaternityLeaves(LeaveRequest leave) {
        leave.setEndDate(leave.getStartDate().plusDays(180));
        noOfDays = ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate());
        if(leave.empoyee.getNoOfMaternityLeavesTaken() >0)
        {
            if(ChronoUnit.DAYS.between(leave.empoyee.getMaternityLeaveTill(), leave.getStartDate()) <180 )
            {
                return  false;
            }
        }

        //if the employee is male or paternity leave is more than 180 days then reject the maternity leave
        if (noOfDays > 180 || leave.empoyee.getGender() != Gender.FEMALE
                || leave.empoyee.getNoOfMaternityLeavesTaken()> 2
                ||ChronoUnit.DAYS.between(leave.empoyee.getJoiningDate(), leave.getStartDate()) <180)
        {
            return false;
        }
        return true;

    }

    private boolean checkForAvailableLeaves (LeaveRequest leave)
        {//if leave has no blanket coverage then get the no of working days between the leaves applied

            if(!leave.isBlanketCoverage()) {
                noOfDays = getWorkingDaysBetweenTwoDays(leave.getStartDate(),leave.getEndDate());
            }
            if(checkForOptionalLeaves(leave))
            {
                noOfDays -=1;
            }

            else
            {
                noOfDays = getWorkingDaysBetweenTwoDays(leave.getStartDate(),leave.getEndDate());
            }
            /*if number of days the employee is applying leave for is greater
            than the employee has in his account then reject the leave
             */
            if(noOfDays > leavesAvailable)
            {
                return false;
            }
            return true;
        }
    private void isDateValid(LeaveRequest leave)
    {
        if (leave.getEndDate().isBefore(leave.getStartDate()))
        {
            throw new IllegalArgumentException("Illegel dates please check");
        }

    }
    private int getWorkingDaysBetweenTwoDays(LocalDate from,LocalDate to) {
        //get working days between two dates
        int workingDays=0;
        for (LocalDate date = from; date.isBefore(to); date = date.plusDays(1)) {
            //if date is saturday or sundau or any other public holiday then dont add it to working day
            if (!(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY
                    || isPublicHoliday(date)))
            {
                workingDays++;
            }
        }
        return workingDays;
    }

    private boolean isPublicHoliday(LocalDate date) {
        //check for public hoidays
        ArrayList <LocalDate> publicHolidays = new ArrayList<>();
        publicHolidays.add(LocalDate.of(2019,1,25));
        publicHolidays.add(LocalDate.of(2019,8,15));
        publicHolidays.add(LocalDate.of(2019,12,25));
        publicHolidays.add(LocalDate.of(2019,10,2));
        return publicHolidays.contains(date);
    }


}

