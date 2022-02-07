/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uniko.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author prant
 */
@Entity
public class VotingGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq")
    @SequenceGenerator(name = "group_seq", sequenceName = "group_sequence", allocationSize = 50)
    private Long id;
    
    private String organizerMailId;
    
    private List<String> memberMailIdList;
    
    private String groupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizerMailId() {
        return organizerMailId;
    }

    public void setOrganizerMailId(String organizerMailId) {
        this.organizerMailId = organizerMailId;
    }

    public List<String> getMemberMailIdList() {
        return memberMailIdList;
    }

    public void setMemberMailIdList(List<String> memberMailIdList) {
        this.memberMailIdList = memberMailIdList;
    }

    

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
        if (!(object instanceof VotingGroup)) {
            return false;
        }
        VotingGroup other = (VotingGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.uniko.entity.VotingGroup[ id=" + id + " ]";
    }
    
}
