package com.celebrateit.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString(callSuper = true, exclude = { "password"})
public class Users extends BaseEntity implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Integer userid;
	
	@Column(name="user_name",length = 20,nullable = false)
	private String userName;
	
	
	@Column(length = 200,unique = true)//unique constraint will be added
	private String email;
	
	@Column(length = 255,nullable = false)//NOT NULL 
	private String password;
//	@Transient //skip col generation
//	private String confirmPassword;
	
	 @Column(name = "contact_number", length = 15, nullable = false, unique = true)
	 private String contact_number;
	

	
	@Enumerated(EnumType.STRING)
	@Column(name="role",length = 20,nullable=false)//varchar(20)
	private UserRole role=UserRole.USER;
	
	//fk
	//1:n bi   service booking
	@OneToMany(mappedBy = "user")
	private List<Bookings> service_bookings =new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.singletonList(new SimpleGrantedAuthority(this.role.name()));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	

//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		return this.userName;
//	}
	
	
}
