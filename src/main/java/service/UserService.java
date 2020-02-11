package service;

import static transformer.Transformer.toEntity;

import entity.User;
import repository.UserRepository;;

public class UserService {

    final UserRepository repository = new UserRepository();

    public void doPost(User user) {

        repository.post(user);
    }

    public User doGet(Integer id) {

        return toEntity(repository.get(id));
    }
    
    public void doPut(Integer id,User user) {

        repository.put(id,user);
    }
    
    public void doDelete(Integer id)
    {
        repository.delete(id);
    }
    
}
