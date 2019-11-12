package org.manuel.mysportfolio.repositories.impl;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.manuel.mysportfolio.repositories.TeamToUsersCustomRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@lombok.AllArgsConstructor
class TeamToUsersCustomRepositoryImpl implements TeamToUsersCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<TeamToUsers> findByUsersExists(final String userId) {
        final var criteria = Criteria.where("users." + userId).exists(true);
        return mongoTemplate.find(new Query(criteria), TeamToUsers.class);
    }

    @Override
    public UserInTeam updateUserInTeam(final ObjectId teamId, final String userId, final UserInTeam userInTeam) {
        final var criteria = Criteria.where("teamId").is(teamId);
        final var query = new Query(criteria);
        final var update = new Update().set("users." + userId, userInTeam);
        mongoTemplate.upsert(query, update, TeamToUsers.class);
        // now retrieve only the updated user in team
        query.fields().include("users." + userId);
        return mongoTemplate.findOne(query, TeamToUsers.class).getUsers().get(userId);
    }

}
