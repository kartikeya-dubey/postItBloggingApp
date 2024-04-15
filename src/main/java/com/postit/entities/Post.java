package com.postit.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="post")
@Getter
@Setter
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	
	@Column(name = "post_title", length = 100, nullable = false)
	private String title;
	
	@Column(length = 10000)
	private String content;
	private String imageName;
	private Date addedDate;
	
	//Posts - Category entity relationship : One Category can have multiple posts
	@ManyToOne
	//Column created was category_category_id -> to rename use JOIN COLUMN attribute
	@JoinColumn(name = "category_id")
	private Category category;
	
	//Posts - User entity relationship : One User can have multiple posts
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Comment> comments=new HashSet<>();
	
}
