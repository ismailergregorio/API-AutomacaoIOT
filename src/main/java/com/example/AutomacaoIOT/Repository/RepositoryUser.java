package com.example.AutomacaoIOT.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AutomacaoIOT.Model.ModelUser.ModelUser;

public interface RepositoryUser extends JpaRepository<ModelUser,Long>{

    ModelUser findByEmail(String email);
 
}
