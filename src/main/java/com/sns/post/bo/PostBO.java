package com.sns.post.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sns.comment.bo.CommentBO;
import com.sns.common.FileManagerService;
import com.sns.like.bo.LikeBO;
import com.sns.post.dao.PostMapper;
import com.sns.post.dao.PostRepository;
import com.sns.post.domain.Post;
import com.sns.post.entity.PostEntity;

@Service
public class PostBO {
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private FileManagerService fileManager;
	
	public Post getPostByPostIdAndUserId(int postId, int userId) {
		return postMapper.selectPostByPostIdAndUserId(postId, userId);
	}
	
	public List<PostEntity> getPostList() {
		return postRepository.findAllByOrderByIdDesc();
	}
	
	public PostEntity addPost(int userId, String userLoginId, String content, MultipartFile file) {
		String imagePath = null;
		
		// 이미지가 있으면 업로드 후 imagePath를 받아옴
		if (file != null) {
			imagePath = fileManager.saveFile(userLoginId, file);
		}
		
		return postRepository.save(
				PostEntity.builder()
				.userId(userId)
				.content(content)
				.imagePath(imagePath)
				.build());
	}
	
	public void deletePostByPostIdAndUserId(int postId, int userId) {
		Post post = postMapper.selectPostByPostIdAndUserId(postId, userId);
		if (post == null) {
			logger.error("###[글 삭제] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}
				
		// 기존에 이미지가 있으면 삭제
		if(post.getImagePath() != null) {
			fileManager.deleteFile(post.getImagePath());
		}
		
		// 댓글 삭제
		if (commentBO.generatedCommentViewList(postId) != null) {
			commentBO.deleteCommentById(userId);
		}
		
		// 좋아요 삭제
		
		// 포스트 삭제
		postMapper.deletePostByPostIdAndUserId(postId, userId);
	}
}
