package com.example.store.model;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

	@NotEmpty
	@Length(min = 6)
	private String username;

	@NotEmpty
	@Length(min = 6)
	private String password;
	
	private boolean is_edit = false;

	public boolean isIs_edit() {
		return is_edit;
	}

	public void setIs_edit(boolean is_edit) {
		this.is_edit = is_edit;
	}

}
