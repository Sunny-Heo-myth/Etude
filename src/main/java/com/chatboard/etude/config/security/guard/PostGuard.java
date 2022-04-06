package com.chatboard.etude.config.security.guard;

import com.chatboard.etude.entity.member.RoleType;
import com.chatboard.etude.entity.post.Post;
import com.chatboard.etude.exception.AccessDeniedException;
import com.chatboard.etude.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostGuard extends Guard{

    private final PostRepository postRepository;
    private final List<RoleType> roleTypes = List.of(RoleType.ROLE_ADMIN);

    @Override
    protected List<RoleType> getRoleTypes() {
        return roleTypes;
    }

    protected boolean isResourceOwner(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    throw new AccessDeniedException("");
                });
        Long memberId = AuthenticationHelper.extractMemberId();
        return post.getMember().getId().equals(memberId);
    }

}