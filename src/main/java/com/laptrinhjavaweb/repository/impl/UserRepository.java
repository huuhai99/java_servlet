package com.laptrinhjavaweb.repository.impl;

import javax.swing.JApplet;

import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.repository.IUserRepository;

public class UserRepository extends SimpleJpaRepository<UserEntity>  implements IUserRepository{

}
