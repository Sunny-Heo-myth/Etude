package com.chatboard.etude.dto.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

// Client modify the images of a post.
// New image will be sent as MultiPartFile
// Old image is found and deleted by id
@ApiModel(value = "post update request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequest {

    @ApiModelProperty(value = "post title",
            notes = "Enter post title.",
            required = true,
            example = "my title")
    @NotBlank(message = "Enter post title.")
    private String title;

    @ApiModelProperty(value = "post content",
            notes = "Enter post content.",
            required = true,
            example = "my content")
    @NotBlank(message = "Enter post content.")
    private String content;

    @ApiModelProperty(value = "price", notes = "Enter the price.", required = true, example = "50000")
    @NotNull(message = "Enter the price.")
    @PositiveOrZero(message = "Enter non-negative value.")
    private Long price;

    @ApiModelProperty(value = "added image", notes = "Attach added image.")
    private List<MultipartFile> addedImages = new ArrayList<>();

    @ApiModelProperty(value = "deleted image id.", notes = "Enter the id of deleted image.")
    private List<Long> deletedImages = new ArrayList<>();
}