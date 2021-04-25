package com.codingdojo.beltReviewer.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity //telling its a database entity
@Table(name="groupz") // 'groups' is a built in keyword in spring tools so rename it to soemthign elese
public class Group {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @NotEmpty(message="Group name cannot be empty")
	 @Size(min=2,message="Group name must be at least 2 characters!")
	 private String name;
	 
	 @NotEmpty(message="Group description cannot be empty!")
	 @Size(min=10,max=1000,message="Group description must be at least 10 characters and less then 1000 characters!")
	 private String description;
	 
	
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name="creator_id")
	 private User creator;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name="vp_id")
	 private User vp;
	 
	 @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(
	        name = "users_groups_members", 
	        joinColumns = @JoinColumn(name = "group_id"), 
	        inverseJoinColumns = @JoinColumn(name = "user_id")
	    )
	 //A group is gonna have a list of users
	    private List<User>members;
	 

	 
	 public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public User getCreator() {
		return creator;
	}



	public void setCreator(User creator) {
		this.creator = creator;
	}



	public User getVp() {
		return vp;
	}



	public void setVp(User vp) {
		this.vp = vp;
	}



	public List<User> getMembers() {
		return members;
	}



	public void setMembers(List<User> members) {
		this.members = members;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	public Date getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}



	@Column(updatable=false)
	 private Date createdAt;
	 private Date updatedAt;
	 
	 
	 
	 public Group() {
		 
	 }
	 
	 @PrePersist
	    protected void onCreate(){
	        this.createdAt = new Date();
	    }
	    @PreUpdate
	    protected void onUpdate(){
	        this.updatedAt = new Date();
	    }
	 
	 
	 

}
