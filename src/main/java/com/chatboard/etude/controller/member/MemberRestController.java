package com.chatboard.etude.controller.member;

import com.chatboard.etude.dto.response.Response;
import com.chatboard.etude.service.member.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "member controller", tags = "member")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberRestController {

    private final MemberService memberService;

    @ApiOperation(value = "member information read", notes = "Read member information.")
    @GetMapping("/api/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response read(
            @ApiParam(value = "user id", required = true) @PathVariable Long id) {
        return Response.success(memberService.readMember(id));
    }

    @ApiOperation(value = "member information deletion", notes = "Delete member information.")
    @DeleteMapping("/api/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(
            @ApiParam(value = "user id", required = true) @PathVariable Long id) {
        memberService.deleteMember(id);
        return Response.success();
    }
}