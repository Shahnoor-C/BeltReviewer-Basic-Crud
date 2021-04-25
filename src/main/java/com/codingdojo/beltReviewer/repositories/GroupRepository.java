package com.codingdojo.beltReviewer.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.beltReviewer.models.Group;
import com.codingdojo.beltReviewer.models.User;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
	List<Group> findBymembersNotContaining(User u);
}
