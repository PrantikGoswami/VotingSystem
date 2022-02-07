package com.uniko.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import com.uniko.entity.Poll;
import com.uniko.entity.Token;
import com.uniko.entity.VotingGroup;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

public class DatabaseOperations {
        private static final Logger LOG = Logger.getLogger(DatabaseOperations.class.getName());
        
	private static final String PERSISTENCE_UNIT_NAME = "voting";	
	private static EntityManager entityMgrObj = null;
        private static EntityManagerFactory emf = null;
        
        public static void dbConnection(){
            LOG.info("DatabaseOperations : Enter into dbConnection() method");
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            entityMgrObj = emf.createEntityManager();
            LOG.info("DatabaseOperations : Exit from dbConnection() method");
        }
        
        @SuppressWarnings("unchecked")
	public static List<VotingGroup> getGroupDetailsFromEmail(String email) {
                LOG.info("DatabaseOperations : Enter into getGroupDetailsFromEmail() method");
		Query queryObj = entityMgrObj.createQuery("SELECT s FROM VotingGroup s WHERE s.organizerMailId = :email");
                queryObj.setParameter("email", email);
		List<VotingGroup> groupList = queryObj.getResultList();
		if (groupList != null && groupList.size() > 0) {
                        LOG.info("DatabaseOperations : Exit from getGroupDetailsFromEmail() method, Grouplist exist for user");
			return groupList;
		} else {
                        LOG.info("DatabaseOperations : Exit from getGroupDetailsFromEmail() method, Grouplist not exist for user");
			return null;
		}
	}
        

        // Method To Fetch All Poll Details From Poll id from The Database
	@SuppressWarnings("unchecked")
	public static Poll getPollDetailsFromId(Long id) {
                LOG.info("DatabaseOperations : Enter into getPollDetailsFromId() method");
		Query queryObj = entityMgrObj.createQuery("SELECT s FROM Poll s WHERE s.id = :id");
                queryObj.setParameter("id", id);
		List<Poll> pollList = queryObj.getResultList();
		if (pollList != null && pollList.size() > 0) {
                        LOG.info("DatabaseOperations : Exit from getPollDetailsFromId() method, returing poll record");
                        return pollList.get(0);
		} else {
                        LOG.info("DatabaseOperations : Exit from getPollDetailsFromId() method, no poll record found");
			return null;
		}
	}
        
        @SuppressWarnings("unchecked")
	public static Token getTokenFromId(String tokenId) {
                LOG.info("DatabaseOperations : Enter into getTokenFromId() method");
		Query queryObj = entityMgrObj.createQuery("SELECT s FROM Token s WHERE s.tokenValue = :id");
                queryObj.setParameter("id", tokenId);
		List<Token> tokenList = queryObj.getResultList();
		if (tokenList != null && tokenList.size() > 0) {
                        LOG.info("DatabaseOperations : Exit from getTokenFromId() method, returning token record");
                        return tokenList.get(0);
		} else {
                        LOG.info("DatabaseOperations : Exit from getTokenFromId() method, no token record found");
			return null;
		}
	}

        // Method To Fetch All Poll Details for organizer From The Database
	@SuppressWarnings("unchecked")
	public static List<Poll> fetchAllPollDetailsForOrganizer(String email) {
            LOG.info("DatabaseOperations : Enter into fetchAllPollDetailsForOrganizer() method");
            Query queryObj = entityMgrObj.createQuery("SELECT s FROM Poll s WHERE s.organizerEmail = :email");
            queryObj.setParameter("email", email);
            List<Poll> pollList = queryObj.getResultList();
            if (pollList != null && pollList.size() > 0){
                    LOG.info("DatabaseOperations : Exit from fetchAllPollDetailsForOrganizer() method, returning all poll records");
                    return pollList;
            }else{
                    LOG.info("DatabaseOperations : Exit from fetchAllPollDetailsForOrganizer() method, no poll record found");
                    return null;
            }
	}
        

        // Method To Fetch All Group Details for the organizer From The Database
	@SuppressWarnings("unchecked")
	public static List<VotingGroup> fetchAllGroupDetailsForOrganizer(String email) {
            LOG.info("DatabaseOperations : Enter into fetchAllGroupDetailsForOrganizer() method");
            Query queryObj = entityMgrObj.createQuery("SELECT s FROM VotingGroup s WHERE s.organizerMailId = :email");
            queryObj.setParameter("email", email);
            List<VotingGroup> groupList = queryObj.getResultList();
            if (groupList != null && groupList.size() > 0) {
                    LOG.info("DatabaseOperations : Exit from fetchAllGroupDetailsForOrganizer() method, returning all group records");
                    return groupList;
            } else {
                    LOG.info("DatabaseOperations : Exit from fetchAllPollDetailsForOrganizer() method, no group record found");
                    return null;
            }
	}
        
        @Transactional(Transactional.TxType.SUPPORTS)
        public static String saveNewPollAndGroup(Poll newPollRecord, List<VotingGroup> groupList){
            LOG.info("DatabaseOperations : Enter into saveNewPollAndGroup() method");
            if(entityMgrObj.isOpen()){
                    if(!entityMgrObj.isJoinedToTransaction())
                        entityMgrObj.joinTransaction();
                    for(VotingGroup voting : groupList){
                        entityMgrObj.persist(voting);
                    }
                    entityMgrObj.persist(newPollRecord);
                    
                    entityMgrObj.flush();
                }
            LOG.info("DatabaseOperations : Exit from saveNewPollAndGroup() method, Poll record and voting groups saved successfully");
            return "Success";
        }
        
        public static String updatePollRecord(Poll updatedPoll){
            LOG.info("DatabaseOperations : Enter into updatePollRecord() method");
            String status = "Failure";
            if(entityMgrObj.isOpen()){
                entityMgrObj.joinTransaction();
                Poll updatedRecord = entityMgrObj.merge(updatedPoll);
                entityMgrObj.flush();
                if(updatedRecord != null){
                    LOG.info("DatabaseOperations : Exit from updatePollRecord() method, update successful.");
                    status="Success";
                }
            }
            LOG.info("DatabaseOperations : Exit from updatePollRecord() method.");
            return status;
        }
}