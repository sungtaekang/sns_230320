package com.sns.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.like.dao.LikeMapper;

@Service
public class LikeBO {

	@Autowired
	private LikeMapper likeMapper;
	
	// input: 누가, 어느글에
	// output: x
	public void likeToggle(int postId, int userId) {
		if (likeMapper.selectLikeCountByPostIdOrUserId(postId, userId) > 0) {
			// 삭제
			likeMapper.deleteLikeByPostIdUserId(postId, userId);
		} else {
			// 추가
			likeMapper.insertLike(postId, userId);
		}
	}
	
	// input: postId
	// output: 좋아요 개수(int)
	public int getLikeCountByPostId(int postId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, null);
	}
	
	// input: postId, userId
	// output: 좋아요 여부 (boolean)
	public boolean filledLike(int postId, Integer userId) {
		// 비로그인
		if (userId == null) {
			return false;
		}
		
		// 로그인
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId) > 0;
	}
	
}
