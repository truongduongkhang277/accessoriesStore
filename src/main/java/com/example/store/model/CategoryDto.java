package com.example.store.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto implements Serializable{

	private Long category_id;

	@NotEmpty
	@Length(min = 5)
	private String categoryname;

	private boolean is_edit = false;

	public boolean isIs_edit() {
		return is_edit;
	}

	public void setIs_edit(boolean is_edit) {
		this.is_edit = is_edit;
	}
	
	
}
