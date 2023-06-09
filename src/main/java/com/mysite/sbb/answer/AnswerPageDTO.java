package com.mysite.sbb.answer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mysite.sbb.comment.Comment;
import com.mysite.sbb.comment.CommentsVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AnswerPageDTO {
    Integer number;
    Integer totalPages;
    Boolean hasNext;
    Boolean hasPrevious;
    Boolean isEmpty;
    ArrayList<AnswerDTO> answers = new ArrayList<>();

    public void addAnswer(Page<Comment> commentPage, Answer answer, String username) {
        AnswerDTO answerDTO = AnswerDTO.builder()
                .id(answer.getId())
                .author(answer.getAuthor().getName())
                .content(answer.getContent())
                .createDate(answer.getCreateDate())
                .voter(answer.getVoter().size())
                .commentsVO(new CommentsVO())
                .modifiedDate(answer.getModifiedDate()).build();

        answerDTO.commentsVO.injectDatum(commentPage, username);

        this.answers.add(answerDTO);
    }

    public void addAnswer(Page<Comment> commentPage, Answer answer) {
        AnswerDTO answerDTO = AnswerDTO.builder()
                .id(answer.getId())
                .author(answer.getAuthor().getName())
                .content(answer.getContent())
                .createDate(answer.getCreateDate())
                .voter(answer.getVoter().size())
                .commentsVO(new CommentsVO())
                .modifiedDate(answer.getModifiedDate()).build();

        answerDTO.commentsVO.injectDatum(commentPage);

        this.answers.add(answerDTO);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class AnswerDTO {
        Integer id;
        String author;
        String content;
        Integer voter;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
        LocalDateTime createDate;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
        LocalDateTime modifiedDate;
        public CommentsVO commentsVO;
    }
}

