/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uniko.entity;

import com.uniko.enums.DecisionMode;
import com.uniko.enums.OptionTypes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author prant
 */
@Entity
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ques_seq")
    @SequenceGenerator(name = "ques_seq", sequenceName = "ques_sequence", allocationSize = 50)
    private Long id;
    
    private DecisionMode mode;
    private Integer maxAnswer;
    private String title;
    private Integer abstentions;
    private OptionTypes optionType;
    private List<String> selectedCheckboxAnswers;
    private String selectedRadioAnswer;
    
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Poll pollId;
    
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "ItemId_Id")
    private List<Item> options;
    
    
    public Question(){
        options = new ArrayList<>();
        options.add(new Item());
        options.add(new Item());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DecisionMode getMode() {
        return mode;
    }

    public void setMode(DecisionMode mode) {
        this.mode = mode;
    }

    public Integer getMaxAnswer() {
        return maxAnswer;
    }

    public void setMaxAnswer(Integer maxAnswer) {
        this.maxAnswer = maxAnswer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAbstentions() {
        return abstentions;
    }

    public void setAbstentions(Integer abstentions) {
        this.abstentions = abstentions;
    }

    public Poll getPollId() {
        return pollId;
    }

    public void setPollId(Poll pollId) {
        this.pollId = pollId;
    }

    public List<Item> getOptions() {
        return options;
    }

    public void setOptions(List<Item> options) {
        this.options = options;
    }

    public OptionTypes getOptionType() {
        return optionType;
    }

    public void setOptionType(OptionTypes optionType) {
        this.optionType = optionType;
    }

    public List<String> getSelectedCheckboxAnswers() {
        if(selectedCheckboxAnswers == null)
            selectedCheckboxAnswers = new ArrayList<>();
        return selectedCheckboxAnswers;
    }

    public void setSelectedCheckboxAnswers(List<String> selectedCheckboxAnswers) {
        this.selectedCheckboxAnswers = selectedCheckboxAnswers;
    }

    public String getSelectedRadioAnswer() {
        return selectedRadioAnswer;
    }

    public void setSelectedRadioAnswer(String selectedRadioAnswer) {
        this.selectedRadioAnswer = selectedRadioAnswer;
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
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.uniko.entity.Question[ id=" + id + " ]";
    }
    
}
