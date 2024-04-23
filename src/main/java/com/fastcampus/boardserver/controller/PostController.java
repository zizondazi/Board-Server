package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.TagDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.dto.response.CommonResponse;
import com.fastcampus.boardserver.service.impl.PostServiceImpl;
import com.fastcampus.boardserver.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.xml.stream.events.Comment;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/posts")
@Log4j2
public class PostController {

    private final UserServiceImpl userService;
    private final PostServiceImpl postService;

    public PostController(UserServiceImpl userService, PostServiceImpl postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> registerPost(String accountId,
                                                                @RequestBody PostDTO postDTO ) {
        postService.register(accountId, postDTO);
        CommonResponse<PostDTO> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPost",postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping("my-posts")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<List<PostDTO>>> myPostInfo(String accountId) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        List<PostDTO> posts = postService.getMyPosts(userInfo.getId());
        CommonResponse<List<PostDTO>> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "myPostInfo",posts);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> updatePosts(String accountId,
                                                                    @PathVariable(name = "postId") int postId,
                                                                    @RequestBody PostUpdateRequest postRequest) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .contents(postRequest.getContents())
                .name(postRequest.getName())
                .views(postRequest.getViews())
                .categoryId(postRequest.getCategoryId())
                .userId(userInfo.getId())
                .fileId(postRequest.getFileId())
                .updateTime(new Date())
                .build();
        postService.updatePosts(postDTO);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePosts",postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDeleteRequest>> deletePosts(String accountId,
                                                             @PathVariable(name = "postId") int postId,
                                                             @RequestBody PostDeleteRequest postRequest) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        postService.deletePosts(userInfo.getId(), postId);
        CommonResponse<PostDeleteRequest> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePosts", postRequest);
        return ResponseEntity.ok(commonResponse);
    }

    // -- comments --
    @PostMapping("comments")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> registerPostComment(String accountId,
                                                                          @RequestBody CommentDTO commentDTO) {
        postService.registerPostComment(commentDTO);
        CommonResponse<CommentDTO> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPostComment", commentDTO);

        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> updateComment(String accountId,
                                                                    @PathVariable(name="commentId") int commentId,
                                                                    @RequestBody CommentDTO commentDTO) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        if(userInfo != null) {
            postService.updateComment(commentDTO);
        }
        CommonResponse<CommentDTO> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updateComment", commentDTO);

        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> deleteComment(String accountId,
                                                                    @PathVariable(name="commentId") int commentId) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        if(userInfo != null) {
            postService.deleteComment(userInfo.getId(), commentId);
        }
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updateComment", commentId);

        return ResponseEntity.ok(commonResponse);
    }

    // -- tag --
    @PostMapping("tags")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> registerTag(String accountId,
                                                                   @RequestBody TagDTO tagDTO) {
        postService.registerTag(tagDTO);
        CommonResponse<TagDTO> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPostComment", tagDTO);

        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> updateTag(String accountId,
                                                                    @PathVariable(name="tagId") int tagId,
                                                                    @RequestBody TagDTO tagDTO) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        if(userInfo != null) {
            postService.updateTag(tagDTO);
        }
        CommonResponse<TagDTO> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updateComment", tagDTO);

        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> deleteTagId(String accountId,
                                                                    @PathVariable(name="tagId") int tagId) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        if(userInfo != null) {
            postService.deleteTag(userInfo.getId(), tagId);
        }
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updateComment", tagId);

        return ResponseEntity.ok(commonResponse);
    }

    @Getter
    @AllArgsConstructor
    private static class PostResponse {
        private List<PostDTO> posts;
    }

    @Getter
    @Setter
    private static class PostUpdateRequest {
        private String name;
        private String contents;
        private int views;
        private int categoryId;
        private int userId;
        private int fileId;
        private Date updateTime;
    }

    @Getter
    @Setter
    private static class PostDeleteRequest {
        private int id;
        private int accountId;
    }
}
