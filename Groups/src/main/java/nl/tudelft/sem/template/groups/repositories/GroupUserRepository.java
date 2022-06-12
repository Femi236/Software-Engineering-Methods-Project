package nl.tudelft.sem.template.groups.repositories;

import nl.tudelft.sem.template.groups.entities.GroupUser;
import nl.tudelft.sem.template.groups.entities.GroupUserId;
import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface GroupUserRepository extends CrudRepository<GroupUser, GroupUserId> {

}
