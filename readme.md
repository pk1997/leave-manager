Steps to use API's
**{} => variable;**
##  steps to use api 
    url:'/employee',       method:'GET',   function 'gives out the list of employees stored in database'
    url: '/employee/{id}', method:'POST',  function:'returns the employee details of id mentioned in url    
    url: '/employee/',     method:'POST'   function:'Creates new employee'
#accepted parameters
#        Parameters                  Datatype        Comments
        name                        String          na
        joiningDate                 string          should be of form yyyy-mm-dd
        gender                      string          accepted values MALE,FEMALE
        leavesCarriedFromLastYear   int             if any leaves are carried from last year update this variable
#
    4)url: '/employee/{id}', method:'PUT'      function: Updates the employee data
note:same params as the post but you cannot update name and id of employee
#
       5)url: '/employee/{id}' method: 'DELETE'    function: deletes the employee 
       6)url: '/employee/{id}/workedhours' method:'POST' function:used to log hours for compoff
#accpeted parameters
#        parameters              datatype            Comments
        from                    string              should be of type "yyyy-mm-ddThh:mm:ss"
        to                      string              should be of type "yyyy-mm-ddThh:mm:ss"
### saves only if the working day is holiday and working hours is more than 8

    7)url: '/employee/{id}/leavebalance/' method:'get' function:gives the leavebalance of employee as of today

#Meaning of parameters returned

    Parameters              Comments
    id                      id of the employee
    name                    name of the employee
    noOfLeavesTaken         leaves taken by employee on this calender year
    gender                  gives the gender of employee
    leavesTakenFrom         start date of approved leaves for this calender year
    leavesTakenTill         end date of approved leaves foe this calender year
    compoff                 information about available compoff and when did the employee work to earn the compoff
    joiningDate             date on which employee joined hashedin
    optionalLeaves          information about optional leaves i.e info about how many employe has taken and when
    noOfMaternityLeavesTaken self explainatory
    maternityLeaveFrom      info about recent maternity leave that has been taken
    maternityLeaveTill      
    leavesCarriedFromLastYear self explainatory
#Apply leave API

    1) url:'/leave/apply' method:'POST'  function:'to apply leave'
##      parameters              datatype        comments
        startDate               string          format "yyyy-mm-dd"
        endDate                 string          format "yyyy-mm-dd"
        types                   string          type of leave accepted values "MATERNITY","SABBATICAL","PATERNITY","COMP_OFF"
                                                by default leave type will be normal
        empId                   int             id of the employee applying for leave
        requestedDate           string          format "yyyy-mm-dd" by default date will be current date
                                                date on which leave was requested
        
Return types : ACCEPTED REJECTED PENDING

