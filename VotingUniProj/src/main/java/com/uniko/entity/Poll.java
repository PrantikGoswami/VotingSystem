/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uniko.entity;

import com.uniko.enums.PollState;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author prant
 */
@Entity
@XmlRootElement
public class Poll implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poll_seq")
    @SequenceGenerator(name = "poll_seq", sequenceName = "poll_sequence", allocationSize = 50)
    private Long id;
    
    //this email will fetch all the polls of that organizer
    private String organizerEmail;
    
    private String pollTitle;
    
    private String pollDescription;
    
    private PollState state;
    
    private Date pollingStartDate;
    
    private Date pollingEndDate;
    
    private List<String> groupNames;
    
    private Integer submittedVotes;
    
    //@ManyToOne(fetch = FetchType.LAZY, cascade = )
    @OneToMany(cascade = CascadeType.PERSIST, targetEntity = Question.class)
    @JoinColumn(name = "pollId_Id")
    private List<Question> questions;
    
    //@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @OneToMany(cascade = CascadeType.PERSIST, targetEntity = Token.class)
    @JoinColumn(name = "pollId_Id")
    private List<Token> tokens;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public void setOrganizerEmail(String organizerEmail) {
        this.organizerEmail = organizerEmail;
    }

    public String getPollTitle() {
        return pollTitle;
    }

    public void setPollTitle(String pollTitle) {
        this.pollTitle = pollTitle;
    }

    public String getPollDescription() {
        return pollDescription;
    }

    public void setPollDescription(String pollDescription) {
        this.pollDescription = pollDescription;
    }
    
    

    public PollState getState() {
        return state;
    }

    public void setState(PollState state) {
        this.state = state;
    }

    public Integer getSubmittedVotes() {
        return submittedVotes;
    }

    public void setSubmittedVotes(Integer submittedVotes) {
        this.submittedVotes = submittedVotes;
    }
    
    public Date getPollingStartDate() {
        return pollingStartDate;
    }

    public void setPollingStartDate(Date pollingStartDate) {
        this.pollingStartDate = pollingStartDate;
    }

    public Date getPollingEndDate() {
        return pollingEndDate;
    }

    public void setPollingEndDate(Date pollingEndDate) {
        this.pollingEndDate = pollingEndDate;
    }

    @XmlTransient
    public List<Question> getQuestions() {
        if(questions == null)
            questions= new ArrayList<>();
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @XmlTransient
    public List<Token> getTokens() {
        if(tokens == null)
            tokens = new ArrayList<>();
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<String> getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(List<String> groupNames) {
        this.groupNames = groupNames;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Poll)) {
            return false;
        }
        Poll other = (Poll) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.uniko.entity.Poll[ id=" + id + " ]";
    }
    
}
