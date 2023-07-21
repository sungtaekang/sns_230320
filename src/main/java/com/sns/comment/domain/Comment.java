package com.sns.comment.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Comment {

	private int id;
	private int userId;
	private int postId;
	private String content;
	private Date createdAt;
	private Date updatedAt;
}
