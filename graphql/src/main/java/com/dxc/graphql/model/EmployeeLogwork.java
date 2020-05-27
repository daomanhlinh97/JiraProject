package com.dxc.graphql.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

@Entity
@Table(name="EmployeeLogwork")
@EntityListeners(AuditingEntityListener.class)
public class EmployeeLogwork {
	@Id
	private String id;
	private String idEmployee;
	private int logworkHours;
	private Date dateOfTask;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(String idEmployee) {
		this.idEmployee = idEmployee;
	}
	public int getLogworkHours() {
		return logworkHours;
	}
	public void setLogworkHours(int logworkHours) {
		this.logworkHours = logworkHours;
	}
	public Date getDateOfTask() {
		return dateOfTask;
	}
	public void setDateOfTask(Date dateOfTask) {
		this.dateOfTask = dateOfTask;
	}
	
	
}
