package com.techgroup.techmanage.model;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User 
{
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@NotBlank(message = "O campo nome completo é de preenchimento obrigatório")
	private String fullName;
	
	@Column(unique = true)
	@NotBlank(message = "O campo e-mail é de preenchimento obrigatório")
	@Email(message = "E-mail em formato inválido")
	private String email;

	@NotBlank(message = "O campo telefone é de preenchimento obrigatório")
	@Pattern(regexp = "^\\+\\d{1,3} \\d{2} \\d{4,5}-\\d{4}$", message = "Número de telefone inválido. Formato esperado: +XX XX XXXXX-XXXX")
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
