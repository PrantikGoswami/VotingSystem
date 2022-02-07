/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uniko.votinguniproj;

import com.uniko.dao.DatabaseOperations;
import com.uniko.entity.Item;
import com.uniko.entity.Person;
import com.uniko.entity.Poll;
import com.uniko.entity.Question;
import com.uniko.entity.Token;
import com.uniko.entity.VotingGroup;
import com.uniko.enums.DecisionMode;
import com.uniko.enums.OptionTypes;
import com.uniko.enums.PollState;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author prant
 */
@Named(value = "personBean")
@SessionScoped
public class PersonManageBean implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(PersonManageBean.class.getName());
    
    private String email;
    private List<Person> personListFromDb;
    private List<Poll> pollList;
    private Poll currentPoll;
    private List<String> selectedGroups;
    private List<VotingGroup> availableGroups;
    private List<String> selectedPersons;
    private List<Person> availablePersons;
    private String groupName;
    private List<Question> questions;
    private List<DecisionMode> decisionMode;
    private List<OptionTypes> optionType;
    
    private String errorMessage;
    
    private Poll pollDetForUsers;
    private Poll resultPoll;
    private Long pollid;
    private String tokenId;
    private Token currentToken;
    private Person loggedPerson;

    /**
     * Creates a new instance of PersonManageBean
     */
    public PersonManageBean() {
        DatabaseOperations.dbConnection();
        loadPersons();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    /**
     * @return the personListFromDb
     */
    public List<Person> getPersonListFromDb() {
        return personListFromDb;
    }

    public List<Poll> getPollList() {
        return pollList;
    }

    public void setPollList(List<Poll> pollList) {
        this.pollList = pollList;
    }

    public Poll getCurrentPoll() {
        return currentPoll;
    }

    public void setCurrentPoll(Poll currentPoll) {
        this.currentPoll = currentPoll;
    }

    public List<String> getSelectedGroups() {
        return selectedGroups;
    }

    public void setSelectedGroups(List<String> selectedGroups) {
        this.selectedGroups = selectedGroups;
    }

    public List<VotingGroup> getAvailableGroups() {
        return availableGroups;
    }

    public void setAvailableGroups(List<VotingGroup> availableGroups) {
        this.availableGroups = availableGroups;
    }

    public List<String> getSelectedPersons() {
        return selectedPersons;
    }

    public void setSelectedPersons(List<String> selectedPersons) {
        this.selectedPersons = selectedPersons;
    }

    public List<Person> getAvailablePersons() {
        return availablePersons;
    }

    public void setAvailablePersons(List<Person> availablePersons) {
        this.availablePersons = availablePersons;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<DecisionMode> getDecisionMode() {
        if(decisionMode == null){
           decisionMode = new ArrayList<>();
           decisionMode.add(DecisionMode.SIMPLE_MAJORITY);
           decisionMode.add(DecisionMode.ABSOLUTE_MAJORITY);
           decisionMode.add(DecisionMode.RELATIVE_MAJORITY);
        }
        return decisionMode;
    }

    public void setDecisionMode(List<DecisionMode> decisionMode) {
        this.decisionMode = decisionMode;
    }

    public List<OptionTypes> getOptionType() {
        if(optionType == null){
           optionType = new ArrayList<>();
           optionType.add(OptionTypes.CHECKBOX);
           optionType.add(OptionTypes.RADIOBUTTON);
        }
        return optionType;
    }

    public void setOptionType(List<OptionTypes> optionType) {
        this.optionType = optionType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    
    public Poll getPollDetForUsers() {
        return pollDetForUsers;
    }

    public void setPollDetForUsers(Poll pollDetForUsers) {
        this.pollDetForUsers = pollDetForUsers;
    }

    public Poll getResultPoll() {
        return resultPoll;
    }

    public void setResultPoll(Poll resultPoll) {
        this.resultPoll = resultPoll;
    }

    
    public Long getPollid() {
        return pollid;
    }

    public void setPollid(Long pollid) {
        this.pollid = pollid;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(Token currentToken) {
        this.currentToken = currentToken;
    }
    
    public Person getLoggedPerson() {
        return loggedPerson;
    }

    public void setLoggedPerson(Person loggedPerson) {
        this.loggedPerson = loggedPerson;
    }

    private void loadPersons(){
        LOG.info("PersonManagebean : Entry into loadPersons() method");
        if(personListFromDb == null)
            personListFromDb = new ArrayList<>();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String path = ec.getRealPath("/resources/person.csv");
        try(BufferedReader bf = new BufferedReader(new FileReader(path))){
           String line;
           while((line = bf.readLine()) != null){
                String[] values = line.split(",")[0].split(";");
                if(values.length == 3 && !"NAME".equals(values[0].trim()))
                    personListFromDb.add(new Person(values[1], values[2], values[0]));
                else
                    System.out.println("Defective users "+values[0]);
                   
           }
           LOG.info("PersonManagebean : Exit from loadPersons() method");
        }catch(IOException ex){
            ex.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


    public String login(){
        LOG.info("PersonManagebean : Entry into Login() method");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if(!facesContext.getExternalContext().isUserInRole("ORGANIZER")){
            FacesContext.getCurrentInstance().addMessage("loginMessageId", new FacesMessage("You don't have Organizer role"));
            return "login.xhtml";
        }
        Principal principal =facesContext.getExternalContext().getUserPrincipal();
        LOG.log(Level.INFO, "PersonManagebean : logged in user: {0}", principal.getName());
        setEmail(principal.getName());
        if(getEmail() != null){
            FacesContext.getCurrentInstance().addMessage("loginForm:loginId", new FacesMessage("Login #" + getEmail() + " is Successful"));
            for(Person person : personListFromDb){
                    if(person.getEmail().trim().equals(getEmail()))
                        setLoggedPerson(person);
                }
            List<Poll> pollListDb = DatabaseOperations.fetchAllPollDetailsForOrganizer(getEmail());
            availableGroups = DatabaseOperations.fetchAllGroupDetailsForOrganizer(getEmail());
            if(availableGroups == null)
                availableGroups = new ArrayList<>();
            if(pollListDb == null){
                LOG.info("PersonManagebean : Exit from Login() method, User has no poll record yet.");
                return "dashboard.xhtml";
            }
            for(Poll poll : pollListDb){
                if(poll.getState().equals(PollState.PREPARED)){
                    poll.setState(PollState.PREPARED);
                    break;
                }
                if(poll.getPollingStartDate().compareTo(new Date()) <= 0)
                    poll.setState(PollState.STARTED);
                if(poll.getSubmittedVotes()>0)
                    poll.setState(PollState.VOTING);
                if((!poll.getSubmittedVotes().equals(0) && poll.getSubmittedVotes().equals(poll.getTokens().size())) || poll.getPollingEndDate().before(new Date()))
                    poll.setState(PollState.FINISHED);
            }
            setPollList(pollListDb);
        }
        LOG.info("PersonManagebean : Exit from Login() method");
        return "dashboard.xhtml";
    }

    public String addPoll(){
        LOG.info("PersonManagebean : Entry into addPoll() method");
        this.currentPoll = new Poll();
        this.currentPoll.setSubmittedVotes(0);
        currentPoll.setOrganizerEmail(getEmail());
        questions = new ArrayList<>();
        questions.add(new Question());
        LOG.info("PersonManagebean : Exit from addPoll() method");
        return "newPoll.xhtml";
    }

    public String addQuestion(){
        LOG.info("PersonManagebean : Entry into addQuestion() method");
        questions.add(new Question());
        LOG.info("PersonManagebean : Exit from addQuestion() method");
        return "newPoll.xhtml";
    }

    public String addOptions(Question question){
        LOG.info("PersonManagebean : Entry into addOptions() method");
        question.getOptions().add(new Item());
        LOG.info("PersonManagebean : Exit from addOptions() method");
        return "newPoll.xhtml";
    }
    public String addNewPollRecord(){
        LOG.info("PersonManagebean : Entry into addNewPollRecord() method");
        if(currentPoll.getPollingEndDate().before(currentPoll.getPollingStartDate())){
            FacesContext.getCurrentInstance().addMessage("newPollForm:endDate", new FacesMessage("End date must be after Start date!!"));
            return "newPoll.xhtml";
        }
        if(currentPoll.getPollingEndDate().before(new Date())){
            FacesContext.getCurrentInstance().addMessage("newPollForm:endDate", new FacesMessage("End date must be future date!!"));
            return "newPoll.xhtml";
        }
        if(selectedGroups == null || selectedGroups.isEmpty()){
            FacesContext.getCurrentInstance().addMessage("newPollForm:groupNames", new FacesMessage("Please select at least one group!!"));
            return "newPoll.xhtml";
        }
        currentPoll.setState(PollState.PREPARED);
        currentPoll.setGroupNames(selectedGroups);
        for(Question question : questions){
            for(Item item : question.getOptions())
                item.setVotes(0);
        }
        currentPoll.setQuestions(questions);
        currentPoll.setTokens(new ArrayList<Token>());
        String stat = DatabaseOperations.saveNewPollAndGroup(currentPoll,availableGroups);
        if("Success".equals(stat)){
            LOG.info("PersonManagebean : addNewPollRecord() method, Save Success");
            setPollList(DatabaseOperations.fetchAllPollDetailsForOrganizer(getEmail()));
            LOG.info("PersonManagebean : Exit from addNewPollRecord() method");
            return "dashboard.xhtml";
        }
        LOG.info("PersonManagebean : Exit from addNewPollRecord() method, Saving failed");
        return "newPoll.xhtml";
    }

    public String startPoll(Poll currPoll){
        LOG.info("PersonManagebean : Entry into startPoll() method");
        currPoll.setState(PollState.STARTED);
        currPoll.setSubmittedVotes(0);
        List<VotingGroup> groups =DatabaseOperations.getGroupDetailsFromEmail(currPoll.getOrganizerEmail());
        Set<String> mailSet = new HashSet<>();
        for(VotingGroup group : groups){
            if(currPoll.getGroupNames().contains(group.getGroupName()))
                mailSet.addAll(group.getMemberMailIdList());
        }
        
        
        for(int i=0;i<mailSet.size();i++){
            currPoll.getTokens().add(new Token()); 
        }
        String result = DatabaseOperations.updatePollRecord(currPoll);
        if("Success".equals(result)){
            LOG.info("PersonManagebean : startPoll() method, poll record update successful");
            setPollList(DatabaseOperations.fetchAllPollDetailsForOrganizer(getEmail()));
            LOG.info("PersonManagebean : startPoll() method, Sending mails");
            // Send email to all the recipients
            MailBean mail = new MailBean();
            // this func call has to be in a loop where tokens will be generated.
            //mail.send("Subject", "This is body where we have to send the link like. http://www.google.com", "usman@uni-koblenz.de");
            List<String> mailList = new ArrayList<>(mailSet);
            for(int i=0; i<mailList.size(); i++){
                mail.send("Submit voting for-"+currPoll.getPollTitle(), "http://localhost:8080/VotingUniProj/pages/vote/userPage.xhtml?id="+currPoll.getId()+"&&tokenId="+currPoll.getTokens().get(i).getTokenValue(), mailList.get(i));
            }
            LOG.info("PersonManagebean : in startPoll() method : Mail sending done!!");
        }
        LOG.info("PersonManagebean : Exit from startPoll() method");
        return "dashboard.xhtml";
    }

    public String createNewGroup(){
        LOG.info("PersonManagebean : Enter into createNewGroup() method");
        List<Person> personList = new ArrayList<>(getPersonListFromDb());
        for(Person person : personList){
            if(person.getEmail().equals(getEmail())){
                personList.remove(person);
                break;
            }
        }
        setAvailablePersons(personList);
        if (selectedPersons != null && !selectedPersons.isEmpty())
            selectedPersons.clear();
        groupName = null;
        LOG.info("PersonManagebean : Exit from createNewGroup() method");
        return "newGroup.xhtml";
    }

    public String addNewGroup(){
        LOG.info("PersonManagebean : Enter into addNewGroup() method");
        VotingGroup saveGrp = new VotingGroup();
        saveGrp.setGroupName(groupName);
        saveGrp.setMemberMailIdList(selectedPersons);
        saveGrp.setOrganizerMailId(getEmail());
        availableGroups.add(saveGrp);
        LOG.info("PersonManagebean : Exit from addNewGroup() method");
        return addPoll();
    }

    public String deleteOptions(Question question, Item item){
        LOG.info("PersonManagebean : Enter into deleteOptions() method");
        int index = questions.indexOf(question);
        for(int i=0; i<question.getOptions().size();i++){
            if(question.getOptions().get(i).getName().equals(item.getName())){
                question.getOptions().remove(i);
                break;
            }    
        }
        questions.set(index, question);
        LOG.info("PersonManagebean : Exit from deleteOptions() method");
        return "newPoll.xhtml";
    }

    public String deleteQuestion(Question question){
        LOG.info("PersonManagebean : Enter into deleteQuestion() method");
        questions.remove(question);
        LOG.info("PersonManagebean : Exit from deleteQuestion() method");
        return "newPoll.xhtml";
    }

    //http://localhost:8080/VotingUniProj/pages/vote/userPage.xhtml?id=57102&&tokenId=6bee6cc6-cbc3-40e4-91d5-89feb5658d76
    public String initPoll(){
        LOG.info("PersonManagebean : Enter into initPoll() method");
        LOG.log(Level.INFO, "PersonManagebean : initPoll() method : Token ID {0}", this.tokenId);
        LOG.log(Level.INFO, "PersonManagebean : initPoll() method : Poll ID {0}", this.pollid);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if(!facesContext.getExternalContext().isUserInRole("PERSON")){
            FacesContext.getCurrentInstance().addMessage("loginMessageId", new FacesMessage("You can't vote"));
            return "login.xhtml";
        }
        currentToken = DatabaseOperations.getTokenFromId(this.tokenId);
        if(currentToken == null){
            setErrorMessage("Fail. Token id invalid");
            LOG.info("PersonManagebean : Exit from initPoll() method, Invalid Token Id");
            return "error.xhtml";
        }
        this.pollDetForUsers = null;
        this.pollDetForUsers = DatabaseOperations.getPollDetailsFromId(this.pollid);
        if(pollDetForUsers == null){
            setErrorMessage("Fail. Poll id invalid");
            LOG.info("PersonManagebean : Exit from initPoll() method, Invalid Poll Id");
            return "error.xhtml";
        }
        if(pollDetForUsers.getPollingEndDate().before(new Date())){
            setErrorMessage("Fail. Poll has already finished");
            LOG.info("PersonManagebean : Exit from initPoll() method, Poll already finished");
            return "error.xhtml";
        }
        for(Question ques : pollDetForUsers.getQuestions()){
            ques.setSelectedRadioAnswer(null);
            ques.setSelectedCheckboxAnswers(null);
        }
        LOG.info("PersonManagebean : Exit from initPoll() method");
        return "userPage.xhtml";
    }

    public String saveVoting(){
        LOG.info("PersonManagebean : Enter into saveVoting() method");
        quesfor : for(Question ques : pollDetForUsers.getQuestions()){
            if("abstention".equals(ques.getSelectedRadioAnswer()) || ques.getSelectedCheckboxAnswers().contains("abstention")){
                if(ques.getAbstentions()==null)
                    ques.setAbstentions(0);
                ques.setAbstentions(ques.getAbstentions()+1);
                continue;
            }
            for(Item item : ques.getOptions()){
                if(item.getVotes()==null)
                    item.setVotes(0);
                if(OptionTypes.RADIOBUTTON.equals(ques.getOptionType())){
                      if(item.getName().equals(ques.getSelectedRadioAnswer())){
                          item.setVotes(item.getVotes()+1);
                          continue quesfor;
                      }

                }else{
                    for(String str : ques.getSelectedCheckboxAnswers()){
                        if(item.getName().equals(str)){
                            item.setVotes(item.getVotes()+1);
                        }
                    }
                }
            }
        }
        pollDetForUsers.setSubmittedVotes(pollDetForUsers.getSubmittedVotes()+1);
        for(Token token : pollDetForUsers.getTokens()){
            if(currentToken.getTokenValue().equals(token.getTokenValue())){
                token.setTokenValue("Done");
                break;
            }
        }
        String result = DatabaseOperations.updatePollRecord(pollDetForUsers);
        if("Failure".equals(result)){
            FacesContext.getCurrentInstance().addMessage("id", new FacesMessage("Voting failed"));
        }else{
            FacesContext.getCurrentInstance().addMessage("id", new FacesMessage("You have successfully voted"));
            LOG.info("PersonManagebean : Exit from saveVoting() method, Voting Successful");
            return "success.xhtml";
        }
        LOG.info("PersonManagebean : Exit from saveVoting() method, Voting failed");
        return "userPage.xhtml";
    }

    public String calculatePoll(Poll poll){
        LOG.info("PersonManagebean : Enter into calculatePoll() method");
        this.resultPoll = poll;
        LOG.info("PersonManagebean : Exit from calculatePoll() method");
        return "result.xhtml";
    }
}