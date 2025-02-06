package com.techgroup.techmanage.model;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@NotBlank(message = "O campo nome completo é de preenchimento obrigatório")
	private String fullName;
	
	@Column(unique = true, nullable = false)
	@Email(message = "E-mail inválido")
	private String email;

	@NotBlank(message = "O campo telefone é de preenchimento obrigatório")
	private String phone;
	
	@Past(message = "A data de nascimento deve estar no passado")
	private Date birthDate;
	
	@Enumerated(EnumType.STRING)
	private UserType userType;

	public void update(User newUser)
	{
		this.setFullName(newUser.getFullName());
		this.setEmail(newUser.getEmail());
		this.setPhone(newUser.getPhone());
		this.setBirthDate(newUser.getBirthDate());
		this.setUserType(newUser.getUserType());
	}
}
